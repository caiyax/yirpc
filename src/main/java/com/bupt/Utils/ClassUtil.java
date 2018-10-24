package com.bupt.Utils;

import javax.print.DocFlavor;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClassUtil {
    /*获取所有的类*/
    public static List<String> getClassName(String packageName){
        String filePath=ClassLoader.getSystemResource("").getPath();
        String packagePath=packageName.replace(".","/");
        String path=filePath+packagePath;
        List<String> classes=new ArrayList<>();
        getClassName(path,classes,packageName);
        return classes;
    }

    //递归找到项目包中的所有类
    public static void getClassName(String path, List<String> className, String packageName){
        File file=new File(path);
        File[] childFiles=file.listFiles();
        for (File child:childFiles){
            if (child.isFile()){
                if (child.getName().endsWith(".class")){
                    String name=child.getName().split("\\.")[0];
                    className.add(packageName+"."+name);
                }
            }else {
                //packageName拼接完整的类名
                getClassName(child.getPath(),className,packageName+"."+child.getName());
            }
        }
    }
}
