package com.kondra.vm;

import com.kondra.vm.common.memory.InsufficientMemoryException;
import com.kondra.vm.common.memory.Memory;
import com.kondra.vm.common.memory.MemoryMgr;

import java.util.HashMap;

public class MyMemoryMgr implements MemoryMgr {
    public Memory memory;
    public HashMap storage;

    public class Node{
        private int pointer;
        private int size;
        private boolean free;
        public Node(int pointer, int size) {
            this.pointer = pointer;
            this.size = size;
            free = true;
        }
        public int getNextPointer(){
            return pointer+size;
        }
        public int getPrevPointer(){
            return pointer-1;
        }
        public int getPointer(){
            return pointer;
        }
        public int getEnd(){
            return pointer+size-1;
        }
        public int getSize(){
            return size;
        }
        public void setSize(int newSize){
            this.size = newSize;
        }
        public boolean isFree(){
            return free;
        }
        public void setFree(boolean newFree){
            this.free = newFree;
        }
    }

    public MyMemoryMgr(){
        this.memory = null;
        this.storage = new HashMap<Integer, Node>();
    }


    @Override
    public void setMemory(Memory memory) {
        this.memory = memory;
        Node n = new Node(0, memory.size());
        add(n);
        allocate(4);
    }

    @Override
    public Memory getMemory() {
        return this.memory;
    }

    @Override
    public int allocate(int size) throws InsufficientMemoryException {
        size= (size+3) &~3;
        if(this.memory.size() < size){
            throw new InsufficientMemoryException();
        }
        Node n = getFree(size);
        split(n, size);
        return n.getPointer();
    }

    @Override
    public void free(int addr) {
        Node n = (Node)this.storage.get(addr);
        Node prev = getPrev(n);
        Node next = getNext(n);
        n.setFree(true);
        n = merge(prev, n);
        merge (n, next);

    }
    private void add(Node n){
        storage.put(n.getPointer(), n);
        storage.put(n.getEnd(), n);
    }

    private void remove(Node n){
        storage.remove(n.getPointer());
        storage.remove(n.getEnd());
    }

    private Node getNext(Node n){
        return (Node) this.storage.get(n.getNextPointer());
    }
    private Node getPrev(Node n){
        return (Node) this.storage.get(n.getPrevPointer());
    }

    private void split(Node n1, int size){
        if(size<n1.getSize()){remove(n1);
        Node n2 = new Node(n1.getPointer()+size, n1.getSize()-size);
        n1.setFree(false);
        n1.setSize(size);
        add(n1);
        add(n2);}
        else{
            n1.setFree(false);
        }
    }
    private Node merge(Node n1, Node n2) {
        if (n1 == null || !n1.isFree()) {
            return n2;
        }
        if (n2 == null || !n2.isFree()) {
            return n1;
        }
        Node n3 = new Node(n1.getPointer(), n1.getSize()+n2.getSize());
        remove(n1);
        remove(n2);
        add(n3);
        return n3;
    }
    private Node getFree(int size){
        Node best = null;
        Node n = (Node) this.storage.get(0);
        while(n!=null){
            if (n.isFree() && n.getSize()>=size){
                if(best==null || n.getSize()<best.getSize()){
                        best = n;
                }
            }
            n = getNext(n);
        }
        if (best==null){throw new InsufficientMemoryException();}

        return best;
    }

    @Override
    public void reset() {
        this.storage = new HashMap<Integer, Node>(); //<pointer, node>
    }



}
