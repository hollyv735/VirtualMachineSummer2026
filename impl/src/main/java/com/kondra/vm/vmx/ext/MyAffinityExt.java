package com.kondra.vm.vmx.ext;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.ext.Affinity;
import com.kondra.vm.common.vmx.ext.AffinityExt;
import com.kondra.vm.vmx.writers.VmxWriter;

import java.util.ArrayList;
import java.util.List;

public class MyAffinityExt implements AffinityExt {
    private byte flags;
    private List<Affinity> affinities;

    public MyAffinityExt(byte flags) {this.flags = flags;}

    @Override
    public List<Affinity> getAffinityList() {return affinities;}

    @Override
    public int getType() {return VmxExt.TYPE_AFFINITY;}
    public byte getFlags(){return flags;}
    public void setAffinities(List<Affinity> affinities){
        this.affinities = affinities;
    }

}

