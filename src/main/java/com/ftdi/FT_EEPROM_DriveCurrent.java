package com.ftdi;

public enum FT_EEPROM_DriveCurrent {
    mA4((byte) 4),
    mA8((byte) 8),
    ma12((byte) 12),
    mA16((byte) 16);

    byte value;

    FT_EEPROM_DriveCurrent(byte value) {
        this.value = value;
    }

    public static FT_EEPROM_DriveCurrent byValue(int value) {
        for (FT_EEPROM_DriveCurrent dc : values()) {
            if (dc.value == value)
                return dc;
        }

        return null;
    }

    byte getValue() {
        return value;
    }

    void setValue(byte value) {
        this.value = value;
    }
}
