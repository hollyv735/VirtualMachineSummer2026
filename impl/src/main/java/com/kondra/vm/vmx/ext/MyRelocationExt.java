package com.kondra.vm.vmx.ext;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.ext.Relocation;
import com.kondra.vm.common.vmx.ext.RelocationExt;
import com.kondra.vm.vmx.writers.VmxWriter;

import java.util.ArrayList;
import java.util.List;

public class MyRelocationExt implements RelocationExt {
    private byte flags;
    List<Relocation> textRelocations;
    List<Relocation> rodataRelocations;
    List<Relocation> dataRelocations;
    List<Relocation> bssRelocations;


    public MyRelocationExt(byte flags) {
        this.flags = flags;
    }

    @Override
    public List<Relocation> getRelocations(int section) {
        switch(section){
            case(0):
                return textRelocations;
            case(1):
                return rodataRelocations;
            case(2):
                return dataRelocations;
            case(3):
                return bssRelocations;
        }
        return null;
    }

    @Override
    public int getType() {
        return VmxExt.TYPE_RELOC;
    }

    public void setRelocations(List<Relocation> relocations, int section){
        switch(section){
            case(0):
                textRelocations = relocations;
            case(1):
                rodataRelocations = relocations;
            case(2):
                dataRelocations = relocations;
            case(3):
                bssRelocations = relocations;
        }
    }

    public byte getFlags(){
        return flags;
    }
}

/*
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

    @Override
    public void setExt(byte[] ext){
        this.ext = ext;
        size = ext.length;
    }
    @Override
    public int getSize() {
        return size;
    }
    @Override
    public byte[] writeExtHeader(){
        byte[] ext = new byte[12];
        ext[0] = type;
        ext[1] = flags;
        byte[] temp = VmxWriter.writeShort(reserved);
        for(int i = 0; i<2; i++){
            ext[2+i] = temp[i];
        }
        temp = VmxWriter.writeInt(offset);
        for(int i = 0; i<4; i++){
            ext[4+i] = temp[i];
        }
        temp = VmxWriter.writeInt(size);
        for(int i = 0; i<4; i++){
            ext[8+i] = temp[i];
        }
        return ext;
    }

    @Override
    public byte[] writeExtContent(){
        return ext;
    }


}

 */
