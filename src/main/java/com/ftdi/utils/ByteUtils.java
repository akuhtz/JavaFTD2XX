package com.ftdi.utils;

public class ByteUtils {

    private final static char[] HEXARRAY = "0123456789ABCDEF".toCharArray();

    /**
     * Convert a byte array to a hex string with spaces delimited. Original source: DatatypeConverterImpl.printHexBinary
     * 
     * @param bytes
     *            the byte array
     * @return the formatted hex string
     */
    public static String bytesToHex(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        StringBuilder r = new StringBuilder(bytes.length * 3);
        for (byte b : bytes) {
            r.append(HEXARRAY[(b >> 4) & 0xF]);
            r.append(HEXARRAY[(b & 0xF)]);
            r.append(' ');
        }
        return r.substring(0, r.length() - 1);
    }

}
