package com.kondra.vm.vmx.ext;

import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.vmx.VmxWriter;

public class MyVmxExt implements VmxExt {
        private byte type;
        private byte flags;
        private short reserved;
        private int offset;
        private int size;
        private byte[] ext;

        public MyVmxExt(byte type, byte flags, short reserved, int offset, int size){
            this.type = type;
            this.flags = flags;
            this.reserved = reserved;
            this.offset = offset;
            this.size = size;
        }

        public void setExt(byte[] ext){
            this.ext = ext;
        }

    public int getSize() {
        return size;
    }

    @Override
    public int getType() {
        return type;
    }
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

    public byte[] writeExtContent(){
            return ext;
    }


}
