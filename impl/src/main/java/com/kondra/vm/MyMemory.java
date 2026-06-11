package com.kondra.vm;

import com.kondra.vm.common.CPU;
import com.kondra.vm.common.VirtualMachine;
import com.kondra.vm.common.concurrent.ProcessCreationException;
import com.kondra.vm.common.concurrent.VmProcess;
import com.kondra.vm.common.concurrent.VmThread;
import com.kondra.vm.common.loader.Loader;
import com.kondra.vm.common.memory.Memory;
import com.kondra.vm.common.memory.MemoryMgr;
import com.kondra.vm.common.vmx.VmxException;
import com.kondra.vm.common.vmx.VmxFile;

import java.io.File;

public class MyMemory implements Memory {
    private byte[] storage;

    public MyMemory(int size){
        this.storage = new byte[size];

    }
    @Override
    public byte getByte(int offset) {
        return storage[offset];
    }

    @Override
    public short getShort(int offset) {
        int temp = ((int)storage[offset])& 0xFF;
        temp |= (((int)storage[offset+1])& 0xFF) <<8;
        return (short) temp;
    }

    @Override
    public int getInt(int offset) {
        int temp = ((int)storage[offset])& 0xFF;
        temp |= (((int)storage[offset+1])& 0xFF) <<8;
        temp |= (((int)storage[offset+2])& 0xFF) <<16;
        temp |= (((int)storage[offset+3])& 0xFF) <<24;
        return (int) temp;
    }

    @Override
    public int getAlignedInt(int offset) {
        int offset2 = offset &~3;
        return getInt(offset2);
    }

    @Override
    public void setByte(int offset, byte val) {
        storage[offset] = val;

    }

    @Override
    public void setShort(int offset, short val) {
        storage[offset] = (byte) val;
        int temp = val >>8;
        storage[offset+1] = (byte) temp;

    }

    @Override
    public void setInt(int offset, int val) {
        storage[offset] = (byte) val;
        int temp = val >>8;
        storage[offset+1] = (byte) temp;
        temp = val >>16;
        storage[offset+2] = (byte) temp;
        temp = val >>24;
        storage[offset+3] = (byte) temp;

    }

    @Override
    public void setAlignedInt(int offset, int val) {
        int offset2 = offset &~3;
        setInt(offset2, val);
    }

    @Override
    public byte[] getBytes() {
        return storage;
    }

    @Override
    public int size() {
        return storage.length;
    }
}
