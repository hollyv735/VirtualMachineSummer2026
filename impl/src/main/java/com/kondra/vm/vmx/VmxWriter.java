package com.kondra.vm.vmx;

import com.kondra.vm.common.vmx.VmxExt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class VmxWriter {
    private MyVmxFile vmxFile;
    private Header header;
    private Section[] sections;
    private List<VmxExt> extensions;
    private FileOutputStream stream;
    private File file;


    public VmxWriter(MyVmxFile vmxFile, File file){
        this.vmxFile = vmxFile;
        this.sections = vmxFile.getSections();
        this.extensions = vmxFile.getExtensions();
        this.header = vmxFile.getHeader();
        this.file = file;
        try{
            this.stream = new FileOutputStream(file);
            stream.write(header.writeHeader());
            for(int i = 0; i<4; i++){
                stream.write(sections[i].writeSectionHeader());
            }
            for(int i = 0; i<header.getExtCount(); i++){
                stream.write(((MyVmxExt)extensions.get(i)).writeExtHeader());
            }
            for(int i = 0; i<3; i++){
                stream.write(sections[i].writeSection());
            }
            for(int i = 0; i<header.getExtCount(); i++){
                stream.write(((MyVmxExt)extensions.get(i)).writeExtContent());
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






}
