package com.bupt.protocol;

import com.bupt.Utils.ClassUtil;
import com.bupt.annotation.RpcService;
import com.bupt.handle.RpcHandle;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SerializationUtilTest {

    @Test
    public void serialize() {
    }

    @Test
    public void deserialize() {
    }

    @Test
    public void testSerializa() throws IllegalAccessException, InstantiationException, InvocationTargetException {
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
        for (Method method:methods) {
            if (method.getName().equals(r2.getMethodName())) {
                Object res = method.invoke(object);
                Response response = new Response();
                response.setRequestId(r2.getId());
                response.setResult(res);
            }
        }
    }
    @Test
    public void testReflect(){
        String packageName="com.bupt";
        List<String> list= ClassUtil.getClassName(packageName);
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