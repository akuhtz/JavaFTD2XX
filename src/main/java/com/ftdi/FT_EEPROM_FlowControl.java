package com.ftdi;

public enum FT_EEPROM_FlowControl {
    NONE((byte) 0),
    RTS_CTS((byte) 0x0100),
    DTR_DSR((byte) 0x0200),
    XON_XOFF((byte) 0x0400);

    byte value;

    FT_EEPROM_FlowControl(byte value) {
        this.value = value;
    }

    public static FT_EEPROM_FlowControl byValue(byte value) {
        for (FT_EEPROM_FlowControl fc : values()) {
            if (value == fc.value)
                return fc;
        }

        return null;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }
}
