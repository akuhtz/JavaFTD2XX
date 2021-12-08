package com.sun.jna.platform.unix;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;
import com.sun.jna.platform.win32.WinDef.DWORD;

public interface LibPThread extends Library {

    String NAME = "pthread";

    LibPThread INSTANCE = Native.load(NAME, LibPThread.class);

    @FieldOrder({ "eCondVar", "eMutex", "iVar" })
    public class EVENT_HANDLE extends Structure {
        public pthread_cond_t eCondVar;

        public pthread_mutex_t eMutex;

        public int iVar;
    }

    @FieldOrder({ "dummy0", "dummy1", "dummy2", "dummy3", "dummy4", "dummy5", "dummy6", "dummy7", "dummy8", "dummy9" })
    public class pthread_mutex_t extends Structure {
        public DWORD dummy0;

        public DWORD dummy1;

        public DWORD dummy2;

        public DWORD dummy3;

        public DWORD dummy4;

        public DWORD dummy5;

        public DWORD dummy6;

        public DWORD dummy7;

        public DWORD dummy8;

        public DWORD dummy9;
    }

    @FieldOrder({ "dummy0", "dummy1", "dummy2", "dummy3", "dummy4", "dummy5", "dummy6", "dummy7", "dummy8", "dummy9",
        "dummy10", "dummy11" })
    public class pthread_cond_t extends Structure {
        public DWORD dummy0;

        public DWORD dummy1;

        public DWORD dummy2;

        public DWORD dummy3;

        public DWORD dummy4;

        public DWORD dummy5;

        public DWORD dummy6;

        public DWORD dummy7;

        public DWORD dummy8;

        public DWORD dummy9;

        public DWORD dummy10;

        public DWORD dummy11;
    }

    int pthread_mutex_init(Pointer mutex, Pointer attr);

    int pthread_mutex_lock(Pointer mutex);

    int pthread_mutex_unlock(Pointer mutex);

    int pthread_mutex_destroy(Pointer mutex);

    int pthread_cond_init(Pointer cond, Pointer attr);

    int pthread_cond_wait(Pointer cond, Pointer mutex);

    int pthread_cond_signal(Pointer cond);

    int pthread_cond_destroy(Pointer cond);

}
