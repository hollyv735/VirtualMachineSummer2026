package com.kondra.vm.vmx.ext;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.ext.LabelExt;
import com.kondra.vm.vmx.writers.VmxWriter;

import java.util.Date;

public class MyLabelExt implements LabelExt {
    private byte flags;
    private Date timestamp;
    private String label;

    public MyLabelExt (byte flags) {
        this.flags = flags;
    }

    public byte getFlags(){return flags;}

    @Override
    public String getLabel() {return label;}

    @Override
    public void setLabel(String label) {this.label = label;}

    @Override
    public Date getTimestamp() {return timestamp;}

    @Override
    public int getType() {return VmxExt.TYPE_LABEL;}

    @Override
    public void setTimestamp(Date timestamp) {this.timestamp = timestamp;}
}


