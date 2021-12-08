package com.ftdi;

public enum LineEndingEnum {
    CRLF("crlf", new byte[] { 0x0D & 0xFF, 0x0A & 0xFF }), CR("cr", new byte[] { 0x0D & 0xFF }), LF("lf",
        new byte[] { 0x0A & 0xFF });

    private final String key;

    private final byte[] values;

    private LineEndingEnum(String key, byte[] values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public byte[] getValues() {
        return values;
    }
}
