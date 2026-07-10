package com.kondra.vm.vmx.readers;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.vmx.Header;
import com.kondra.vm.vmx.MyVmxFile;
import com.kondra.vm.vmx.Section;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VmxReader {
    private FileInputStream stream;
    private Header header;
    private Section[] sections;
    private List<VmxExt> extensions;
    private List<Integer> extensionSizes;
    private List<Integer> sectionSizes;





    public VmxReader(File file){
        try {
            this.sections = new Section[4];
            extensionSizes = new ArrayList<>();
            sectionSizes = new ArrayList<>();
            this.extensions = new ArrayList<VmxExt>();
            this.stream = new FileInputStream(file);
            this.header = readHeader();
            for(int i = 0; i<4; i++){
                this.sections[i]=readSectionHeader();
            }
            for(int i = 0; i<header.getExtCount(); i++){
                extensions.add(readExtensionHeader());
            }
            for(int i = 0; i<3; i++){
                readSectionContent(this.sections[i]);
            }
            this.sections[3].setSection(new byte[sectionSizes.get(0)]);
            for(int i = 0; i<header.getExtCount(); i++){
                readExtensionContent(extensions.get(i));
            }

        }catch(FileNotFoundException ex){
            System.out.print("file not found");
        }
    }



    private Header readHeader(){
        int magic = readInt();
        byte extCount = readByte();
        byte vmxVariation = readByte();
        byte flags = readByte();
        byte reserved = readByte();
        byte major = readByte();
        byte minor = readByte();
        short buildNum = readShort();
        int fileSize = readInt();
        int programSize = readInt();
        int entryOffset = readInt();
        Header header =  new Header(magic, extCount, vmxVariation, flags, reserved, major, minor, buildNum, entryOffset);
        return header;

    }




    private VmxExt readExtensionHeader(){
        byte type = readByte();
        byte flags = readByte();
        short reserved = readShort();
        int offset = readInt();
        int size = readInt();
        extensionSizes.add(size);
        MyVmxExtReader reader = new MyVmxExtReader();
        return reader.readHeader(flags, type);
    }
    private void readExtensionContent(VmxExt vmxExt) {
        int extSize = extensionSizes.get(0);
        extensionSizes.remove(0);
        byte[] ext = new byte[extSize];
        for(int i = 0; i < extSize; i++){
            ext[i] = readByte();
        }
        int type = vmxExt.getType();
        MyVmxExtReader reader = null;
        switch(type){
            case(VmxExt.TYPE_RELOC):
                reader = new MyRelocExtReader();
                reader.readContent(vmxExt, ext);
                break;
            case(VmxExt.TYPE_SYMTAB):
                reader = new MySymbolTableExtReader();
                reader.readContent(vmxExt, ext);
                break;
            case(VmxExt.TYPE_PRELOAD):

            case(VmxExt.TYPE_EXPORT):

            case(VmxExt.TYPE_LABEL):

            case(VmxExt.TYPE_AFFINITY):
        }
    }


    private Section readSectionHeader(){
        int sOffset = readInt();
        int sSize = readInt();
        sectionSizes.add(sSize);
        return new Section();
    }
    private void readSectionContent(Section s){
        int sSize = sectionSizes.get(0);
        sectionSizes.remove(0);
        byte[] content = new byte[sSize];
        for(int i = 0; i<sSize; i++){
            content[i] = readByte();
        }
        s.setSection(content);
    }



    public MyVmxFile getVmxFile(){
        return new MyVmxFile(header, sections, extensions);
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

    public static int readInt(byte[] bytes, int offset){
        int temp = (bytes[offset])& 0xFF;
        temp |= ((bytes[offset+1])& 0xFF) <<8;
        temp |= ((bytes[offset+2])& 0xFF) <<16;
        temp |= ((bytes[offset+3])& 0xFF) <<24;
        return (int) temp;
    }

    public static short readShort(byte[] bytes, int offset){
        int temp = (bytes[offset])& 0xFF;
        temp |= ((bytes[offset+1])& 0xFF) <<8;
        return (short) temp;
    }













}
