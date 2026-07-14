package com.kondra.vm.vmx.readers;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.vmx.ext.*;
import com.kondra.vm.vmx.writers.MySymbolTableExtWriter;
import com.kondra.vm.vmx.writers.MyVmxExtWriter;
import com.kondra.vm.vmx.writers.VmxWriter;

public class MyVmxExtReader {

    public VmxExt readHeader(byte flags, byte type){
        switch(type){
            case(VmxExt.TYPE_RELOC):
                return new MyRelocationExt(flags);
            case(VmxExt.TYPE_SYMTAB):
                return new MySymbolTableExt(flags);
            case(VmxExt.TYPE_PRELOAD):
                return new MyPreloadExt(flags);
            case(VmxExt.TYPE_EXPORT):
                return new MyExportExt(flags);
            case(VmxExt.TYPE_LABEL):
                return new MyLabelExt(flags);
            case(VmxExt.TYPE_AFFINITY):
                return new MyAffinityExt(flags);
        }
        return null;
    }

    public void readContent(VmxExt vmxExt, byte[] ext){
    }


}
