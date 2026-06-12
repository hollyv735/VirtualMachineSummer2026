package com.kondra.vm.vmx;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VmxReader {
    private FileInputStream stream;
    private Header header;
    private Section[] sections;
    private List<MyVmxExt> extensions;





    public VmxReader(File file){
        try {
            this.sections = new Section[4];
            this.stream = new FileInputStream(file);
            readInt(); //magic
            this.header = readHeader();
            for(int i = 0; i<4; i++){
                this.sections[i]=readSection();
            }
            for(int i = 0; i<header.getExtCount(); i++){
                extensions.add(readExtension());
            }

        }catch(FileNotFoundException ex){
        }
    }

    public MyVmxFile getVmxFile(){
        return new MyVmxFile(header, sections, extensions);
    }

    public Header getHeader(){
        return header;
    }
    public Section[] getSections(){
        return sections;
    }

    public List<MyVmxExt> getExtensions() {
        return extensions;
    }



    private byte readByte(){
        try {
            return (byte)stream.read();
        }
        catch(IOException ex){
            return -1;
        }
    }
    private short readShort(){
        try {
            int temp = (stream.read())& 0xFF;
            temp |= ((stream.read())& 0xFF) <<8;
            return (short) temp;
        }
        catch(IOException ex){
            return -1;
        }
    }
    private int readInt(){
        try {
            int temp = (stream.read())& 0xFF;
            temp |= ((stream.read())& 0xFF) <<8;
            temp |= ((stream.read())& 0xFF) <<16;
            temp |= ((stream.read())& 0xFF) <<24;
            return (int) temp;
        }
        catch(IOException ex){
            return -1;
        }
    }

    private Header readHeader(){
        return new Header(readByte(),//extCount
                readByte(), //vmx variation
                readByte(), //flags
                readByte(), //reserved
                readByte(), //major
                readByte(), //minor
                readShort(), //build num
                readInt(), //file size
                readInt(), //program size
                readInt()); //entry offset

    }

    private MyVmxExt readExtension(){
        MyVmxExt ex =  new MyVmxExt(readByte(), //type
                readByte(), //flags
                readShort(), //reserved
                readInt(), //offset
                readInt()); //size
        return ex;
    }


    private Section readSection(){
        int sOffset = readInt();
        int sSize = readInt();
        Section s = new Section(sOffset, sSize);
        byte[] content = new byte[sSize];
        for(int i = 0; i<sSize; i++){
            content[i] = readByte();
        }
        s.setSection(content);
        return s;
    }





}
