package com.kondra.vm.vmx.writers;

import com.kondra.vm.vmx.Section;

public class SectionWriter {
    Section section;
    private int offset;


    public SectionWriter(Section section){
        this.section = section;
    }

    public void setOffset(int o){
        offset = o;
    }

    public byte[] writeHeader(){
        byte[] header = new byte[8];
        VmxWriter.writeInt(header, 0, offset);
        VmxWriter.writeInt(header, 4, getSize());
        return header;
    }
    public byte[] writeContent(){
        return section.getSection();
    }

    public int getSize(){
        byte[] content = writeContent();
        int size = content.length;
        return size;
    }
}
