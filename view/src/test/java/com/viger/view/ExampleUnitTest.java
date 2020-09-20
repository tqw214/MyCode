package com.viger.view;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test01() {
        int arr[] = new int[]{1,3,4,2,3};
        int result = getDuplicate(arr);
        System.out.println("重复数字="+result);
    }

    private int getDuplicate(int[] arr) {
        if(arr == null || arr.length <=0) {
            System.out.println("数组无效");
            return -1;
        }

        for(int a : arr) {
            if(a < 0 || a > arr.length-1) {
                System.out.println("数字超出范围");
                return -1;
            }
        }
        for(int i=0;i<arr.length;i++) {
            int temp;
            while(arr[i] != i) {
                if(arr[arr[i]] == arr[i]) {
                    return arr[i];
                }
                temp = arr[i];
                arr[i] = arr[temp];
                arr[temp] = temp;
            }
        }
        System.out.println("无重复数字");
        return -1;
    }

}