package com.viger.mvp.bean;

import javax.inject.Inject;

public class Student {

    private String message = "student message";

    @Inject
    public Student() {

    }

    public String getMessage() {
        return this.message;
    }

}
