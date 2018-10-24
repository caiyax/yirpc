package com.bupt.protocol;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

public class SerializationUtil {
    public static byte[] serialize(Object object){
        ByteArrayOutputStream byteStream=new ByteArrayOutputStream();
        ObjectOutputStream outputStream= null;
        byte[] bytes=null;
        try {
            outputStream = new ObjectOutputStream(byteStream);
            outputStream.writeObject(object);
            outputStream.flush();
            outputStream.close();
            bytes=byteStream.toByteArray();
            byteStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
    public static Object deserialize(byte[] bytes){
        Object object=null;
        try {
            ByteArrayInputStream inputStream=new ByteArrayInputStream(bytes);
            ObjectInputStream stream=new ObjectInputStream(inputStream);
            object=stream.readObject();
            stream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }
}
