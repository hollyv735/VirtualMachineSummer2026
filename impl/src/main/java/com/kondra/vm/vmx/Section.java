package com.kondra.vm.vmx;

public class Section{
    private int offset;
    private int size;
    private byte[] section;

    public Section(int offset, int size){
        this.size = size;
        this.offset = offset;
    }
    public Section(int offset, byte[] bytes){
        this.offset = offset;
        this.section = bytes;
        this.size = bytes.length;
    }

    public void setSection(byte[] section){
        this.section = section;
    }

    public byte[] getSection(){
        return section;
    }

    public int getOffset() {
        return offset;
    }

    public int getSize() {
        return size;
    }

    public byte[] writeSectionHeader() {
        byte[] bytes = new byte[8];
        byte[] temp = VmxWriter.writeInt(offset);
        for (int i = 0; i < 4; i++) {
            bytes[i] = temp[i];
        }
        temp = VmxWriter.writeInt(size);
        for (int i = 0; i < 4; i++) {
            bytes[4 + i] = temp[i];

        }
        return bytes;
    }

    public byte[] writeSection(){
        return section;
    }

}
