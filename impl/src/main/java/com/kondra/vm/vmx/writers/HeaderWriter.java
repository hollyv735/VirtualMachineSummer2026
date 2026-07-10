package com.kondra.vm.vmx.writers;

import com.kondra.vm.vmx.Header;

public class HeaderWriter {
    private Header header;
    int fileSize;
    int programSize;

    public HeaderWriter(Header header, int fileSize, int programSize){
        this.header = header;
        this.fileSize = fileSize;
        this.programSize = programSize;
    }

    public byte[] write(){
        byte[] bytes = new byte[24];
        VmxWriter.writeInt(bytes, 0, header.getMagic());
        bytes[4] = header.getExtCount();
        bytes[5] = header.getVmxVersion();
        bytes[6] = header.getFlags();
        bytes[8] = header.getMajor();
        bytes[9] = header.getMinor();
        VmxWriter.writeShort(bytes, 10, header.getBuildNum());
        VmxWriter.writeInt(bytes, 12, fileSize);
        VmxWriter.writeInt(bytes, 16, programSize);
        VmxWriter.writeInt(bytes, 20, header.getEntryOffset());
        return bytes;
    }
}
