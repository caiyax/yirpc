package com.bupt.model;

import com.bupt.annotation.RpcService;
import lombok.Data;

@RpcService("student")
public class Student implements Person{
    private String name;
    public Student(){}
    public Student(String name){
        this.name=name;
    }
    @Override
    public String say() {
        System.out.println("stu say");
        return "hello";
    }
}
