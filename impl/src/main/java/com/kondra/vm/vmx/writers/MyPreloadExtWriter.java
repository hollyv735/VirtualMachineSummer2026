package com.kondra.vm.vmx.writers;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.vmx.ext.MyPreloadExt;
import com.kondra.vm.vmx.readers.VmxReader;

import java.util.List;

public class MyPreloadExtWriter extends MyVmxExtWriter{
    VmxExt vmxExt;
    public MyPreloadExtWriter(VmxExt vmxExt) {
        super(vmxExt);
        this.vmxExt = vmxExt;
    }

    @Override
    public byte[] writeContent(){
        List<Integer> offsets = ((MyPreloadExt)vmxExt).getSymbolOffsets();
        byte[] bytes = new byte[(offsets.size() * 4)];
        int offset = 0;
        for (Integer i: offsets){
            VmxWriter.writeInt(bytes, offset, i);
            offset = offset +4;
        }
        return bytes;
    }
}
