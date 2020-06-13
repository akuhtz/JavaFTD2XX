package com.ftdi;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EnabledOnOs({ OS.WINDOWS })
public class FtdiDeviceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FtdiDeviceTest.class);

    @Test
    public void ftdiTest() {
        try {
            LOGGER.info("List the FTDevices in the system.");
            List<FTDevice> devices = FTDevice.getDevices(true);
            if (devices != null) {
                for (FTDevice device : devices) {
                    LOGGER.info("Current device: {}", device);

                    try {
                        device.open();
                        int userAreaSize = device.getEEPROMUserAreaSize();
                        EEPROMData eepromData = device.readEEPROM();
                        LOGGER.info("userAreaSize: {}, eepromData: {}", userAreaSize, eepromData);
                        // byte[] userAreaData = device.readEEPROMUserArea(50);
                        // String userAreaDataString = device.readFullEEPROMUserAreaAsString();
                        // LOGGER.info("userAreaSize: {}, userAreaData: {}", userAreaSize, userAreaData);

                        LOGGER.info("manufacturer: {}", eepromData.getManufacturer());
                        LOGGER.info("manufacturerID: {}", eepromData.getManufacturerID());
                        LOGGER.info("description: {}", eepromData.getDescription());
                        LOGGER.info("serialNumber: {}", eepromData.getSerialNumber());
                        LOGGER.info("maxPower: {}", eepromData.getMaxPower());
                        LOGGER.info("PnP: {}", eepromData.isPnP());
                        LOGGER.info("selfPowered: {}", eepromData.isSelfPowered());
                        LOGGER.info("remoteWakeup: {}", eepromData.isRemoteWakeup());

                        // eepromData.setDescription("BiDiB-IF2");
                        // LOGGER.info("Change the description: {}", eepromData.getDescription());
                        //
                        // device.writeEEPROM(eepromData);
                        //
                        // device.cyclePort();
                    }
                    finally {
                        device.close();
                    }
                }
            }
        }
        catch (Exception ex) {
            LOGGER.warn("List FTDI devices failed.", ex);
        }

    }
}
