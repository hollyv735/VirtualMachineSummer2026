package com.kondra.vm.vmx.writers;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.vmx.ext.MyLabelExt;

import java.util.Date;

import static com.kondra.vm.vmx.writers.VmxWriter.writeInt;

public class MyLabelExtWriter extends MyVmxExtWriter{
    private VmxExt vmxExt;
    public MyLabelExtWriter(VmxExt vmxExt) {
        super(vmxExt);
        vmxExt = vmxExt;
    }
    @Override
    public byte[] writeContent(){
        byte[] bytes = new byte[36];
        long time = (((MyLabelExt)vmxExt).getTimestamp()).getTime();
        writeInt(bytes, 0, (int)(time/1000));
        byte[] string = ((MyLabelExt)vmxExt).getLabel().getBytes();
        for(int i = 0; i<string.length; i++){
            bytes[4+i] = string[i];
        }
        return bytes;
    }

}
