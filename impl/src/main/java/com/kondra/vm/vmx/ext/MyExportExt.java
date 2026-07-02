package com.kondra.vm.vmx.ext;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.ext.Export;
import com.kondra.vm.common.vmx.ext.ExportExt;
import com.kondra.vm.common.vmx.ext.LabelExt;
import com.kondra.vm.vmx.VmxWriter;

import java.util.ArrayList;
import java.util.List;

public class MyExportExt extends MyVmxExt implements ExportExt {
    //from MyVmxExt
    private byte type;
    private byte flags;
    private short reserved;
    private int offset;
    private int size;
    private byte[] ext;

    public MyExportExt(byte type, byte flags, short reserved, int offset, int size) {
        super(type, flags, reserved, offset, size);
        this.type = type;
        this.flags = flags;
        this.reserved = reserved;
        this.offset = offset;
        this.size = size;
    }

    @Override
    public List<Export> getExports() {
        List<Export> exports = new ArrayList<>();
        for(int i = 0; i<size; i=i+8){
            int symbolOffset = readInt(i);
            int word2 = readInt(i+4);
            int addressOffset = word2 & 0x03ffffff;
            int section = word2 >> 26;
            exports.add(new MyExport(symbolOffset, section, addressOffset));
        }
        return exports;
    }

    //from MyVmxExt
    public int getType() {
        return VmxExt.TYPE_EXPORT;
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

}
