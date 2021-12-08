package com.sun.jna.platform.unix;

import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

public interface LibPThread extends Library {

    String NAME = "pthread";

    LibPThread INSTANCE = Native.load(NAME, LibPThread.class);

    @FieldOrder({ "eCondVar", "eMutex", "iVar" })
    public static class EVENT_HANDLE extends Structure {

        public pthread_cond_t.ByReference eCondVar;

        public pthread_mutex_t.ByReference eMutex;

        public int iVar;

        public EVENT_HANDLE() {

            setAlignType(ALIGN_NONE);

            Memory mem = new Memory(pthread_cond_t.condSize() + pthread_mutex_t.mutexSize() + Integer.BYTES);

            useMemory(mem);

            eCondVar = new pthread_cond_t.ByReference(mem, 0);
            eMutex = new pthread_mutex_t.ByReference(mem, pthread_cond_t.condSize());
        }
    }

    @FieldOrder({ "mutex" })
    public static class pthread_mutex_t extends Structure {

        public static class ByReference extends pthread_mutex_t implements Structure.ByReference {

            public ByReference(Memory mem, long offset) {
                super(mem, offset);
            }
        }

        public static int mutexSize() {
            return 5 * Native.LONG_SIZE;
        }

        public byte[] mutex;

        public pthread_mutex_t(Memory mem, long offset) {
            mutex = mem.getByteArray(offset, mutexSize());
            useMemory(mem, (int) offset);
        }
    }

    @FieldOrder({ "cond" })
    public static class pthread_cond_t extends Structure {

        public static class ByReference extends pthread_cond_t implements Structure.ByReference {

            public ByReference(Memory mem, long offset) {
                super(mem, offset);
            }
        }

        public static int condSize() {
            return 6 * Native.LONG_SIZE;
        }

        public byte[] cond;

        public pthread_cond_t(Memory mem, long offset) {
            cond = mem.getByteArray(offset, condSize());
            useMemory(mem, (int) offset);
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
