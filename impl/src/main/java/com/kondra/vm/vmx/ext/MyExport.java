package com.kondra.vm.vmx.ext;

import com.kondra.vm.common.vmx.ext.Export;
import com.kondra.vm.common.vmx.ext.ExportExt;

public class MyExport implements Export {
    private int symbolOffset;
    private int section;
    private int addressOffset;

    public MyExport(int symbolOffset, int section, int addressOffset){
        this.symbolOffset = symbolOffset;
        this.section = section;
        this.addressOffset = addressOffset;

    }

    @Override
    public int getSymbolOffset() {
        return symbolOffset;
    }

    @Override
    public void setSymbolOffset(int offset) {
        symbolOffset = offset;
    }

    @Override
    public int getAddressOffset() {
        return addressOffset;
    }

    @Override
    public void setAddressOffset(int offset) {
        addressOffset = offset;
    }

    @Override
    public int getSection() {
        return section;
    }

    @Override
    public void setSection(int section) {
        this.section = section;
    }
}


