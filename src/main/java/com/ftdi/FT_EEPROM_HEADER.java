package com.ftdi;

import com.sun.jna.Structure;

/**
 * Ftdi EEPROM common header
 *
 * Please see D2XX Programmer's Guide for more information.
 *
 * @author Lukasz Chlebowski <lchlebowski@meden.com.pl>
 */
@Structure.FieldOrder({"deviceType",
        "vendorId",
        "productId",
        "serNumEnable",
        "maxPower",
        "selfPowered",
        "remoteWakeup",
        "pullDownEnable"})
public class FT_EEPROM_HEADER extends Structure {
    public int deviceType = 0;
    public short vendorId;
    public short productId;
    public byte serNumEnable;
    public short maxPower;
    public byte selfPowered;
    public byte remoteWakeup;
    public byte pullDownEnable;

    public FT_EEPROM_HEADER() {
    }
}
