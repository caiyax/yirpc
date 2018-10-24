package com.bupt.client;

import com.bupt.model.RpcProxy;
import com.bupt.protocol.Request;
import com.bupt.protocol.Response;
import com.bupt.protocol.SerializationUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Proxy;
import java.net.Socket;

@Component
public class RpcClient {
    @Value("${rpc.host}")
    private String host;
    @Value("${rpc.port}")
    private Integer port;
    public <T> T create(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new RpcProxy<>(interfaceClass)
        );
    }
    public Response sendRequest(Request request){
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
}
