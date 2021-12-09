package com.sun.jna.platform.unix;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

public interface LibPThread extends Library {

    String NAME = "pthread";

    LibPThread INSTANCE = Native.load(NAME, LibPThread.class);

    @FieldOrder({ "eCondVar", "eMutex", "iVar" })
    public static class EVENT_HANDLE extends Structure {
        public pthread_cond_t eCondVar;

        public pthread_mutex_t eMutex;

        public int iVar;

        public EVENT_HANDLE() {
            eCondVar = new pthread_cond_t();
            eMutex = new pthread_mutex_t();
            allocateMemory();
        }

        // // You can either override or create a separate helper method
        // @Override
        // public void useMemory(Pointer m) {
        // super.useMemory(m);
        // }
    }

    @FieldOrder({ "mutex" })
    public static class pthread_mutex_t extends Structure {
        public byte[] mutex;

        public pthread_mutex_t() {
            mutex = new byte[5 * Native.LONG_SIZE];
            allocateMemory();
        }
    }

    // @FieldOrder({ "dummy0", "dummy1", "dummy2", "dummy3", "dummy4", "dummy5", "dummy6", "dummy7", "dummy8", "dummy9"
    // })
    // public static class pthread_mutex_t extends Structure {
    // public NativeLong dummy0;
    //
    // public NativeLong dummy1;
    //
    // public NativeLong dummy2;
    //
    // public NativeLong dummy3;
    //
    // public NativeLong dummy4;
    //
    // public NativeLong dummy5;
    //
    // public NativeLong dummy6;
    //
    // public NativeLong dummy7;
    //
    // public NativeLong dummy8;
    //
    // public NativeLong dummy9;
    //
    // // You can either override or create a separate helper method
    // @Override
    // public void useMemory(Pointer m) {
    // super.useMemory(m);
    // }
    // }

    // @FieldOrder({ "dummy0", "dummy1", "dummy2", "dummy3", "dummy4", "dummy5", "dummy6", "dummy7", "dummy8", "dummy9",
    // "dummy10", "dummy11" })
    // public static class pthread_cond_t extends Structure {
    // public NativeLong dummy0;
    //
    // public NativeLong dummy1;
    //
    // public NativeLong dummy2;
    //
    // public NativeLong dummy3;
    //
    // public NativeLong dummy4;
    //
    // public NativeLong dummy5;
    //
    // public NativeLong dummy6;
    //
    // public NativeLong dummy7;
    //
    // public NativeLong dummy8;
    //
    // public NativeLong dummy9;
    //
    // public NativeLong dummy10;
    //
    // public NativeLong dummy11;
    //
    // // You can either override or create a separate helper method
    // @Override
    // public void useMemory(Pointer m) {
    // super.useMemory(m);
    // }
    // }

    @FieldOrder({ "cond" })
    public static class pthread_cond_t extends Structure {
        public byte[] cond;

        public pthread_cond_t() {
            cond = new byte[6 * Native.LONG_SIZE];
            allocateMemory();
        }
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
