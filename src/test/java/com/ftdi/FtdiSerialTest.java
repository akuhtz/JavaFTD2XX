package com.ftdi;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftdi.handler.BlockingReaderHandler;
import com.ftdi.handler.EventNotificationHandlerLinux;
import com.ftdi.handler.EventNotificationHandlerWindows;
import com.ftdi.utils.ByteUtils;
import com.sun.jna.Native;
import com.sun.jna.Platform;

public class FtdiSerialTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtdiSerialTest.class);

    @Test
    public void ftdiEventTest() {

        int size_t = Native.SIZE_T_SIZE;
        int size_long = Native.LONG_SIZE;
        LOGGER.info("size_t: {}, size_long: {}", size_t, size_long);

        long baudRate = 19200;

        try {
            LOGGER.info("List the FTDevices in the system.");
            List<FTDevice> devices = FTDevice.getDevices(true);
            if (devices != null && devices.size() > 0) {
                FTDevice ftDevice = devices.get(0);
                LOGGER.info("Current ftDevice: {}", ftDevice);

                final CountDownLatch received = new CountDownLatch(1);

                AbstractEventNotificationHandler<?> handler = null;

                try {
                    ftDevice.open();

                    ftDevice.setBitMode((byte) 0, BitModes.BITMODE_RESET);

                    ftDevice.setFlowControl(FlowControl.FLOW_NONE);
                    // ftDevice.setFlowControl(FlowControl.FLOW_RTS_CTS);
                    ftDevice.setBaudRate(baudRate);
                    ftDevice.setDataCharacteristics(WordLength.BITS_8, StopBits.STOP_BITS_1, Parity.PARITY_NONE);

                    ftDevice.setUSBParameters(128, 64);

                    // ftDevice.setTimeouts(5, 500);

                    ftDevice.purgeBuffer(true, true);

                    if (Platform.isWindows()) {
                        handler = new EventNotificationHandlerWindows() {
                            @Override
                            protected void processMessages(byte[] bytes) {
                                LOGGER.info("Received data: {}", ByteUtils.bytesToHex(bytes));

                                received.countDown();
                            }
                        };
                    }
                    else if (Platform.isLinux()) {
                        handler = new EventNotificationHandlerLinux() {
                            @Override
                            protected void processMessages(byte[] bytes) {
                                LOGGER.info("Received data: {}", ByteUtils.bytesToHex(bytes));

                                received.countDown();
                            }
                        };
                    }
                    else {
                        LOGGER.error("Unsupported platform detected.");
                        throw new IllegalArgumentException("Unsupported platform detected.");
                    }
                    handler.setFTDevice(ftDevice);

                    handler.startReceiverAndQueues();

                    Thread.sleep(100);

                    send(ftDevice, "Hello World!", LineEndingEnum.CRLF);

                    received.await(1000, TimeUnit.MILLISECONDS);
                    // Thread.sleep(300);

                    Assertions.assertEquals(0L, received.getCount(), "No data received!");

                }
                finally {

                    if (handler != null) {
                        LOGGER.info("Close handler: {}", handler);
                        handler.close();
                    }

                    if (ftDevice != null && ftDevice.isOpen()) {
                        LOGGER.info("Close device: {}", ftDevice);
                        ftDevice.close();
                    }
                }
            }
        }
        catch (Exception ex) {
            LOGGER.warn("List FTDI devices failed.", ex);
        }
    }

    @Test
    @Disabled
    public void ftdiBlockTest() {

        long baudRate = 19200;

        try {
            LOGGER.info("List the FTDevices in the system.");
            List<FTDevice> devices = FTDevice.getDevices(true);
            if (devices != null && devices.size() > 0) {
                FTDevice ftDevice = devices.get(0);
                LOGGER.info("Current ftDevice: {}", ftDevice);

                final CountDownLatch received = new CountDownLatch(1);

                AbstractDataHandler handler = null;

                try {
                    ftDevice.open();

                    ftDevice.setBitMode((byte) 0, BitModes.BITMODE_RESET);

                    ftDevice.setFlowControl(FlowControl.FLOW_NONE);
                    // ftDevice.setFlowControl(FlowControl.FLOW_RTS_CTS);
                    ftDevice.setBaudRate(baudRate);
                    ftDevice.setDataCharacteristics(WordLength.BITS_8, StopBits.STOP_BITS_1, Parity.PARITY_NONE);

                    ftDevice.setUSBParameters(128, 64);

                    ftDevice.setTimeouts(5, 500);

                    ftDevice.purgeBuffer(true, true);

                    handler = new BlockingReaderHandler() {
                        @Override
                        protected void processMessages(byte[] bytes) {
                            LOGGER.info("Received data: {}", ByteUtils.bytesToHex(bytes));

                            received.countDown();
                        }
                    };

                    handler.setFTDevice(ftDevice);

                    handler.startReceiverAndQueues();

                    Thread.sleep(100);

                    send(ftDevice, "Hello World!", LineEndingEnum.CRLF);

                    received.await(1000, TimeUnit.MILLISECONDS);

                    Assertions.assertEquals(0L, received.getCount(), "No data received!");

                }
                finally {

                    if (handler != null) {
                        LOGGER.info("Close handler: {}", handler);
                        handler.close();
                    }

                    if (ftDevice != null && ftDevice.isOpen()) {
                        LOGGER.info("Close device: {}", ftDevice);
                        ftDevice.close();
                    }
                }
            }
        }
        catch (Exception ex) {
            LOGGER.warn("List FTDI devices failed.", ex);
        }
    }

    private void send(final FTDevice ftDevice, String message, LineEndingEnum lineEnding) {
        try {

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info(">> [{}] - {}", message, ByteUtils.bytesToHex(message.getBytes()));
            }

            int written =
                ftDevice
                    .write(message.getBytes(StandardCharsets.UTF_8), 0,
                        message.getBytes(StandardCharsets.UTF_8).length);
            if (lineEnding != null) {
                written += ftDevice.write(lineEnding.getValues(), 0, lineEnding.getValues().length);
            }
            LOGGER.info("Data written: {}", written);
        }
        catch (FTD2XXException ex) {
            throw new RuntimeException("Send message to device failed.", ex);
        }
    }

    private void send(final FTDevice ftDevice, byte[] content) {
        try {

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info(">> [{}] - {}", content.length, ByteUtils.bytesToHex(content));
            }

            int written = ftDevice.write(content, 0, content.length);
            LOGGER.info("Data written: {}", written);
        }
        catch (FTD2XXException ex) {
            throw new RuntimeException("Send message to device failed.", ex);
        }
    }
}
