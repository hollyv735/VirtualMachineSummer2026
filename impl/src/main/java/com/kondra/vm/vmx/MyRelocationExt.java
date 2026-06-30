package com.kondra.vm.vmx;

import com.kondra.vm.common.vmx.ext.Relocation;
import com.kondra.vm.common.vmx.ext.RelocationExt;

import java.util.ArrayList;
import java.util.List;

public class MyRelocationExt extends MyVmxExt implements RelocationExt{
    //from MyVmxExt
    private byte type;
    private byte flags;
    private short reserved;
    private int offset;
    private int size;
    private byte[] ext;

    public MyRelocationExt(byte type, byte flags, short reserved, int offset, int size) {
        super(type, flags, reserved, offset, size);
    }


    @Override
    public List<Relocation> getRelocations(int i) {
        int offset = readInt(8*i);
        int size = readInt((8*i)+4);
        List<Relocation> relocations = new ArrayList<>();

        for(int j = offset; j< (size+offset); j = j+8){
            relocations.add(getReloc(j));
        }

        return relocations;
    }

    @Override
    public int getType() {
        return MyVmxExt.TYPE_RELOC;
    }


    private int readInt(int i){
        int temp = (ext[i])& 0xFF;
        temp |= ((ext[i+1])& 0xFF) <<8;
        temp |= ((ext[i+2])& 0xFF) <<16;
        temp |= ((ext[i+3])& 0xFF) <<24;
        return (int) temp;
    }
    private short readShort(int i){
        int temp = (ext[i])& 0xFF;
        temp |= ((ext[i+1])& 0xFF) <<8;
        return (short) temp;
    }

    private MyRelocation getReloc(int i){
        byte[] bytes = new byte[8];
        for (int j = 0; j<8; j++){
            bytes[j] = ext[i+j];
        }
        return new MyRelocation(bytes);
    }


}
