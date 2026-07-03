## What this is
JavaFTD2XX is a Java library that provides access to FTDI's D2XX native driver using JNA, exposing FTDI device operations (device open/close, read/write, EEPROM access, modes) to Java applications and test code. It's intended for developers who need programmatic control of FTDI USB-to-serial/MPSSE devices from Java.

### Stack
- **Language(s):** Java (primary)
- **Framework / runtime:** Java + Maven (pom.xml present)
- **Notable libraries / native deps:** JNA (com.sun.jna package present), FTDI D2XX native driver (native code / platform driver under src/main/native), Maven for build lifecycle

## How it's organized
Top-level repository layout:
```
.gitignore                (git ignore rules)
LICENSE                   (project license)
pom.xml                   (Maven build & dependencies)
readme.md                 (project README / usage)
renovate.json             (dependency/renovation config)
.github/                  (repo metadata / CI configs - present)
src/
  main/
    java/
      com/
        ftdi/
          FTD2XX.java                     (large wrapper for native FTDI calls)
          FTDevice.java                   (higher-level device abstraction)
          FTDeviceInputStream.java
          FTDeviceOutputStream.java
          FTD2XXException.java
          EEPROMData.java
          EepromX.java
          EepromHeaderControler.java
          DeviceStatus.java
          DeviceType.java
          BitModes.java
          FlowControl.java
          Parity.java
          Purge.java
          StopBits.java
          WordLength.java
          AbstractDataHandler.java
          AbstractEventNotificationHandler.java
          handler/
            BlockingReaderHandler.java
            EventNotificationHandlerLinux.java
            EventNotificationHandlerWindows.java
          utils/
            ByteUtils.java
    native/                              (native bits / platform helpers)
  test/                                  (tests)
```

How it fits together:
- FTD2XX.java is the low-level Java wrapper that mirrors the FTDI D2XX API (native function bindings via JNA). FTDevice.java provides a higher-level object model around a connected device and uses the wrapper methods. Stream classes (FTDeviceInputStream/OutputStream) provide Java IO semantics for reads/writes. EEPROM* classes model device EEPROM layout and helpers to read/modify it. Handler classes implement event/notification and platform-specific behaviors. The native/ folder contains platform-specific native helpers or build artifacts needed by JNA/native calls. The pom.xml controls compilation, tests, and dependencies.

## How to run it
This is a Java library built with Maven. Typical steps from a fresh clone:
```bash
git clone https://github.com/akuhtz/JavaFTD2XX.git
cd JavaFTD2XX
# build jar (skip tests if you need to)
mvn package
# or to run tests
mvn test
```

Notes and prerequisites:
- The library depends on JNA and the FTDI D2XX native driver. Ensure the appropriate FTDI D2XX drivers are installed on your platform and reachable by the JVM (native library path or system install).
- If native helper libraries are required from src/main/native, build/install them or put prebuilt platform libraries on java.library.path or the JNA search path.
- Running integration tests or sample code that talks to devices requires access to attached FTDI hardware and appropriate permissions (USB device access on Linux).

## Try asking
- How does the README demonstrate opening and using an FTDevice — is there a simple example that calls FTD2XX.open and uses FTDeviceInputStream/OutputStream?
- Are the files in src/main/native intended to be built here (tooling/Makefile) or are prebuilt platform libraries expected to be provided by the user?
- Do you want a CI workflow added under .github to build and run the unit tests across multiple JDK versions and OSes (Linux/Windows/macOS) so native bindings are validated automatically?
