package com.kondra.vm.vmx.ext;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.ext.Affinity;
import com.kondra.vm.common.vmx.ext.AffinityExt;
import com.kondra.vm.common.vmx.ext.LabelExt;
import com.kondra.vm.vmx.VmxWriter;

import java.util.ArrayList;
import java.util.List;

public class MyAffinityExt extends MyVmxExt implements AffinityExt {
    //from MyVmxExt
    private byte type;
    private byte flags;
    private short reserved;
    private int offset;
    private int size;
    private byte[] ext;

    public MyAffinityExt(byte type, byte flags, short reserved, int offset, int size) {
        super(type, flags, reserved, offset, size);
        this.type = type;
        this.flags = flags;
        this.reserved = reserved;
        this.offset = offset;
        this.size = size;
    }

    @Override
    public List<Affinity> getAffinityList() {
        List<Affinity> affinities = new ArrayList<>();
        for(int i = 0; i<size; i=i+12){
            int major = readShort(i);
            int minor = readShort(i+2);
            int symbolOffset = readInt(i+4);
            affinities.add(new MyAffinity(major, minor, symbolOffset));
        }
        return affinities;

    }

    //from MyVmxExt
    public int getType() {
        return VmxExt.TYPE_AFFINITY;
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

}
