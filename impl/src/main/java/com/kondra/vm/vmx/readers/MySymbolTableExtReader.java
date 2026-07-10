package com.kondra.vm.vmx.readers;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.vmx.ext.MySymbolTableExt;

import java.util.HashMap;

public class MySymbolTableExtReader extends MyVmxExtReader {



    @Override
    public void readContent(VmxExt vmxExt, byte[] ext){
        HashMap<Integer, String> offsetSymbol = new HashMap<>();
        HashMap<String, Integer> symbolOffset = new HashMap<>();
        int offset = 0;
        String symbol = "";
        for (int i = 0; i<ext.length; i++){
            if (ext[i]+"" == "\0") {
                offsetSymbol.put(offset, symbol);
                symbolOffset.put(symbol, offset);
                symbol = "";
            }
            else {
                symbol += ext[i];
            }
        }
        ((MySymbolTableExt)vmxExt).setSymbolTable(offsetSymbol, symbolOffset, ext.length);
    }



}
