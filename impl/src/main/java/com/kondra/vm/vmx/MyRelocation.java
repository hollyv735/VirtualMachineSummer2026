package com.kondra.vm.vmx;

import com.kondra.vm.common.vmx.ext.Relocation;

public class MyRelocation implements Relocation {
    byte[] bytes;
    int type;
    int fixupOffset;
    int section;
    int sectionOffset;
    boolean dynamic;
    int dynamicSymbolOffset;
    int affinityTableIndex;



    public MyRelocation(byte[] bytes){
        this.bytes = bytes;
        int word1 = (bytes[0])& 0xFF;
        word1 |= ((bytes[1])& 0xFF) <<8;
        word1 |= ((bytes[2])& 0xFF) <<16;
        word1 |= ((bytes[3])& 0xFF) <<24;
        int word2 = (bytes[4])& 0xFF;
        word2 |= ((bytes[5])& 0xFF) <<8;
        word2 |= ((bytes[6])& 0xFF) <<16;
        word2 |= ((bytes[7])& 0xFF) <<24;
        type = word1 & 0x3;
        fixupOffset = word1 & 0xfffffffc;
        section = (word2 >> 26) & 0xf;
        sectionOffset = word2 & 0x03ffffff;
        dynamicSymbolOffset = word2 & 0xffff;
        affinityTableIndex = (word2 >> 16) & 0xff;
        dynamic = ((word2 & 0x40000000)==0)?false:true;

    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int i) {
        this.type = i;
    }

    @Override
    public int getFixupOffset() {
        return type;
    }

    @Override
    public void setFixupOffset(int i) {
        this.fixupOffset = i;
    }

    @Override
    public int getSection() {
        return section;
    }

    @Override
    public void setSection(int i) {
        this.section = i;
    }

    @Override
    public int getSectionOffset() {
        return sectionOffset;
    }

    @Override
    public void setSectionOffset(int i) {
        sectionOffset = i;
    }

    @Override
    public boolean isDynamic() {
        return dynamic;
    }

    @Override
    public void setDynamic(boolean b) {
        dynamic = b;
    }

    @Override
    public int getDynamicSymbolOffset() {
        return dynamicSymbolOffset;
    }

    @Override
    public void setDynamicSymbolOffset(int i) {
        dynamicSymbolOffset = i;
    }

    @Override
    public int getAffinityTableIndex() {
        return affinityTableIndex;
    }

    @Override
    public void setAffinityTableIndex(int i) {
        affinityTableIndex = i;
    }
}
