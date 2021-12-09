package com.ftdi.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftdi.AbstractEventNotificationHandler;
import com.ftdi.FTD2XXException;
import com.ftdi.FTDevice;
import com.sun.jna.Pointer;
import com.sun.jna.platform.unix.LibPThread;
import com.sun.jna.platform.unix.LibPThread.EVENT_HANDLE;

public class EventNotificationHandlerLinux extends AbstractEventNotificationHandler<EVENT_HANDLE> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventNotificationHandlerLinux.class);

    private static final LibPThread libPThreadExt = LibPThread.INSTANCE;

    @Override
    protected void createEventHandle() {

        EVENT_HANDLE hEvent = new EVENT_HANDLE();

        int retVal = libPThreadExt.pthread_mutex_init(hEvent.eMutex.getPointer(), Pointer.NULL);
        LOGGER.info("Initialized the mutex: {} @ {}", retVal, hEvent.eMutex.getPointer());
        retVal = libPThreadExt.pthread_cond_init(hEvent.eCondVar.getPointer(), Pointer.NULL);
        LOGGER.info("Initialized the condVar: {} @ {}", retVal, hEvent.eCondVar.getPointer());

        setEventHandle(hEvent);
    }

    @Override
    protected void waitForNotificationEvent(FTDevice ftDevice, int eventMask) throws FTD2XXException {
        LOGGER.info("SetEventNotification for event-handle @ {}", eventHandle.getPointer());

        ftDevice.SetEventNotification(eventHandle.getPointer(), eventMask);

        int retVal = libPThreadExt.pthread_mutex_lock(eventHandle.eMutex.getPointer());
        LOGGER.info("Lock the mutex: {}", retVal);
        retVal = libPThreadExt.pthread_cond_wait(eventHandle.eCondVar.getPointer(), eventHandle.eMutex.getPointer());
        LOGGER.info("Wait on cond: {}", retVal);
        retVal = libPThreadExt.pthread_mutex_unlock(eventHandle.eMutex.getPointer());
        LOGGER.info("Unlock the mutex: {}", retVal);

    }

    @Override
    protected void closeHandle() {
        LOGGER.info("Close the handle: {}", eventHandle);

        if (eventHandle != null) {

            int retVal = libPThreadExt.pthread_cond_signal(eventHandle.eCondVar.getPointer());
            LOGGER.info("Signal the cond: {}", retVal);

            libPThreadExt.pthread_cond_destroy(eventHandle.eCondVar.getPointer());
            libPThreadExt.pthread_mutex_destroy(eventHandle.eMutex.getPointer());
            eventHandle = null;
        }
    }

    @Override
    protected void processMessages(byte[] bytes) {
        // TODO Auto-generated method stub

    }

}
