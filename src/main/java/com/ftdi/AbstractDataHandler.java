package com.ftdi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftdi.utils.ByteUtils;

public abstract class AbstractDataHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDataHandler.class);

    protected byte[] inputBuffer = new byte[2048];

    private ByteArrayOutputStream output = new ByteArrayOutputStream();

    private AtomicBoolean receiverRunning = new AtomicBoolean();

    private BlockingQueue<byte[]> receiveQueue = new LinkedBlockingQueue<>();

    private AtomicLong receiveQueueWorkerThreadId = new AtomicLong();

    private String requestedPortName;

    private Thread receiveQueueWorker;

    private Thread receiverThread;

    private FTDevice device;

    protected abstract void createEventHandle();

    protected abstract void waitForNotificationEvent(final FTDevice ftDevice, int eventMask) throws FTD2XXException;

    protected abstract int readData(final FTDevice ftDevice) throws FTD2XXException;

    protected abstract void processMessages(byte[] bytes);

    protected abstract void closeHandle();

    protected void configureDevice(final FTDevice ftDevice) throws FTD2XXException {

    }

    protected Thread createReceiverThread() {
        return new ReceiverThread();
    }

    public void setFTDevice(FTDevice device) {
        this.device = device;
    }

    private FTDevice getFTDevice() {
        return this.device;
    }

    public class ReceiverThread extends Thread {
        public ReceiverThread() {
            super("FTDI-Receiver");
        }

        @Override
        public void run() {
            receiverRunning.set(true);

            Thread.currentThread().setPriority(NORM_PRIORITY - 1);

            final FTDevice ftDevice = getFTDevice();

            try {
                configureDevice(ftDevice);
            }
            catch (FTD2XXException ex) {
                LOGGER.warn("Configure the device failed.", ex);
            }

            LOGGER.info("Started the receiver thread.");

            createEventHandle();

            int eventMask = FTD2XX.NotificationEvents.FT_EVENT_RXCHAR | FTD2XX.NotificationEvents.FT_EVENT_MODEM_STATUS;

            while (receiverRunning.get()) {

                try {
                    LOGGER.debug("Try to read data");

                    waitForNotificationEvent(ftDevice, eventMask);

                    int len = readData(ftDevice);

                    if (len < 0) {
                        // check if the port was closed.
                        boolean portClosed = !ftDevice.isOpen();
                        LOGGER.info("Port closed: {}", portClosed);

                        if (portClosed) {
                            // say good-bye
                            LOGGER.info("The port is closed. Leave the receiver loop.");

                            receiverRunning.set(false);
                            continue;
                        }
                    }

                    if (len > 0) {
                        output.write(inputBuffer, 0, len);

                        if (LOGGER.isDebugEnabled()) {
                            LOGGER
                                .debug("<<<< len: {}, data: {}", output.size(),
                                    ByteUtils.bytesToHex(output.toByteArray()));
                        }

                        addDataToReceiveQueue(output);

                        if (output != null && output.size() > 0) {
                            LOGGER.warn("Data in output: {}", output.toString());
                        }

                    }
                }
                catch (FTD2XXException ex) {
                    LOGGER.warn("Receive data failed with an exception!", ex);

                    receiverRunning.set(false);

                    if (ftDevice == null || ftDevice.isOpen()) {
                        triggerClosePort();
                    }
                }
                catch (NullPointerException ex) {
                    LOGGER.error("Receive data failed with an NPE! The port might be closed.", ex);

                    receiverRunning.set(false);
                }
                catch (Exception ex) {
                    LOGGER.error("Message receiver returned from receive with an exception!", ex);
                }

            }

            closeHandle();

            LOGGER.info("Leaving receiver loop.");
        }
    }

    public void close() {

        if (this.device != null) {

            LOGGER.info("Close the port.");
            long start = System.currentTimeMillis();

            final FTDevice portToClose = this.device;
            this.device = null;

            LOGGER.info("Set the receiver running flag to false.");
            receiverRunning.set(false);

            try {
                portToClose.close();
            }
            catch (IOException ex) {
                LOGGER.warn("Close serial port failed.", ex);
            }

            stopReceiverThread();
            stopReceiveQueueWorker();

            long end = System.currentTimeMillis();
            LOGGER.info("Closed the port. duration: {}", end - start);

        }
        else {
            LOGGER.info("No port to close available.");
        }

    }

    protected void triggerClosePort() {
        LOGGER.warn("Close the port.");
        Thread worker = new Thread(() -> {

            LOGGER.info("Start close port because error was detected.");
            try {
                // the listeners are notified in close()
                close();
            }
            catch (Exception ex) {
                LOGGER.warn("Close after error failed.", ex);
            }
            LOGGER.warn("The port was closed.");
        });
        worker.start();
    }

    public void startReceiverAndQueues() {
        LOGGER.info("Start receiver and queues.");

        if (receiverThread == null) {
            receiverThread = createReceiverThread();
        }

        receiverThread.start();

        startReceiveQueueWorker();
    }

    private void startReceiveQueueWorker() {
        receiverRunning.set(true);

        LOGGER.info("Start the receiveQueueWorker. Current receiveQueueWorker: {}", receiveQueueWorker);
        receiveQueueWorker = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    processReceiveQueue();
                }
                catch (Exception ex) {
                    LOGGER.warn("The processing of the receive queue was terminated with an exception!", ex);
                }

                LOGGER.info("Process receive queue has finished.");
            }
        }, "receiveQueueWorker");

        try {
            receiveQueueWorkerThreadId.set(receiveQueueWorker.getId());
            receiveQueueWorker.start();
        }
        catch (Exception ex) {
            LOGGER.error("Start the receiveQueueWorker failed.", ex);
        }

        LOGGER.info("Start the receiveQueueWorker finished. Current receiveQueueWorker: {}", receiveQueueWorker);
    }

    private void stopReceiveQueueWorker() {
        LOGGER.info("Stop the receive queue worker.");
        receiverRunning.set(false);

        try {
            receiveQueueWorker.interrupt();

            receiveQueueWorker.join(1000);

            LOGGER.info("receiveQueueWorker has finished.");
        }
        catch (Exception ex) {
            LOGGER.warn("Interrupt receiveQueueWorker failed.", ex);
        }
        receiveQueueWorker = null;
    }

    private void processReceiveQueue() {
        byte[] bytes = null;
        LOGGER.info("The receiveQueueWorker is ready for processing, requestedPortName: {}", requestedPortName);

        while (receiverRunning.get()) {
            try {
                // get the message to process
                bytes = receiveQueue.take();

                if (bytes != null) {
                    // process
                    try {

                        processMessages(bytes);
                    }
                    catch (Exception ex) {
                        LOGGER.warn("Process received bytes failed.", ex);
                    }

                }
            }
            catch (InterruptedException ex) {
                LOGGER.warn("Get message from receiveQueue failed because thread was interrupted.");
            }
            catch (Exception ex) {
                LOGGER.warn("Get message from receiveQueue failed.", ex);
                bytes = null;
            }

        }

        LOGGER.info("The receiveQueueWorker has finished processing, requestedPortName: {}", requestedPortName);
        receiveQueueWorkerThreadId.set(0);
    }

    private void stopReceiverThread() {
        LOGGER.info("Stop the receiver thread by set the running flag to false.");
        receiverRunning.set(false);

        if (receiverThread != null) {
            LOGGER.info("Wait for termination of receiver thread.");

            synchronized (receiverThread) {

                // close handle will release the event
                closeHandle();

                try {
                    receiverThread.join(5000);
                }
                catch (InterruptedException ex) {
                    LOGGER.warn("Wait for termination of receiver thread failed.", ex);
                }
            }

            LOGGER.info("Free the receiver thread.");
            receiverThread = null;
        }
    }

    private void addDataToReceiveQueue(final ByteArrayOutputStream output) {

        byte[] bytes = output.toByteArray();

        byte[] buffer = new byte[bytes.length];
        System.arraycopy(bytes, 0, buffer, 0, bytes.length);

        if (LOGGER.isDebugEnabled()) {
            LOGGER
                .debug("<<<< len: {}, data: {}, string: {}", bytes.length, ByteUtils.bytesToHex(buffer),
                    new String(bytes));
        }

        boolean added = receiveQueue.offer(buffer);
        if (!added) {
            LOGGER.error("The message was not added to the receive queue: {}", ByteUtils.bytesToHex(buffer));
        }

        output.reset();
    }

}
