package com.kondra.vm.vmx.writers;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.ext.Export;
import com.kondra.vm.vmx.ext.MyExportExt;

import java.util.ArrayList;
import java.util.List;

public class MyExportExtWriter extends MyVmxExtWriter{
    VmxExt vmxExt;
    public MyExportExtWriter(VmxExt vmxExt) {
        super(vmxExt);
        this.vmxExt = vmxExt;
    }
    @Override
    public byte[] writeContent(){
        List<Export> exports = (((MyExportExt)vmxExt).getExports());
        byte[] bytes = new byte[(exports.size() *8)];
        int i = 0;
        for (Export e : exports){
            int symbolOffset = e.getSymbolOffset();
            int addressOffset = e.getAddressOffset();
            int section = e.getSection();
            int maskedAddress = addressOffset & 0x03FFFFFF;
            int shiftedSection = section << 26;
            int word2 = shiftedSection | maskedAddress;
            VmxWriter.writeInt(bytes, i, symbolOffset);
            VmxWriter.writeInt(bytes, i+4, word2);
            i = i+8;

        }
        return bytes;
    }
}
