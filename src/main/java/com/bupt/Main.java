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

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Person student= RpcClient.create(Person.class);
        String res=student.say();
        System.out.println(res);
    }
    public static void testSerializa() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Map<String,Class<?>> classMap = RpcHandle.getRpcMap();
        Request request=new Request();
        request.setId(System.currentTimeMillis());
        request.setClassName("com.bupt.model.Student");
        request.setMethodName("say");
        byte[] bytes= SerializationUtil.serialize(request);
        Request r2 = (Request) SerializationUtil.deserialize(bytes);
        Class clazz=classMap.get(r2.getClassName());
        Object object=clazz.newInstance();
        Method[] methods=clazz.getMethods();
        for (Method method:methods){
            if (method.getName().equals(r2.getMethodName())){
                Object res=method.invoke(object);
                Response response=new Response();
                response.setRequestId(r2.getId());
                response.setResult(res);
            }
        }
    }
    public static void testReflect(){
        String packageName="com.bupt";
        List<String> list=ClassUtil.getClassName(packageName);
        for (String name:list){
            try {
                Class cls=Class.forName(name);
                if (cls.isAnnotationPresent(RpcService.class)){
                    System.out.println(cls.getName());

                    Object object=cls.newInstance();
                    Method[] methods=cls.getMethods();
                    for (Method method:methods){
                        if (method.getName().equals("say")){
                            Object res=method.invoke(object);
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
