package com.bupt;

import com.bupt.Utils.ClassUtil;
import com.bupt.annotation.RpcService;
import com.bupt.client.RpcClient;
import com.bupt.handle.RpcHandle;
import com.bupt.model.Person;
import com.bupt.model.RpcProxy;
import com.bupt.model.Student;
import com.bupt.protocol.Request;
import com.bupt.protocol.Response;
import com.bupt.protocol.SerializationUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


}
