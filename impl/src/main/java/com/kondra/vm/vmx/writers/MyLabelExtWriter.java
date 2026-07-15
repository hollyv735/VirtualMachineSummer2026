package com.kondra.vm.vmx.writers;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.vmx.ext.MyLabelExt;

import java.util.Date;

import static com.kondra.vm.vmx.writers.VmxWriter.writeInt;

public class MyLabelExtWriter extends MyVmxExtWriter{
    private VmxExt vmxExt;
    public MyLabelExtWriter(VmxExt vmxExt) {
        super(vmxExt);
        this.vmxExt = vmxExt;
    }
    @Override
    public byte[] writeContent(){
        byte[] bytes = new byte[36];
        for (int i = 0; i<36; i++){
            bytes[i] = 0;
        }
        long time = (((MyLabelExt)vmxExt).getTimestamp()).getTime();
        VmxWriter.writeInt(bytes, 0, (int)(time/1000));
        VmxWriter.writeString(bytes, 4, ((MyLabelExt)vmxExt).getLabel());
        /*
        byte[] string = ((MyLabelExt)vmxExt).getLabel().getBytes();
        for(int i = 0; (i<32); i++){
            bytes[4+i] = string[i];
        }
         */
        return bytes;
    }

}
