package com.kondra.vm.vmx;

public class Header{
    private byte extCount;
    private byte vmxVersion;
    private byte flags;
    private byte reserved;
    private byte major;
    private byte minor;
    private short buildNum;
    private int fileSize;
    private int programSize;
    private int entryOffset;

    public Header(byte extCount, byte vmxVersion, byte flags, byte reserved, byte major, byte minor, short buildNum,
                  int fileSize, int programSize, int entryOffset){
        this.extCount = extCount;
        this.vmxVersion = vmxVersion;
        this.flags = flags;
        this.reserved = reserved;
        this.major = major;
        this.minor = minor;
        this.buildNum = buildNum;
        this.fileSize = fileSize;
        this.programSize = programSize;
        this.entryOffset = entryOffset;
    }

    public byte getExtCount() {
        return extCount;
    }

    public byte getFlags(){
        return flags;
    }
    public void setFlags(byte flags){
        this.flags = flags;
    }
    public int getEntryOffset(){
        return entryOffset;
    }
    public void setEntryOffset(int entryOffset){
        this.entryOffset = entryOffset;
    }
}
