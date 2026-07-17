package com.kondra.vm.vmx.readers;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.ext.Affinity;
import com.kondra.vm.vmx.ext.MyAffinity;
import com.kondra.vm.vmx.ext.MyAffinityExt;
import com.kondra.vm.vmx.writers.VmxWriter;

import java.util.ArrayList;
import java.util.List;

public class MyAffinityExtReader extends MyVmxExtReader{

    @Override
    public void readContent(VmxExt vmxExt, byte[] ext){
        List<Affinity> affinities = new ArrayList<>();
        for (int i = 0; i < ext.length; i = i+12){
            int major = (int) VmxReader.readShort(ext, i);
            int minor = (int) VmxReader.readShort(ext, i+2);;
            int offset = (int) VmxReader.readInt(ext, i+4);;
            affinities.add(new MyAffinity(major, minor, offset));
        }
        ((MyAffinityExt)vmxExt).setAffinities(affinities);
    }
}
