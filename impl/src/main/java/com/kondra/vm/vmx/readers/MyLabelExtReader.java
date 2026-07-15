package com.kondra.vm.vmx.readers;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.vmx.ext.MyLabelExt;

import java.util.Date;

import static com.kondra.vm.vmx.readers.VmxReader.readInt;

public class MyLabelExtReader extends MyVmxExtReader{
    byte[] ext;

    @Override
    public void readContent(VmxExt vmxExt, byte[] ext){
        this.ext = ext;
        String label = VmxReader.readString(ext, 4);
        Date timestamp = new Date((long)(readInt(ext, 0))*1000);
        ((MyLabelExt)vmxExt).setLabel(label);
        ((MyLabelExt)vmxExt).setTimestamp(timestamp);
    }
}
