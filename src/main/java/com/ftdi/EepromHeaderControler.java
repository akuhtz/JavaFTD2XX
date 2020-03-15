package com.ftdi;

public class EepromHeaderControler {
    private final FT_EEPROM_HEADER header;

    public EepromHeaderControler(FT_EEPROM_HEADER header) {
        this.header = header;
    }

    public DeviceType getDeviceType() {
        return DeviceType.values()[header.deviceType];
    }

    public EepromHeaderControler setDeviceType(DeviceType deviceType) {
        header.deviceType = deviceType.constant();
        return this;
    }

    public short getVendorId() {
        return header.vendorId;
    }

    public EepromHeaderControler setVendorId(short vendorId) {
        header.vendorId = vendorId;
        return this;
    }

    public short getProductId() {
        return header.productId;
    }

    public EepromHeaderControler setProductId(short productId) {
        header.productId = productId;
        return this;
    }

    public byte getSerNumEnable() {
        return header.serNumEnable;
    }

    public EepromHeaderControler setSerNumEnable(byte serNumEnable) {
        header.serNumEnable = serNumEnable;
        return this;
    }

    public short getMaxPower() {
        return header.maxPower;
    }

    public EepromHeaderControler setMaxPower(short maxPower) {
        header.maxPower = maxPower;
        return this;
    }

    public boolean getSelfPowered() {
        return header.selfPowered > 0 ? true : false;
    }

    public EepromHeaderControler setSelfPowered(boolean selfPowered) {
        header.selfPowered = (byte) (selfPowered ? 1 : 0);
        return this;
    }

    public boolean getRemoteWakeup() {
        return header.remoteWakeup > 0 ? true : false;
    }

    public EepromHeaderControler setRemoteWakeup(boolean remoteWakeup) {
        header.remoteWakeup = (byte) (remoteWakeup ? 1 : 0);
        return this;
    }

    public boolean getPullDownEnable() {
        return header.pullDownEnable > 0 ? true : false;
    }

    public EepromHeaderControler setPullDownEnable(boolean pullDownEnable) {
        header.pullDownEnable = (byte) (pullDownEnable ? 1 : 0);
        return this;
    }
}
