package com.bupt.handle;

import com.bupt.Utils.ClassUtil;
import com.bupt.annotation.RpcService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RpcHandle {
    private static Map<String,Class<?>> classMap=new HashMap<>();
    public static Map<String,Class<?>> getRpcMap(){
        String packageName="com.bupt";
        List<String> list= ClassUtil.getClassName(packageName);
        list.forEach(name->{
            try {
                Class clazz= Class.forName(name);
                if (clazz.isAnnotationPresent(RpcService.class)){
                    classMap.put(name,clazz);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return classMap;
    }
}
