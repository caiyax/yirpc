package com.bupt.model;

import com.bupt.client.RpcClient;
import com.bupt.protocol.Request;
import com.bupt.protocol.Response;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RpcProxy<T> implements InvocationHandler {
    private Class<T> clazz;
    public RpcProxy(Class<T> clazz){
        this.clazz=clazz;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request=new Request();
        request.setId(System.currentTimeMillis());
        request.setClassName("com.bupt.model.Student");//method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParametersTypes(method.getParameterTypes());
        request.setParameters(method.getParameters());
        RpcClient client=new RpcClient();
        Response response=client.sendRequest(request);
        Object result=response.getResult();
        return result;
    }
}
