package com.viger.mycode.paixu;

/**
 * 排序
 */
public class Test01 {

    private static int[] array = {2,1,8,5,9,3,7};

    public static void main(String[] args) {
        printArray(array);
        //maopao(array);
        //xuanze(array);
        insert(array);
        printArray(array);
    }

    //插入排序
    public static void insert(int[] a) {
        //j 当前位置前一位
        //t当前位置元素
        int j,t;
        for (int i = 1; i <a.length; i++) {
            t=a[i];
            j=i-1;
            while (j>=0 && t<a[j])
            {
                a[j+1]=a[j];
                j--;
            }
            a[j+1]=t;
            printArray(a);
        }

    }

    //选择排序
    private static void xuanze(int[] array) {
        int k = 0;
        for(int i=0;i<array.length-1;i++) {
            k = i;
            for(int j=i;j<array.length;j++) {
                if(array[j] < array[k]) {
                    k = j;
                }
            }
            int temp = array[k];
            array[k] = array[i];
            array[i] = temp;
        }
    }

    //冒泡排序
    private static void maopao(int[] array) {
        for(int i=0;i<array.length-1;i++) {
            for(int j = array.length-1;j > i;j--) {
                if(array[j] < array[j-1]) {
                    int temp = array[j];
                    array[j] = array[j-1];
                    array[j-1] = temp;
                }
            }
        }


    }


//    private static void maopao(int[] array) {
//        for(int i=0;i<array.length-1;i++) {
//            for(int j=array.length-1;j>i;j--) {
//                if(array[j] < array[j-1]) {
//                    int temp = array[j];
//                    array[j] = array[j-1];
//                    array[j-1] = temp;
//                }
//            }
//        }
//    }

    private static void printArray(int[] array) {
        System.out.print("[ ");
        for(int i=0;i<array.length;i++) {
            System.out.print(array[i] + " ");
        }
        System.out.print(" ]");
        System.out.println();
    }



}
