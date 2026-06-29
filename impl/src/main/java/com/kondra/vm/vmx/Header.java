package com.kondra.vm.vmx;

public class Header{
    private int magic;
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

    public Header(int magic, byte extCount, byte vmxVersion, byte flags, byte reserved, byte major, byte minor, short buildNum,
                  int fileSize, int programSize, int entryOffset){
        this.magic = magic;
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

    public byte getMajor() {
        return major;
    }

    public byte getMinor() {
        return minor;
    }

    public short getBuildNum() {
        return buildNum;
    }

    public void setBuildNum(short buildNum) {
        this.buildNum = buildNum;
    }

    public void setMajor(byte major) {
        this.major = major;
    }

    public void setMinor(byte minor) {
        this.minor = minor;
    }

    public byte[] writeHeader(){
        byte[] header = new byte[24];
        //magic header[0-3]
        byte[] temp = VmxWriter.writeInt(magic);
        for(int i = 0; i<4; i++){
            header[i] = temp[i];
        }
        header[4] = extCount;
        header[5] = vmxVersion;
        header[6] = flags;
        header[7] = reserved;
        header[8] = major;
        header[9] = minor;
        temp = VmxWriter.writeShort(buildNum);
        for(int i = 0; i<2; i++){
            header[10+i] = temp[i];
        }
        temp = VmxWriter.writeInt(fileSize);
        for(int i = 0; i<4; i++){
            header[12+i] = temp[i];
        }
        temp = VmxWriter.writeInt(programSize);
        for(int i = 0; i<4; i++){
            header[16+i] = temp[i];
        }
        temp = VmxWriter.writeInt(entryOffset);
        for(int i = 0; i<4; i++){
            header[20+i] = temp[i];
        }
        return header;
    }
}
