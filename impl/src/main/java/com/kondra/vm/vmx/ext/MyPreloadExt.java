package com.kondra.vm.vmx.ext;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.ext.PreloadExt;
import com.kondra.vm.vmx.writers.VmxWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyPreloadExt implements PreloadExt {
    private byte flags;
    private List<Integer> symbolOffsets;

    public MyPreloadExt(byte flags) {
        this.flags = flags;
        symbolOffsets = new ArrayList<>();
    }

    @Override
    public void addSymbolOffset(int offset) {symbolOffsets.add(offset);}

    @Override
    public List<Integer> getSymbolOffsets() {return symbolOffsets;}

    @Override
    public int getType() {return VmxExt.TYPE_PRELOAD;}

    public byte getFlags(){return flags;}

}


