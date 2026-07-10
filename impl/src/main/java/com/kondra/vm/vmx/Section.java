package com.kondra.vm.vmx;

import com.kondra.vm.vmx.writers.VmxWriter;

public class Section{
    private byte[] section;


    public void setSection(byte[] section){
        this.section = section;
    }

    public byte[] getSection(){
        return section;
    }


}
