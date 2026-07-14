package com.kondra.vm.vmx.writers;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.vmx.MyVmxFile;
import com.kondra.vm.vmx.ext.MySymbolTableExt;

import java.util.List;

public class MySymbolTableExtWriter extends MyVmxExtWriter {
    private VmxExt vmxExt;

    public MySymbolTableExtWriter(VmxExt vmxExt){
        super(vmxExt);
        this.vmxExt = vmxExt;
    }

    @Override
    public byte[] writeContent(){
        int len = ((MySymbolTableExt)vmxExt).getNextOffset()-1;
        if (len % 4 == 1){
            len = len + 3;
        }
        else if (len % 4 == 2){
            len = len + 2;
        }
        else if (len % 4 == 3){
            len = len + 1;
        }
        byte[] ext = new byte[len];
        List<String> strings = ((MySymbolTableExt)vmxExt).getSymbols();
        int index = 0;
        for(String s: strings){
            for(int i = 0; i<s.length(); i++){
                ext[index] = s.getBytes()[i];
                index++;
            }
            index++;
        }
        return ext;
    }


}
