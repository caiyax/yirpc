package com.bupt.client;

import com.bupt.model.RpcProxy;
import com.bupt.protocol.Request;
import com.bupt.protocol.Response;
import com.bupt.protocol.SerializationUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class RpcClient {
    public static <T> T create(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new RpcProxy<>(interfaceClass)
        );
    }
    public Response sendRequest(Request request){
        int port=12345;
        String host="127.0.0.1";
        Socket socket=null;
        Response response=null;
        try {
            byte[] bytes= SerializationUtil.serialize(request);
            socket=new Socket(host,port);
            OutputStream outputStream=socket.getOutputStream();
            InputStream inputStream=socket.getInputStream();
            outputStream.write(bytes);
            outputStream.flush();
            byte[] res=new byte[1024];
            inputStream.read(res);
            response= (Response) SerializationUtil.deserialize(res);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (socket!=null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }
    /*public static void main(String[] args) {
        int port=12345;
        String host="127.0.0.1";
        Socket socket=null;
        try {
            Request request=new Request();
            request.setId(System.currentTimeMillis());
            request.setClassName("com.bupt.model.Student");
            request.setMethodName("say");
            byte[] bytes= SerializationUtil.serialize(request);
            socket=new Socket(host,port);
            OutputStream outputStream=socket.getOutputStream();
            InputStream inputStream=socket.getInputStream();
            outputStream.write(bytes);
            outputStream.flush();
            byte[] res=new byte[1024];
            inputStream.read(res);
            Response response= (Response) SerializationUtil.deserialize(res);
            System.out.println(response.getRequestId());
            System.out.println(response.getResult());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
}
