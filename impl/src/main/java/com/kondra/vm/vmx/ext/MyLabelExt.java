package com.kondra.vm.vmx.ext;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.ext.LabelExt;
import com.kondra.vm.common.vmx.ext.RelocationExt;
import com.kondra.vm.vmx.VmxWriter;

import java.util.Date;

public class MyLabelExt extends MyVmxExt implements LabelExt {
    //from MyVmxExt
    private byte type;
    private byte flags;
    private short reserved;
    private int offset;
    private int size;
    private byte[] ext;

    private Date timestamp;
    private String label;

    public MyLabelExt(byte type, byte flags, short reserved, int offset, int size) {
        super(type, flags, reserved, offset, size);
        this.type = type;
        this.flags = flags;
        this.reserved = reserved;
        this.offset = offset;
        this.size = size;
        this.timestamp = new Date((long)(readInt(0))*1000);
        label = "";
        for(int i = 0; i<32; i++){
            label=label + ext[i];
        }
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        byte[] bytes = label.getBytes();
        for(int i = 0; i<32; i++){
            ext[i] = bytes[i];
        }
        this.label = label;
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        byte[] time = writeInt((int)(((timestamp.getTime())/1000)));
        for(int i = 0; i<4; i++){
            ext[i] = time[i];
        }

    }


    //from MyVmxExt
    public int getType() {
        return VmxExt.TYPE_LABEL;
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
    static public byte[] writeInt(int i){
        byte[] bytes = new byte[4];
        bytes[0] = (byte) i;
        bytes[1] = (byte)(i>>>8);
        bytes[2] = (byte)(i>>>16);
        bytes[3] = (byte)(i>>>24);
        return bytes;
    }


}
