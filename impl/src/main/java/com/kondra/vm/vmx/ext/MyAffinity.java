package com.kondra.vm.vmx.ext;

import com.kondra.vm.common.vmx.ext.Affinity;
public class MyAffinity implements Affinity {
    private int major;
    private int minor;
    private int offset;

    public MyAffinity(int major, int minor, int offset){
        this.major = major;
        this.minor = minor;
        this.offset = offset;
    }

    @Override
    public int getMajorVersion() {
        return major;
    }

    @Override
    public void setMajorVersion(int major) {
        this.major = major;
    }

    @Override
    public int getMinorVersion() {
        return minor;
    }

    @Override
    public void setMinorVersion(int minor) {
        this.minor = minor;
    }

    @Override
    public int getSymbolOffset() {
        return offset;
    }

    @Override
    public void setSymbolOffset(int offset) {
        this.offset = offset;
    }
}

