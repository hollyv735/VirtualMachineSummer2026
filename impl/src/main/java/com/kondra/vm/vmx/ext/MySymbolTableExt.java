package com.kondra.vm.vmx.ext;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.ext.SymbolTableExt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MySymbolTableExt implements SymbolTableExt {
    private HashMap<Integer, String> offsetSymbol;
    private HashMap<String, Integer> symbolOffset;
    private int nextOffset;
    private byte flags;


    public MySymbolTableExt(byte flags) {
        this.flags = flags;
        offsetSymbol = null;
        symbolOffset = null;
        nextOffset = 0;
    }
    public void setSymbolTable(HashMap<Integer, String> offsetSymbol, HashMap<String, Integer> symbolOffset, int nextOffset){
        this.offsetSymbol = offsetSymbol;
        this.symbolOffset = symbolOffset;
        this.nextOffset = nextOffset;
    }

    @Override
    public void addSymbol(int offset, String symbol) {
        if (offset<nextOffset){
            offset = nextOffset;
            nextOffset = offset+symbol.length();
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
        for(int i = 0; i<nextOffset; i++){
            if (offsetSymbol.containsKey(i)){
                strings.add(offsetSymbol.get(i));
            }
        }
        return strings;
    }

    @Override
    public int getNextOffset() {
        return nextOffset;
    }

    @Override
    public int getType() {
        return VmxExt.TYPE_SYMTAB;
    }

    public byte getFlags(){
        return flags;
    }







}
