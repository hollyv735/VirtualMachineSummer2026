package com.kondra.vm.vmx.writers;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.VmxFile;
import com.kondra.vm.vmx.Header;
import com.kondra.vm.vmx.MyVmxFile;
import com.kondra.vm.vmx.Section;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class VmxWriter {
    private MyVmxFile vmxFile;
    private Header header;
    private Section[] sections;
    private List<VmxExt> extensions;
    private FileOutputStream stream;
    private File file;
    List<MyVmxExtWriter> extWriters;
    List<SectionWriter> secWriters;
    HeaderWriter headerWriter;


    public VmxWriter(MyVmxFile vmxFile) {
        this.vmxFile = vmxFile;
        this.sections = vmxFile.getSections();
        this.extensions = vmxFile.getExtensions();
        this.header = vmxFile.getHeader();
        this.file = file;
        int numExt = extensions.size();
        secWriters = new ArrayList<>();
        extWriters = new ArrayList<>();
        int fileSize;
        int programSize = 0;
        int entryOffset = header.getEntryOffset();
        for (int i = 0; i < 3; i++) {
            SectionWriter secWriter = new SectionWriter(sections[i]);
            secWriter.setOffset(programSize + entryOffset);
            programSize = programSize + secWriter.getSize();
            secWriters.add(secWriter);
        }
        //bss
        SectionWriter secWriter = new SectionWriter(sections[3]);
        secWriter.setOffset(programSize + entryOffset);
        secWriters.add(secWriter);

        int extOffset = programSize;
        for (int i = 0; i < numExt; i++) {
            MyVmxExtWriter extWriter = new MyVmxExtWriter(extensions.get(i));
            extWriter.setOffset(extOffset);
            extOffset = extOffset + extWriter.getSize();
            extWriters.add(extWriter);
        }
        fileSize = extOffset + 56 + (12*numExt);
        headerWriter = new HeaderWriter(header, fileSize, programSize);
        //all of offsets and sizes are now saved
    }



    public void write(File file){
        try{
            this.stream = new FileOutputStream(file);
            stream.write(headerWriter.write());
            for(int i = 0; i<4; i++){
                stream.write(secWriters.get(i).writeHeader());
            }
            for(int i = 0; i<header.getExtCount(); i++){
                stream.write((extWriters.get(i)).writeHeader());
            }
            for(int i = 0; i<3; i++){
                stream.write(secWriters.get(i).writeContent());
            }
            for(int i = 0; i<header.getExtCount(); i++){
                stream.write((extWriters.get(i)).writeContent());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





    static public byte[] writeShort(short s){
        byte[] bytes = new byte[2];
        bytes[0] = (byte) s;
        bytes[1] = (byte)(s>>>8);
        return bytes;
    }

    static public byte[] writeInt(int i){
        byte[] bytes = new byte[4];
        bytes[0] = (byte) i;
        bytes[1] = (byte)(i>>>8);
        bytes[2] = (byte)(i>>>16);
        bytes[3] = (byte)(i>>>24);
        return bytes;
    }

    static public void writeInt(byte[] bytes, int offset, int i){
        bytes[offset] = (byte) i;
        bytes[offset+1] = (byte)(i>>>8);
        bytes[offset+2] = (byte)(i>>>16);
        bytes[offset+3] = (byte)(i>>>24);
    }

    static public void writeShort(byte[] bytes, int offset, int i){
        bytes[offset] = (byte) i;
        bytes[offset+1] = (byte)(i>>>8);
    }






}
