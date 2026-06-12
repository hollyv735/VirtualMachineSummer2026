package com.kondra.vm.vmx;

import com.kondra.vm.common.vmx.VmxExt;

public class MyVmxExt implements VmxExt {
        private byte type;
        private byte flags;
        private short reserved;
        private int offset;
        private int size;

        public MyVmxExt(byte type, byte flags, short reserved, int offset, int size){
            this.type = type;
            this.flags = flags;
            this.reserved = reserved;
            this.offset = offset;
            this.size = size;
        }

    @Override
    public int getType() {
        return type;
    }
}
