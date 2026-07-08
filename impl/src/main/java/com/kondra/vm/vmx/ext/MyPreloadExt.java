package com.kondra.vm.vmx.ext;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.ext.PreloadExt;
import com.kondra.vm.common.vmx.ext.SymbolTableExt;
import com.kondra.vm.vmx.VmxWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyPreloadExt extends MyVmxExt implements PreloadExt {
    //from MyVmxExt
    private byte type;
    private byte flags;
    private short reserved;
    private int offset;
    private int size;
    private byte[] ext;
    private HashMap st;

    public MyPreloadExt(byte type, byte flags, short reserved, int offset, int size) {
        super(type, flags, reserved, offset, size);
        this.type = type;
        this.flags = flags;
        this.reserved = reserved;
        this.offset = offset;
        this.size = size;
    }
    @Override
    public void addSymbolOffset(int offset) {
        size = size + 4;
        byte[] newExt = new byte[size];
        for (int i = 0; i < (size-4); i++) {
            newExt[i] = ext[i];
        }
        byte[] bytes = VmxWriter.writeInt(offset);
        for(int i = 0; i<4; i++){
            newExt[size-4+i] = bytes[i];
        }
        ext = newExt;

    }


    @Override
    public List<Integer> getSymbolOffsets() {
        List<Integer> l = new ArrayList<>();
        for(int i = 0; i<(size); i=i+4){
            l.add(readInt(ext[i]));
        }
        return l;
    }


    private int readInt(int i){
        int temp = (ext[i])& 0xFF;
        temp |= ((ext[i+1])& 0xFF) <<8;
        temp |= ((ext[i+2])& 0xFF) <<16;
        temp |= ((ext[i+3])& 0xFF) <<24;
        return (int) temp;
    }

    public void printSymbols(){
        List<Integer> list = getSymbolOffsets();
        if(!list.isEmpty()) {
            for (int i = (int) ((ArrayList)list).get(0); i < size; i++) {
                if (list.contains(i)) {
                    System.out.println(ext[i]);
                } else {
                    System.out.print(ext[i]);
                }
            }
        }
    }


    //from MyVmxExt

    @Override
    public int getType() {
        return VmxExt.TYPE_PRELOAD;
    }

    @Override
    public void setExt(byte[] ext){
        this.ext = ext;

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
