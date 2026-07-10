package com.kondra.vm.vmx.writers;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.ext.Relocation;
import com.kondra.vm.vmx.ext.MyRelocation;
import com.kondra.vm.vmx.ext.MyRelocationExt;

import java.util.List;

public class MyRelocExtWriter extends MyVmxExtWriter{
    VmxExt vmxExt;

    public MyRelocExtWriter(VmxExt vmxExt) {
        super(vmxExt);
        this.vmxExt = vmxExt;
    }

    @Override
    public byte[] writeContent(){
        int textSize = (((MyRelocationExt)vmxExt).getRelocations(0).size())*8;
        int rodataSize = (((MyRelocationExt)vmxExt).getRelocations(1).size())*8;
        int dataSize = (((MyRelocationExt)vmxExt).getRelocations(2).size())*8;
        int bssSize = (((MyRelocationExt)vmxExt).getRelocations(3).size())*8;
        byte[] bytes = new byte[((textSize + rodataSize + dataSize + bssSize)) + 32];
        List<Relocation> relocations;
        //were gonna go through this each section at a time
        VmxWriter.writeInt(bytes, 0, 32);
        VmxWriter.writeInt(bytes, 4, textSize);
        VmxWriter.writeInt(bytes, 8, textSize+32);
        VmxWriter.writeInt(bytes, 12, rodataSize);
        VmxWriter.writeInt(bytes, 16, rodataSize+32+textSize);
        VmxWriter.writeInt(bytes, 20, dataSize);
        VmxWriter.writeInt(bytes, 24, dataSize+32+textSize+rodataSize);
        VmxWriter.writeInt(bytes, 28, bssSize);
        int offset = 32;
        relocations = ((MyRelocationExt)vmxExt).getRelocations(0);
        for (Relocation r : relocations){
            for(int i = 0; i<8; i++){
                bytes[offset + i] = ((MyRelocation)r).getBytes()[i];
            }
            offset = offset + 8;
        }
        relocations = ((MyRelocationExt)vmxExt).getRelocations(1);
        for (Relocation r : relocations){
            for(int i = 0; i<8; i++){
                bytes[offset + i] = ((MyRelocation)r).getBytes()[i];
            }
            offset = offset + 8;
        }
        relocations = ((MyRelocationExt)vmxExt).getRelocations(2);
        for (Relocation r : relocations){
            for(int i = 0; i<8; i++){
                bytes[offset + i] = ((MyRelocation)r).getBytes()[i];
            }
            offset = offset + 8;
        }
        relocations = ((MyRelocationExt)vmxExt).getRelocations(3);
        for (Relocation r : relocations){
            for(int i = 0; i<8; i++){
                bytes[offset + i] = ((MyRelocation)r).getBytes()[i];
            }
            offset = offset + 8;
        }


        return bytes;


    }
}
