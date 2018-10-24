package com.bupt.service;

import com.bupt.client.RpcClient;
import com.bupt.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RpcService {
    @Autowired
    private RpcClient rpcClient;
    public void bussise(){
        Person student= rpcClient.create(Person.class);
        String res=student.say();
        System.out.println(res);
    }
}
