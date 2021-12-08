package com.ftdi.handler;

import com.ftdi.AbstractDataHandler;
import com.ftdi.FTD2XXException;
import com.ftdi.FTDevice;

public class BlockingReaderHandler extends AbstractDataHandler {

    @Override
    protected void configureDevice(final FTDevice ftDevice) throws FTD2XXException {
        ftDevice.setTimeouts(5, 100);
    }

    @Override
    protected void waitForNotificationEvent(FTDevice ftDevice, int eventMask) throws FTD2XXException {
        // not used
    }

    @Override
    protected void closeHandle() {
        // not used
    }

    @Override
    protected void processMessages(byte[] bytes) {

    }

    @Override
    protected void createEventHandle() {
        // not used
    }

    @Override
    protected int readData(FTDevice ftDevice) throws FTD2XXException {
        int len = ftDevice.read(inputBuffer);
        return len;
    }

}
