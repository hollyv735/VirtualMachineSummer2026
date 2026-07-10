package com.kondra.vm.vmx.readers;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.ext.Relocation;
import com.kondra.vm.vmx.ext.MyRelocation;
import com.kondra.vm.vmx.ext.MyRelocationExt;
import com.kondra.vm.vmx.ext.MySymbolTableExt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyRelocExtReader extends MyVmxExtReader{
    byte[] ext;


    @Override
    public void readContent(VmxExt vmxExt, byte[] ext){
        int textOffset = VmxReader.readInt(ext, 0);
        int textSize = VmxReader.readInt(ext, 4);
        int rodataOffset = VmxReader.readInt(ext, 8);
        int rodataSize = VmxReader.readInt(ext, 12);
        int dataOffset = VmxReader.readInt(ext, 16);
        int dataSize = VmxReader.readInt(ext, 20);
        int bssOffset = VmxReader.readInt(ext, 24);
        int bssSize = VmxReader.readInt(ext, 28);
        this.ext = ext;
        List<Relocation> textRelocations = new ArrayList<>();
        List<Relocation> rodataRelocations = new ArrayList<>();
        List<Relocation> dataRelocations = new ArrayList<>();
        List<Relocation> bssRelocations = new ArrayList<>();
        for(int i = textOffset; i<textSize+textOffset; i = i+8){
            textRelocations.add(getReloc(i));
        }
        ((MyRelocationExt)vmxExt).setRelocations(textRelocations, 0);
        for(int i = rodataOffset; i<rodataSize+rodataOffset; i = i+8){
            rodataRelocations.add(getReloc(i));
        }
        ((MyRelocationExt)vmxExt).setRelocations(rodataRelocations, 1);
        for(int i = dataOffset; i<dataSize+dataOffset; i = i+8){
            dataRelocations.add(getReloc(i));
        }
        ((MyRelocationExt)vmxExt).setRelocations(dataRelocations, 2);
        for(int i = bssOffset; i<bssSize+bssOffset; i = i+8){
            bssRelocations.add(getReloc(i));
        }
        ((MyRelocationExt)vmxExt).setRelocations(bssRelocations, 3);

    }

    private Relocation getReloc(int offset){
        byte[] bytes = new byte[8];
        for (int i = 0; i<8; i++){
            bytes[i] = ext[i+offset];
        }
        return new MyRelocation(bytes);
    }

}
