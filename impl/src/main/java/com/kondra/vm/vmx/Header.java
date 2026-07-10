package com.kondra.vm.vmx;

import com.kondra.vm.vmx.writers.VmxWriter;

public class Header{
    private int magic;
    private byte extCount;
    private byte vmxVersion;
    private byte flags;
    private byte reserved;
    private byte major;
    private byte minor;
    private short buildNum;
    private int entryOffset;

    public Header(int magic, byte extCount, byte vmxVersion, byte flags, byte reserved, byte major, byte minor, short buildNum, int entryOffset){
        this.magic = magic;
        this.extCount = extCount;
        this.vmxVersion = vmxVersion;
        this.flags = flags;
        this.reserved = reserved;
        this.major = major;
        this.minor = minor;
        this.buildNum = buildNum;
        this.entryOffset = entryOffset;

    }
    public int getMagic(){return magic;}
    public byte getExtCount() {return extCount;}
    public byte getVmxVersion(){return vmxVersion;}
    public byte getFlags(){
        return flags;
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
    public int getEntryOffset() {return entryOffset;}

    public void setBuildNum(short buildNum) {
        this.buildNum = buildNum;
    }
    public void setMajor(byte major) {
        this.major = major;
    }
    public void setMinor(byte minor) {
        this.minor = minor;
    }
    public void setFlags(byte flags){
        this.flags = flags;
    }
    public void setEntryOffset(int i) {this.entryOffset = i;}


}
