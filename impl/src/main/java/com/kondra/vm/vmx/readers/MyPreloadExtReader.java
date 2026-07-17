package com.kondra.vm.vmx.readers;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.vmx.ext.MyPreloadExt;
import com.kondra.vm.vmx.writers.VmxWriter;

public class MyPreloadExtReader extends MyVmxExtReader{

    @Override
    public void readContent(VmxExt vmxExt, byte[] ext){
        for(int i = 0; i<ext.length; i=i+4){
            ((MyPreloadExt)vmxExt).addSymbolOffset(VmxReader.readInt(ext, i));
        }
    }


}
