package com.viger.mycode;

public class TestBean {

    private String name = "myname";

    private TestBean() {
       /// System.arraycopy();
        System.out.println("TestBean()===>");
    }

    public String getName() {
        return "bbb";
    }

    private int getAge() {
        return 11;
    }

    //@CheckNet(value = {1,9},name="aa")
    @CheckNet
    public void doSomething(String info) {
        System.out.println("doSomething===>" + info);
    }


}
