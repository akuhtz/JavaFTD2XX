package com.ftdi;

public class EepromX {
    FT_EEPROM_X_SERIES eeprom = new FT_EEPROM_X_SERIES();
    EepromHeaderControler headerControler;

    String manufacturer;
    String manufacturerId;
    String description;
    String serialNumber;

    public EepromX() {
        headerControler = new EepromHeaderControler(eeprom.header);
    }

    public EepromHeaderControler getHeader() {
        return headerControler;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public EepromX setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public EepromX setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EepromX setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public EepromX setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public boolean getAcSlowSlew() {
        return eeprom.acSlowSlew > 0 ? true : false;
    }

    public EepromX setAcSlowSlew(boolean acSlowSlew) {
        this.eeprom.acSlowSlew = (byte) (acSlowSlew ? 1 : 0);
        return this;
    }

    public boolean getAcSchmittInput() {
        return eeprom.acSchmittInput > 0 ? true : false;
    }

    public EepromX setAcSchmittInput(boolean acSchmittInput) {
        this.eeprom.acSchmittInput = (byte) (acSchmittInput ? 1 : 0);
        return this;
    }

    public FT_EEPROM_DriveCurrent getAcDriveCurrent() {
        return FT_EEPROM_DriveCurrent.byValue(eeprom.acDriveCurrent);
    }

    public EepromX setAcDriveCurrent(FT_EEPROM_DriveCurrent acDriveCurrent) {
        this.eeprom.acDriveCurrent = acDriveCurrent.getValue();
        return this;
    }

    public boolean getAdSlowSlew() {
        return eeprom.adSlowSlew > 0 ? true : false;
    }

    public EepromX setAdSlowSlew(boolean adSlowSlew) {
        this.eeprom.adSlowSlew = (byte) (adSlowSlew ? 1 : 0);
        return this;
    }

    public boolean getAdSchmittInput() {
        return eeprom.adSchmittInput > 0 ? true : false;
    }

    public EepromX setAdSchmittInput(boolean adSchmittInput) {
        this.eeprom.adSchmittInput = (byte) (adSchmittInput ? 1 : 0);
        return this;
    }

    public FT_EEPROM_DriveCurrent getAdDriveCurrent() {
        return FT_EEPROM_DriveCurrent.byValue(eeprom.adDriveCurrent);
    }

    public EepromX setAdDriveCurrent(FT_EEPROM_DriveCurrent adDriveCurrent) {
        this.eeprom.adDriveCurrent = adDriveCurrent.getValue();
        return this;
    }

    public FT_EEPROM_X_SERIES.CBUS getCBus(int pin) {
        return FT_EEPROM_X_SERIES.CBUS.byCode(eeprom.cBus[pin]);
    }

    public EepromX setCBus(int pin, FT_EEPROM_X_SERIES.CBUS cbus) {
        eeprom.cBus[pin] = (byte) cbus.getCode();
        return this;
    }

    public boolean getInvertTxd() {
        return eeprom.invertTxd > 0 ? true : false;
    }

    public EepromX setInvertTxd(boolean invertTxd) {
        this.eeprom.invertTxd = (byte) (invertTxd ? 1 : 0);
        return this;
    }

    public boolean getInvertRxd() {
        return eeprom.invertRxd > 0 ? true : false;
    }

    public EepromX setInvertRxd(boolean invertRxd) {
        this.eeprom.invertRxd = (byte) (invertRxd ? 1 : 0);
        return this;
    }

    public boolean getInvertRTS() {
        return eeprom.invertRTS > 0 ? true : false;
    }

    public EepromX setInvertRTS(boolean invertRTS) {
        this.eeprom.invertRTS = (byte) (invertRTS ? 1 : 0);
        return this;
    }

    public boolean getInvertCTS() {
        return eeprom.invertCTS > 0 ? true : false;
    }

    public EepromX setInvertCTS(boolean invertCTS) {
        this.eeprom.invertCTS = (byte) (invertCTS ? 1 : 0);
        return this;
    }

    public boolean getInvertDTR() {
        return eeprom.invertDTR > 0 ? true : false;
    }

    public EepromX setInvertDTR(boolean invertDTR) {
        this.eeprom.invertDTR = (byte) (invertDTR ? 1 : 0);
        return this;
    }

    public boolean getInvertDSR() {
        return eeprom.invertDSR > 0 ? true : false;
    }

    public EepromX setInvertDSR(boolean invertDSR) {
        this.eeprom.invertDSR = (byte) (invertDSR ? 1 : 0);
        return this;
    }

    public boolean getInvertDCD() {
        return eeprom.invertDCD > 0 ? true : false;
    }

    public EepromX setInvertDCD(boolean invertDCD) {
        this.eeprom.invertDCD = (byte) (invertDCD ? 1 : 0);
        return this;
    }

    public boolean getInvertRI() {
        return eeprom.invertRI > 0 ? true : false;
    }

    public EepromX setInvertRI(boolean invertRI) {
        this.eeprom.invertRI = (byte) (invertRI ? 1 : 0);
        return this;
    }

    public boolean getBcdEnable() {
        return eeprom.bcdEnable > 0 ? true : false;
    }

    public EepromX setBcdEnable(boolean bcdEnable) {
        this.eeprom.bcdEnable = (byte) (bcdEnable ? 1 : 0);
        return this;
    }

    public boolean getBcdForceCBusPWREN() {
        return eeprom.bcdForceCBusPWREN > 0 ? true : false;
    }

    public EepromX setBcdForceCBusPWREN(boolean bcdForceCBusPWREN) {
        this.eeprom.bcdForceCBusPWREN = (byte) (bcdForceCBusPWREN ? 1 : 0);
        return this;
    }

    public boolean getBcdDisableSleep() {
        return eeprom.bcdDisableSleep > 0 ? true : false;
    }

    public EepromX setBcdDisableSleep(boolean bcdDisableSleep) {
        this.eeprom.bcdDisableSleep = (byte) (bcdDisableSleep ? 1 : 0);
        return this;
    }

    public short getI2CSlaveAddress() {
        return eeprom.I2CSlaveAddress;
    }

    public EepromX setI2CSlaveAddress(short i2CSlaveAddress) {
        eeprom.I2CSlaveAddress = i2CSlaveAddress;
        return this;
    }

    public int getI2CDeviceId() {
        return eeprom.I2CDeviceId;
    }

    public EepromX setI2CDeviceId(int i2CDeviceId) {
        eeprom.I2CDeviceId = i2CDeviceId;
        return this;
    }

    public boolean getI2CDisableSchmitt() {
        return eeprom.I2CDisableSchmitt > 0 ? true : false;
    }

    public EepromX setI2CDisableSchmitt(boolean i2CDisableSchmitt) {
        eeprom.I2CDisableSchmitt = (byte) (i2CDisableSchmitt ? 1 : 0);
        return this;
    }

    public FT_EEPROM_CPol getFT1248Cpol() {
        return FT_EEPROM_CPol.values()[eeprom.FT1248Cpol];
    }

    public void setFT1248Cpol(FT_EEPROM_CPol FT1248Cpol) {
        this.eeprom.FT1248Cpol = (byte) FT1248Cpol.ordinal();
    }

    public boolean getFT1248Lsb() {
        return eeprom.FT1248Lsb > 0 ? true : false;
    }

    public void setFT1248Lsb(boolean FT1248Lsb) {
        this.eeprom.FT1248Lsb = (byte) (FT1248Lsb ? 1 : 0);
    }

    public FT_EEPROM_FlowControl getFT1248FlowControl() {
        return FT_EEPROM_FlowControl.byValue(eeprom.FT1248FlowControl);
    }

    public void setFT1248FlowControl(FT_EEPROM_FlowControl FT1248FlowControl) {
        this.eeprom.FT1248FlowControl = FT1248FlowControl.getValue();
    }

    public boolean getRS485EchoSuppress() {
        return eeprom.RS485EchoSuppress > 0 ? true : false;
    }

    public void setRS485EchoSuppress(byte RS485EchoSuppress) {
        this.eeprom.RS485EchoSuppress = RS485EchoSuppress;
    }

    public boolean getPowerSaveEnable() {
        return eeprom.powerSaveEnable > 0 ? true : false;
    }

    public void setPowerSaveEnable(boolean powerSaveEnable) {
        this.eeprom.powerSaveEnable = (byte) (powerSaveEnable ? 1 : 0);
    }

    public EepromX setVCP(boolean enable) {
        byte value = (byte) (enable ? 1 : 0);
        eeprom.driverType = value;
        return this;
    }
}
