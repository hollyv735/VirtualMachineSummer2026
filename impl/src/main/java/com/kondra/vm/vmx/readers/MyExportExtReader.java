package com.kondra.vm.vmx.readers;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.ext.Export;
import com.kondra.vm.vmx.ext.MyExport;
import com.kondra.vm.vmx.ext.MyExportExt;

import java.util.ArrayList;
import java.util.List;

public class MyExportExtReader extends MyVmxExtReader{

    @Override
    public void readContent(VmxExt vmxExt, byte[] ext){
        List<Export> exports = new ArrayList<>();
        for (int i = 0; i < ext.length; i = i+8){
            int symbolOffset = VmxReader.readInt(ext, i);
            int word2 = VmxReader.readInt(ext, i+4);
            int section = word2 >> 26;
            int addressOffset = word2 & 0x03ffffff;
            exports.add(new MyExport(symbolOffset, section, addressOffset));
        }
        ((MyExportExt)vmxExt).setExports(exports);

    }
}
