package com.kondra.vm.vmx.writers;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.VmxFile;
import com.kondra.vm.vmx.ext.*;
import com.kondra.vm.vmx.readers.MySymbolTableExtReader;

public class MyVmxExtWriter {
    private int offset;
    private VmxExt vmxExt;

    public MyVmxExtWriter(VmxExt vmxExt){
        this.vmxExt = vmxExt;
    }


    public void setOffset(int o){
        offset = o;
    }

    public int getSize(){
        int size;
        MyVmxExtWriter writer;
        switch(vmxExt.getType()){
            case(VmxExt.TYPE_RELOC):
                writer = new MyRelocExtWriter(vmxExt);
                size = writer.writeContent().length;
                return size;
            case(VmxExt.TYPE_SYMTAB):
                writer = new MySymbolTableExtWriter(vmxExt);
                size = writer.writeContent().length;
                return size;
            case(VmxExt.TYPE_PRELOAD):

            case(VmxExt.TYPE_EXPORT):

            case(VmxExt.TYPE_LABEL):

            case(VmxExt.TYPE_AFFINITY):
        }
        return 0;

    }

    public byte[] writeHeader(){
        byte[] header = new byte[12];
        byte type = (byte)vmxExt.getType();
        header[0] = type;
        VmxWriter.writeInt(header, 4, offset);
        int size = getSize();
        byte flags;
        VmxWriter.writeInt(header, 8, size);
        switch(type){
            case(VmxExt.TYPE_RELOC):
                flags = ((MyRelocationExt)vmxExt).getFlags();
                header[1] = flags;
                break;
            case(VmxExt.TYPE_SYMTAB):
                flags = ((MySymbolTableExt)vmxExt).getFlags();
                header[1] = flags;
                break;
            case(VmxExt.TYPE_PRELOAD):

            case(VmxExt.TYPE_EXPORT):

            case(VmxExt.TYPE_LABEL):
                flags = ((MyPreloadExt)vmxExt).getFlags();
                header[1] = flags;
                break;
            case(VmxExt.TYPE_AFFINITY):
        }





        return header;
    }


    public byte[] writeContent() {
        int type = vmxExt.getType();
        switch(type){
            case(VmxExt.TYPE_RELOC):
                return (new MyRelocExtWriter(vmxExt)).writeContent();
            case(VmxExt.TYPE_SYMTAB):
                return (new MySymbolTableExtWriter(vmxExt)).writeContent();
            case(VmxExt.TYPE_PRELOAD):

            case(VmxExt.TYPE_EXPORT):

            case(VmxExt.TYPE_LABEL):
                return (new MyLabelExtWriter(vmxExt)).writeContent();

            case(VmxExt.TYPE_AFFINITY):
        }
        return null;
    }

}
