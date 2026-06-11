package com.kondra.vm;

import com.kondra.vm.common.CPU;
import com.kondra.vm.common.StackOverflowException;
import com.kondra.vm.common.concurrent.VmThread;
import com.kondra.vm.common.memory.Memory;

public class MyCPU implements CPU {
    private VmThread thread;
    private int[] register;
    private int iPtr;
    private int nextIPtr;
    private MyMemory memory;

    public MyCPU(VmThread thread){
        this.thread = thread;
        this.register = new int[34];
        setIPtr(0);
        memory = new MyMemory(1024);

    }
    @Override
    public int getRegister(int reg) {
        return this.register[reg];
    }

    @Override
    public void setRegister(int reg, int val) {
        this.register[reg] = val;
    }

    @Override
    public int getIPtr() {
        return this.iPtr;
    }

    @Override
    public void setIPtr(int iPtr) {
        this.iPtr = iPtr;
        this.nextIPtr = iPtr + 4;

    }

    @Override
    public int getNextIPtr() {
        return this.nextIPtr;
    }

    @Override
    public void setNextIPtr(int nextIPtr) {
        this.nextIPtr = nextIPtr;
    }
    //improve this code
    @Override
    public Memory getMemory() {
        return memory;
    }

    @Override
    public void execute(int count) throws StackOverflowException {
        for(int i = 0; i < count; i++){
            int instr = memory.getInt(iPtr);
            setIPtr(nextIPtr);
            int mask = ((1 << 5) - 1);
            int sa = (instr >> 6) & mask;
            int rd = (instr >> 11) & mask;
            int id = (instr>>26) & 0x3f;
            int rt = (instr >> 16) & mask;
            int rs = (instr >> 21) & mask;
            int target = instr & ((1 << 26) - 1);
            short imm = (short) instr;
            int im = (int) imm;
            long rrt = ((long) register[rt]) & 0xffffffffl;
            long rrs = ((long) register[rs]) & 0xffffffffl;
            switch (id) {
                case(0):
                    int id1 = (instr & ((1 << 6) - 1));
                    switch (id1) {
                        //sll
                        case (0):
                            register[rd] = register[rt] << sa;
                        break;
                        //srl
                        case(2):
                            register[rd] = register[rt] >>> sa;
                        break;
                        //sra
                        case(3):
                            register[rd] = register[rt] >> sa;
                        break;
                        //sllv
                        case(4):
                            register[rd] = (register[rt] << register[rs]);
                        break;
                        //srav
                        case(6):
                            register[rd] = register[rt] >>> register[rs];
                        break;
                        //srlv
                        case(7):
                            register[rd] = (register[rt] >> register[rs]);
                        break;
                        //add
                        case(32):
                            register[rd] = register[rt] + register[rs];
                        break;
                        //addu
                        case(33):
                            register[rd] = register[rt] + register[rs];
                        break;
                        //sub
                        case(34):
                            register[rd] = register[rs] - register[rt];
                        break;
                        //subu
                        case(35):
                            register[rd] = register[rs] - register[rt];
                        break;
                        //and
                        case(36):
                            register[rd] = register[rt] & register[rs];
                        break;
                        //nor
                        case(39):
                            register[rd] = ~(register[rt] | register[rs]);
                        break;
                        //or
                        case(37):
                            register[rd] = register[rt] | register[rs];
                        break;
                        //xor
                        case(38):
                            register[rd] = (register[rt] ^ register[rs]);
                        break;
                        //mfhi
                        case(16):
                            register[rd] = register[REG_HI];
                        break;
                        //mflo
                        case(18):
                            register[rd] = register[REG_LO];
                        break;
                        //mthi
                        case(17):
                            register[REG_HI] = register[rs];
                        break;
                        //mtlo
                        case(19):
                            register[REG_LO] = register[rs];
                        break;
                        //mult
                        case(24):
                            long temp = ((long)register[rs])* (long)(register[rt]);
                            register[REG_HI] = (int) (temp>>32);
                            register[REG_LO] = (int) temp;
                        break;
                        //multu
                        case(25):
                            long temp1 = rrs * rrt;
                            register[REG_HI] = (int) (temp1>>>32);
                            register[REG_LO] = (int) temp1;
                        break;
                        //div
                        case(26):
                            register[REG_LO] = (int) register[rs]/register[rt];
                            register[REG_HI] = (int) register[rs]%register[rt];
                        break;
                        //divu
                        case(27):
                            register[REG_LO] = (int) (rrs/rrt);
                            register[REG_HI] = (int) (rrs%rrt);
                        break;
                        //slt
                        case(42):
                            if (register[rs]<register[rt]) {
                                register[rd] = 1;
                            }
                            else {
                                register[rd] = 0;
                            }
                        break;
                        //sltu
                        case(43):
                            if (rrs<rrt) {
                                register[rd] = 1;
                            }
                            else {
                                register[rd] = 0;
                            }
                        break;
                        //jr
                        case(8):
                            nextIPtr = register[rs];
                        break;
                        //jalr
                        case(9):
                            register[rd] = nextIPtr;
                            nextIPtr = register[rs];
                        break;

                    }
                break;

                //addi
                case(8):
                //addiu
                case(9):
                    register[rt] = register[rs] + imm;
                break;
                //andi
                case(12):
                    register[rt] = register[rs] & im;
                break;
                //ori
                case(13):
                    register[rt] = register[rs] | im;
                break;
                //xori
                case(14):
                    register[rt] = (register[rs] ^ im);
                break;
                //lui - incomplete
                case(15):
                    register[rt] = im << 16;
                break;
                //slti
                case(10):
                    if (register[rs]<im) {

                        register[rt] = 1;
                    }
                    else {
                        register[rt] = 0;
                    }
                break;
                //sltiu
                case(11):

                    if (rrs<im) {
                        register[rt] = 1;
                    }
                    else {
                        register[rt] = 0;
                    }
                break;
                //lb
                case(32):
                    register[rt] = memory.getByte(im + register[rs]);
                break;
                //lbu
                case(36):
                    register[rt] = (memory.getByte(im + register[rs]))&0xff;
                break;
                //lh
                case(33):
                    register[rt] = memory.getShort(im + register[rs]);
                break;
                //lhu
                case(37):
                    register[rt] = (memory.getShort(im + register[rs]))&0xffff;
                break;
                //lw
                case(35):
                    register[rt] = memory.getInt(im + register[rs]);
                break;
                //sb
                case(40):
                    memory.setByte((im + register[rs]), (byte)register[rt]);
                break;
                //sh
                case(41):
                    memory.setShort((im + register[rs]), (short)register[rt]);
                break;
                //sw
                case(43):
                    memory.setInt((im + register[rs]), register[rt]);
                break;
                //lwl
                case(34):
                    int addr = register[rs] + im;
                    int temp = memory.getAlignedInt(addr);
                    int shift = (addr&3)<<3;
                    mask = 0xffffffff << shift;
                    register[rt] &= ~mask;
                    register[rt] |= (temp<<shift);
                break;
                //lwr
                case(38):
                    addr = register[rs] + im;
                    temp = memory.getAlignedInt(addr);
                    shift = (3-(addr&3))<<3;
                    mask = (int)(0xffffffffl >> shift);
                    register[rt] &= ~mask;
                    register[rt] |= (temp>>shift) & mask;
                break;
                //swl
                case(42):
                    addr = register[rs] + im;
                    temp = register[rt];
                    shift = (addr&3)<<3;
                    mask = (int)(0xffffffffl >> shift);
                    int mem = memory.getAlignedInt(addr);
                    mem &= ~mask;
                    mem |= (temp>>shift) & mask;
                    memory.setAlignedInt(addr,mem);
                break;
                //swr
                case(46):
                    addr = register[rs] + im;
                    temp = register[rt];
                    shift = (3-(addr&3))<<3;
                    mask = 0xffffffff << shift;
                    mem = memory.getAlignedInt(addr);
                    mem &= ~mask;
                    mem |= (temp<<shift);
                    memory.setAlignedInt(addr,mem);
                break;
                case(1):
                    switch(rt) {
                        //bltz
                        case(0):
                            if (register[rs] < 0) {
                                nextIPtr = iPtr + (int)(im << 2);
                            }
                        break;
                        //bgez
                        case(1):
                            if (register[rs] >= 0) {
                                nextIPtr = iPtr + (int)(im << 2);
                            }
                        break;
                        //bltzal
                        case(16):
                            register[31] = nextIPtr;
                            if (register[rs] < 0) {
                                nextIPtr = iPtr + (int)(im << 2);
                            }

                        break;
                        //bgezal
                        case(17):
                            register[31] = nextIPtr;
                            if (register[rs] >= 0) {
                                nextIPtr = iPtr + (int)(im << 2);
                            }
                        break;

                    }
                break;
                //j
                case(2):
                    nextIPtr = (iPtr&0xfffffff0) | (int)(target << 2);
                break;
                //jal
                case(3):
                    register[31] = nextIPtr;
                    nextIPtr = (iPtr&0xfffffff0) | (int)(target << 2);

                break;
                //beq
                case(4):
                    if (register[rs] == register[rt]) {
                        nextIPtr = iPtr + (int)(im << 2);
                    }
                break;
                //bne
                case(5):
                    if (register[rs] != register[rt]) {
                        nextIPtr = iPtr + (int)(im << 2);
                    }
                break;
                //bgtz
                case(7):
                    if (register[rs] > 0) {
                        nextIPtr = iPtr + (int)(im << 2);
                    }
                break;
                //blez
                case(6):
                    if (register[rs] <= 0) {
                        nextIPtr = iPtr + (int)(im << 2);
                    }
                break;
                //b
                case(20):
                    nextIPtr = iPtr + (im << 2);
                break;
                //bal
                case(21):
                    register[REG_RA] = nextIPtr;
                    nextIPtr = iPtr + (im << 2);
                break;


            }
            register[0] = 0;
        }
    }

    //not day one
    @Override
    public void execute() throws StackOverflowException {

    }
    //not day one
    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public VmThread getThread() {
        return this.thread;
    }
}
