package org.nothink.ballcrm.util;

import java.util.BitSet;

public class TestBitMap {
    public static void main(String[] args){
        BitSet bitSet  = new BitSet(99999999);
        long start=System.currentTimeMillis();
        for (int i=1;i<=1000000000;i++){
            bitSet.set(i,true);
        }
        long end=System.currentTimeMillis();
        System.out.println(end-start);
    }
}
