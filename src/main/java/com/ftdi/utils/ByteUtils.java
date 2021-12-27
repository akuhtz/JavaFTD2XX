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

    /**
     * Returns the low byte of an int value.
     * 
     * @param value
     *            the value
     * @return the low byte
     */
    public static byte getLowByte(int value) {
        byte lowByte = getLowByte(value, 0xFF);
        return lowByte;
    }

    /**
     * Returns the low byte of an int value.
     * 
     * @param value
     *            the value
     * @param mask
     *            the mask
     * @return the low byte
     */
    public static byte getLowByte(int value, int mask) {
        byte lowByte = (byte) (value & mask);
        return lowByte;
    }

    /**
     * Concat a byte array.
     * 
     * @param array1
     *            the first array
     * @param array2
     *            the second array
     * @return the concatenated array
     */
    public static byte[] concat(byte[] array1, byte[] array2) {
        byte[] result = new byte[array1.length + array2.length];

        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

}
