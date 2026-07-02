package com.kondra.vm.vmx.ext;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.ext.SymbolTableExt;
import com.kondra.vm.vmx.VmxWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MySymbolTableExt extends MyVmxExt implements SymbolTableExt {
    //from MyVmxExt
    private byte type;
    private byte flags;
    private short reserved;
    private int offset;
    private int size;
    private byte[] ext;
    private HashMap<Integer, String> offsetSymbol;
    private HashMap<String, Integer> symbolOffset;


    public MySymbolTableExt(byte type, byte flags, short reserved, int offset, int size) {
        super(type, flags, reserved, offset, size);
        this.type = type;
        this.flags = flags;
        this.reserved = reserved;
        this.offset = offset;
        this.size = size;
    }

    @Override
    public void addSymbol(int offset, String symbol) {
        byte[] symbolBytes = symbol.getBytes();
        int symbolSize = symbolBytes.length;
        byte[] newExt = new byte[size + symbolSize+1];
        for(int i = 0; i<offset; i++){
            newExt[i] = ext[i];
        }
        for(int i = offset; i<offset+symbolSize; i++){
            newExt[i] = symbolBytes[i];
        }
        for(int i = offset+symbolSize+1; i<size; i++){
            newExt[i] = ext[i];
        }
        offsetSymbol.put(offset, symbol);
        symbolOffset.put(symbol, offset);
    }

    @Override
    public String getSymbol(int offset) {
        return offsetSymbol.get(offset);
    }

    @Override
    public int getOffset(String symbol) {
        return symbolOffset.get(symbol);
    }

    @Override
    public List<String> getSymbols() {
        List<String> strings = new ArrayList<>();
        for(int i = 0; i<size; i++){
            if (offsetSymbol.containsKey(i)){
                strings.add(offsetSymbol.get(i));
            }
        }
        return strings;
    }

    @Override
    public int getNextOffset() {
        return 0;
    }

    @Override
    public int getType() {
        return VmxExt.TYPE_SYMTAB;
    }



    //from MyVmxExt
    @Override
    public void setExt(byte[] ext){
        this.ext = ext;
        int offset = 0;
        String symbol = "";
        for (int i = 0; i<size; i++){
            if (ext[i]+"" == "\0") {
                offsetSymbol.put(offset, symbol);
                symbolOffset.put(symbol, offset);
                offset = i+1;
                symbol = "";
            }
            else {
                symbol += ext[i];
            }
        }




    }
    @Override
    public int getSize() {
        return size;
    }
    @Override
    public byte[] writeExtHeader(){
        byte[] ext = new byte[12];
        ext[0] = type;
        ext[1] = flags;
        byte[] temp = VmxWriter.writeShort(reserved);
        for(int i = 0; i<2; i++){
            ext[2+i] = temp[i];
        }
        temp = VmxWriter.writeInt(offset);
        for(int i = 0; i<4; i++){
            ext[4+i] = temp[i];
        }
        temp = VmxWriter.writeInt(size);
        for(int i = 0; i<4; i++){
            ext[8+i] = temp[i];
        }
        return ext;
    }

    @Override
    public byte[] writeExtContent(){
        return ext;
    }


}
