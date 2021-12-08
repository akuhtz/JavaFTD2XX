package com.ftdi.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftdi.AbstractEventNotificationHandler;
import com.ftdi.FTD2XXException;
import com.ftdi.FTDevice;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public class EventNotificationHandlerWindows extends AbstractEventNotificationHandler<HANDLE> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventNotificationHandlerWindows.class);

    static private final Kernel32 kernel32 = Kernel32.INSTANCE;

    @Override
    protected void createEventHandle() {

        WinBase.SECURITY_ATTRIBUTES lpEventAttributes = null;
        final HANDLE hEvent = kernel32.CreateEvent(lpEventAttributes, false, false, "");

        setEventHandle(hEvent);
    }

    @Override
    protected void waitForNotificationEvent(final FTDevice ftDevice, int eventMask) throws FTD2XXException {

        ftDevice.SetEventNotification(eventHandle.getPointer(), eventMask);
        kernel32.WaitForSingleObject(eventHandle, -1);

    }

    @Override
    protected void closeHandle() {
        LOGGER.info("Close the handle: {}", eventHandle);

        if (eventHandle != null) {
            kernel32.SetEvent(eventHandle);

            kernel32.CloseHandle(eventHandle);
            eventHandle = null;
        }
    }

    @Override
    protected void processMessages(byte[] bytes) {

    }

}
