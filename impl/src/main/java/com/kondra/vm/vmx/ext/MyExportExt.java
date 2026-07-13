package com.kondra.vm.vmx.ext;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.ext.Export;
import com.kondra.vm.common.vmx.ext.ExportExt;
import com.kondra.vm.vmx.writers.VmxWriter;

import java.util.ArrayList;
import java.util.List;
public class MyExportExt implements ExportExt {
    private byte flags;
    List<Export> exports;

    public MyExportExt(byte type, byte flags, short reserved, int offset, int size) {
        this.flags = flags;
    }

    @Override
    public List<Export> getExports() {
        return exports;
    }

    public void setExports(List<Export> exports){
        this.exports = exports;
    }

    @Override
    public int getType() {return VmxExt.TYPE_EXPORT;}
    public byte getFlags(){return flags;}

}

