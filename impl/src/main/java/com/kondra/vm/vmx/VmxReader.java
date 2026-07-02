package com.kondra.vm.vmx;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.vmx.ext.MyRelocationExt;
import com.kondra.vm.vmx.ext.MyVmxExt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VmxReader {
    private FileInputStream stream;
    private Header header;
    private Section[] sections;
    private List<VmxExt> extensions;





    public VmxReader(File file){
        try {
            this.sections = new Section[4];
            this.extensions = new ArrayList<VmxExt>();
            this.stream = new FileInputStream(file);
            this.header = readHeader();
            for(int i = 0; i<4; i++){
                this.sections[i]=readSectionHeader();
            }
            for(int i = 0; i<header.getExtCount(); i++){
                extensions.add(readExtension());
            }
            for(int i = 0; i<3; i++){
                readSectionContent(this.sections[i]);
            }
            this.sections[3].setSection(new byte[sections[3].getSize()]);
            for(int i = 0; i<header.getExtCount(); i++){
                readExtensionContent(extensions.get(i));
            }

        }catch(FileNotFoundException ex){
        }
    }

    private void readExtensionContent(VmxExt vmxExt) {
        byte[] ext = new byte[((MyVmxExt)vmxExt).getSize()];
        for(int i = 0; i < ((MyVmxExt)vmxExt).getSize(); i++){
            ext[i] = readByte();
        }
        ((MyVmxExt) vmxExt).setExt(ext);
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

    public List<VmxExt> getExtensions() {
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
        return new Header(readInt(), readByte(),//extCount
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
        byte type = readByte();
        switch(type){
            case(VmxExt.TYPE_RELOC):
                MyVmxExt ex =  new MyRelocationExt(type, //type
                        readByte(), //flags
                        readShort(), //reserved
                        readInt(), //offset
                        readInt()); //size
                return ex;

        }
        return null;

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

    private Section readSectionHeader(){
        int sOffset = readInt();
        int sSize = readInt();
        return new Section(sOffset, sSize);
    }

    private void readSectionContent(Section s){
        int sSize = s.getSize();
        byte[] content = new byte[sSize];
        for(int i = 0; i<sSize; i++){
            content[i] = readByte();
        }
        s.setSection(content);
    }





}
