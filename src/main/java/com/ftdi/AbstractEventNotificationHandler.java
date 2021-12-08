package com.ftdi;

import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEventNotificationHandler<T> extends AbstractDataHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEventNotificationHandler.class);

    protected T eventHandle;

    @Override
    protected int readData(final FTDevice ftDevice) throws FTD2XXException {
        // LOGGER.info("After wait for read data");
        int len = -1;
        int[] status = ftDevice.getStatus();

        if ((status[2]
            & FTD2XX.NotificationEvents.FT_EVENT_MODEM_STATUS) == FTD2XX.NotificationEvents.FT_EVENT_MODEM_STATUS) {

            LOGGER.info("Received modem or line status event: {}", status[2]);

            final EnumSet<DeviceStatus> deviceStatusSet = ftDevice.getDeviceStatus();

            if (deviceStatusSet.contains(DeviceStatus.CTS)) {
                LOGGER.info(">>>> CTS is high.");
            }
            else {
                LOGGER.info(">>>> CTS is low.");
            }

            if (deviceStatusSet.contains(DeviceStatus.DSR)) {
                LOGGER.info(">>>> DSR is high.");
            }
            else {
                LOGGER.info(">>>> DSR is low.");
            }

        }

        int available = status[0];

        if (available > 0) {
            len = ftDevice.read(inputBuffer, 0, available);

            LOGGER.info("Read len: {}", len);
        }
        return len;
    }

    protected void setEventHandle(T eventHandle) {
        this.eventHandle = eventHandle;
    }
}
