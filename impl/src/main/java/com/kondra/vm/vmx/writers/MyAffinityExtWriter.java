package com.kondra.vm.vmx.writers;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.ext.Affinity;
import com.kondra.vm.common.vmx.ext.Export;
import com.kondra.vm.vmx.ext.MyAffinity;
import com.kondra.vm.vmx.ext.MyAffinityExt;
import com.kondra.vm.vmx.ext.MyExportExt;

import java.util.List;

public class MyAffinityExtWriter extends MyVmxExtWriter{
    VmxExt vmxExt;

    public MyAffinityExtWriter(VmxExt vmxExt) {
        super(vmxExt);
        this.vmxExt = vmxExt;
    }


    @Override
    public byte[] writeContent(){
        List<Affinity> affinities = (((MyAffinityExt)vmxExt).getAffinityList());
        byte[] bytes = new byte[(affinities.size() *12)];
        int i = 0;
        for (Affinity a : affinities){
            short major = (short) a.getMajorVersion();
            short minor = (short) a.getMinorVersion();
            int offset = a.getSymbolOffset();
            VmxWriter.writeShort(bytes, i, major);
            VmxWriter.writeShort(bytes, i+2, major);
            VmxWriter.writeInt(bytes, i+4, offset);
            i=i+12;
        }
        return bytes;
    }

}
