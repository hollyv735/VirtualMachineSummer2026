package com.kondra.vm.vmx;

import com.kondra.vm.common.Version;
import com.kondra.vm.common.vmx.VmxException;
import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.VmxFile;
import com.kondra.vm.vmx.writers.VmxWriter;

import java.io.File;
import java.util.List;

public class MyVmxFile implements VmxFile {
    private Header header;
    private Section[] sections;
    private List<VmxExt> extensions;

    public MyVmxFile(Header header, Section[] sections, List<VmxExt> extensions){
        this.header = header;
        this.sections = sections;
        this.extensions = extensions;
    }
    @Override
    public void write(File file) throws VmxException {
        VmxWriter writer = new VmxWriter(this);
        writer.write(file);

    }

    @Override
    public byte[] getSection(int i) {
        return sections[i].getSection();
    }

    @Override
    public void setSection(int i, byte[] bytes) {
        sections[i].setSection(bytes);
    }

    @Override
    public List<VmxExt> getExtensions() {
        return extensions;
    }

    @Override
    public VmxExt getExtension(int i) {
        return extensions.get(i);
    }

    @Override
    public Version getVersion() {
        return new Version((int) header.getMajor(), (int)header.getMinor(), (int)header.getBuildNum());
    }

    @Override
    public void setVersion(Version version) {
        header.setBuildNum((short)version.getBuildNum());
        header.setMinor((byte)version.getMinor());
        header.setMajor((byte)version.getMajor());
    }



    @Override
    public int getFlags() {
        return header.getFlags();
    }

    @Override
    public void setFlags(int i) {
        header.setFlags((byte)i);
    }

    @Override
    public int getEntryOffset() {
        return header.getEntryOffset();
    }

    @Override
    public void setEntryOffset(int i) {
        header.setEntryOffset(i);
    }

    public Header getHeader(){
        return header;
    }
    public Section[] getSections(){
        return sections;
    }

}
