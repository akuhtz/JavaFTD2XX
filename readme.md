# JavaFTD2XX

[![Build Status](https://github.com/akuhtz/JavaFTD2XX/workflows/Java%20CI/badge.svg)](https://github.com/akuhtz/JavaFTD2XX/actions)
[![Maven Central](https://img.shields.io/maven-central/v/org.bidib.com.ftdi/FTD2xxJ.svg?label=Maven%20Central&style=flat)](https://search.maven.org/artifact/org.bidib.com.ftdi/FTD2xxJ)

This project is a Java wrapper for the FTD2XX driver of FTDI. The drivers are provided by JavaFTD2XX and no native installation is required.

Since 0.3.6 it supports the usage of event notification for receiving data.

## Linux

To get access to the FTDI device under Linux you must remove the modules `ftdi_sio` and `usbserial`  as stated in  _AN_220 FTDI Drivers Installation Guide for Linux_ .

Downloads:
 * [D2XX_Programmers_Guide][1]
 * [AN_220 FTDI Drivers Installation Guide for Linux][2]
 * [Drivers for Linux](https://ftdichip.com/drivers/d2xx-drivers/)
 * [Readme](https://www.ftdichip.com/Drivers/D2XX/Linux/ReadMe.txt)
 
The readme shows the steps below.

```
sudo rmmod ftdi_sio
sudo rmmod usbserial
```

Show all FTDI devices:

```
# show USB devices
lsusb -d 0403:6001 -v | grep Serial

andreas@debian:~/Downloads/libftd2xx/release/examples/Events$ lsusb -d 0403:6001 -v | grep Serial
can't get device qualifier: Resource temporarily unavailable
can't get debug descriptor: Resource temporarily unavailable
Bus 005 Device 002: ID 0403:6001 Future Technology Devices International, Ltd FT232 Serial (UART) IC
  idProduct          0x6001 FT232 Serial (UART) IC
  iSerial                 3 A50285BI
Bus 005 Device 003: ID 0403:6001 Future Technology Devices International, Ltd FT232 Serial (UART) IC
  idProduct          0x6001 FT232 Serial (UART) IC
  iSerial                 3 A91BT6R0
can't get device qualifier: Resource temporarily unavailable
```

Change the access right to allow non-root access for the selected device by bus and device number:

```
# change the access rights of the device
sudo chmod a+rw /dev/bus/usb/005/003
```

### Use udev rule
Source: [Stackoverflow](https://stackoverflow.com/a/34021765)

Install udev rules to `/etc/udev/rules.d`, e.g. `99-ftd2xx.rules`

```
# FTDI Devices: FT232BM/L/Q, FT245BM/L/Q, FT232RL/Q, FT245RL/Q, VNC1L with VDPS Firmware
SUBSYSTEM=="usb", ENV{DEVTYPE}=="usb_device", ATTRS{idVendor}=="0403", ATTRS{idProduct}=="6001", MODE="0664", GROUP="plugdev", RUN+="/bin/sh -c 'rmmod ftdi_sio && rmmod usbserial'"

# FTDI Devices: FT2232C/D/L, FT2232HL/Q
SUBSYSTEM=="usb", ENV{DEVTYPE}=="usb_device", ATTRS{idVendor}=="0403", ATTRS{idProduct}=="6010", MODE="0664", GROUP="plugdev", RUN+="/bin/sh -c 'rmmod ftdi_sio && rmmod usbserial'"

# FTDI Devices: FT4232HL/Q
SUBSYSTEM=="usb", ENV{DEVTYPE}=="usb_device", ATTRS{idVendor}=="0403", ATTRS{idProduct}=="6011", MODE="0664", GROUP="plugdev", RUN+="/bin/sh -c 'rmmod ftdi_sio && rmmod usbserial'"
```

If you know the device number you can try this script instead instead of rmmod above:

```
RUN+="/bin/sh -c 'echo -n 1-1:1.0 > /sys/bus/usb/drivers/ftdi_sio/unbind'"
```



## Mac OS

### Mac OS 10.9 (Mavericks)

```
sudo kextunload -b com.apple.driver.AppleUSBFTDI
```



[1]: https://www.ftdichip.com/Support/Documents/ProgramGuides/D2XX_Programmer%27s_Guide(FT_000071).pdf
[2]: https://ftdichip.com/wp-content/uploads/2020/08/AN_220_FTDI_Drivers_Installation_Guide_for_Linux-1.pdf