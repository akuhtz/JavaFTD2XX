package com.ftdi;

import com.sun.jna.Structure;

/**
 * Ftdi X-Series specific EEPROM data
 *
 * Please see D2XX Programmer's Guide for more information.
 *
 * @author Lukasz Chlebowski <lchlebowski@meden.com.pl>
 */

@Structure.FieldOrder({
        "header",
        "acSlowSlew",
        "acSchmittInput",
        "acDriveCurrent",
        "adSlowSlew",
        "adSchmittInput",
        "adDriveCurrent",
        "cBus",
        "invertTxd",
        "invertRxd",
        "invertRTS",
        "invertCTS",
        "invertDTR",
        "invertDSR",
        "invertDCD",
        "invertRI",
        "bcdEnable",
        "bcdForceCBusPWREN",
        "bcdDisableSleep",
        "I2CSlaveAddress",
        "I2CDeviceId",
        "I2CDisableSchmitt",
        "FT1248Cpol",
        "FT1248Lsb",
        "FT1248FlowControl",
        "RS485EchoSuppress",
        "powerSaveEnable",
        "driverType"})
public class FT_EEPROM_X_SERIES extends Structure {
    public FT_EEPROM_HEADER header;
    // Drive options
    public byte acSlowSlew;
    public byte acSchmittInput;
    public byte acDriveCurrent;
    public byte adSlowSlew;
    public byte adSchmittInput;
    public byte adDriveCurrent;
    // CBUS Options
    public byte[] cBus = new byte[7];

    // UART signal options
    public byte invertTxd;
    public byte invertRxd;
    public byte invertRTS;
    public byte invertCTS;
    public byte invertDTR;
    public byte invertDSR;
    public byte invertDCD;
    public byte invertRI;
    // Battery Charge Detect options
    public byte bcdEnable;
    public byte bcdForceCBusPWREN;
    public byte bcdDisableSleep;
    // I2C options
    public short I2CSlaveAddress;
    public int I2CDeviceId;
    public byte I2CDisableSchmitt;
    // FT1248 options
    public byte FT1248Cpol;
    public byte FT1248Lsb;
    public byte FT1248FlowControl;
    // Hardware options
    public byte RS485EchoSuppress;
    public byte powerSaveEnable;
    // Driver option
    public byte driverType;

    public FT_EEPROM_X_SERIES() {
        header.deviceType = DeviceType.DEVICE_X_SERIES.constant();
    }

    public enum CBUS {
        TRISTATE(0x00),
        RXLED(0x01),
        TXLED(0x02),
        TXRXLED(0x03),
        PWREN(0x04),
        SLEEP(0x05),
        DRIVE_0(0x06),
        DRIVE_1(0x07),
        IOMODE(0x08),
        TXDEN(0x09),
        CLK24(0x0A),
        CLK12(0x0B),
        CLK6(0x0C),
        BCD_CHARGER(0x0D),
        BCD_CHARGER_N(0x0E),
        I2C_TXE(0x0F),
        I2C_RXF(0x10),
        VBUS_SENSE(0x11),
        BITBANG_WR(0x12),
        BITBANG_RD(0x13),
        TIMESTAMP(0x14),
        KEEP_AWAKE(0x15);

        int code;

        CBUS(int code) {
            this.code = code;
        }

        public static CBUS byCode(int code) {
            for (CBUS c : values()) {
                if (code == c.getCode())
                    return c;
            }

            return null;
        }

        int getCode() {
            return code;
        }
    }
}
