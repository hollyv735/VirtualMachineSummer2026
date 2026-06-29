package com.kondra.vm.vmx;

import com.kondra.vm.common.vmx.ext.Relocation;
import com.kondra.vm.common.vmx.ext.RelocationExt;

import java.util.List;

public class MyRelocationExt implements RelocationExt{
    //from MyVmxExt
    private byte type;
    private byte flags;
    private short reserved;
    private int offset;
    private int size;
    private byte[] ext;


    //new fields
    /*
    int textOffset;
    int textSize;
    int rodataOffset;
    int rodataSize;
    int dataOffset;
    int dataSize;
    int bssOffset;
    int bssSize;
     */

    @Override
    public List<Relocation> getRelocations(int i) {
        int size = ext[2*i]; //read this as an int not byte
        int offset = ext[(2*i-1)]; //read this as an int not byte


        return List.of();
    }

    @Override
    public int getType() {
        return MyVmxExt.TYPE_RELOC;
    }
}
