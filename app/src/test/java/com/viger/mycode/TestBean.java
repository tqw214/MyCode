package com.viger.mycode;

import java.util.HashSet;

public class TestBean {

    private String name = "myname";

    private TestBean() {
       /// System.arraycopy();
        System.out.println("TestBean()===>");
    }

    public String getName() {
        return "bbb";// test
    }

    private int getAge() {
        return 11;
    }

    //@CheckNet(value = {1,9},name="aa")
    @CheckNet
    public void doSomething(String info) {
        System.out.println("doSomething===>" + info);
    }

    class Solution {
        public int lengthOfLongestSubstring(String s) {
            int res = 0, left = 0, right = 0;
            HashSet<Character> t = new HashSet<Character>();
            while (right < s.length()) {
                if (!t.contains(s.charAt(right))) {
                    t.add(s.charAt(right++));
                    res = Math.max(res, t.size());
                } else {
                    t.remove(s.charAt(left++));
                }
            }
            return res;
        }
    }


}
