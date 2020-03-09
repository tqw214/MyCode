package com.viger.mycode.paixu;

import org.junit.Test;

public class Test02 {


    @Test
    public void maopao() {
        int[] array = {3,9,1,5,2,8,6,11,33,22,0};
        boolean isSwap;
        for(int i=0;i<array.length-1;i++) {
            isSwap = false;
            for(int j=0;j<array.length-1-i;j++) {
                if(array[j] > array[j+1]) {
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                    isSwap = true;
                }
            }
            if(!isSwap) {
                //
                System.out.println("已经好了");
                break;
            }

        }

        for(int i=0;i<array.length;i++) {
            System.out.print(array[i] + " ");
        }

    }



}
