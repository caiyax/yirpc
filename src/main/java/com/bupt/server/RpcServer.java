package com.bupt.server;

import com.bupt.handle.RpcHandle;
import com.bupt.protocol.Request;
import com.bupt.protocol.Response;
import com.bupt.protocol.SerializationUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcServer {
    private static Map<String,Class<?>> classMap;
    private static ExecutorService newCachedThreadPool=
            Executors.newCachedThreadPool();
    public static void main(String[] args) {
        int port=12345;
        classMap = RpcHandle.getRpcMap();
        try {
            ServerSocket serverSocket=new ServerSocket(port);
            while (true){
                final Socket socket=serverSocket.accept();
                newCachedThreadPool.submit(()->socketHandle(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void socketHandle(Socket socket){
        try {
            byte[] bytes=new byte[1024];
            InputStream inputStream=socket.getInputStream();
            OutputStream outputStream=socket.getOutputStream();
            while (true){
                int read=inputStream.read(bytes);
                if (read==-1) break;
                Request request = (Request) SerializationUtil.deserialize(bytes);
                Response response=requestHandle(request);
                byte[] res=SerializationUtil.serialize(response);
                outputStream.write(res);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static Response requestHandle(Request request){
        Class clazz = classMap.get(request.getClassName());
        Response response=null;
        try {
            Object object=clazz.newInstance();
            Method method =clazz.getMethod(request.getMethodName(),request.getParametersTypes());
            Object result=method.invoke(object);
            response=new Response();
            response.setRequestId(request.getId());
            response.setResult(result);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return response;
    }
}
