package com.kuangcp.mythpoi.utils;

import com.kuangcp.mythpoi.excel.base.BaseConfig;
import com.kuangcp.mythpoi.excel.base.MainConfig;
import com.kuangcp.mythpoi.utils.base.ExcelConfig;
import org.ho.yaml.Yaml;
import org.junit.Test;
import sun.applet.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.nio.charset.MalformedInputException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by https://github.com/kuangcp on 18-2-22  下午3:41
 * 测试读取注解信息
 *  注意: 注解在什么对象上就拿到该对应对象调用 isAnnotationPresent 方法即可判断是否有对应注解存在
 *
 * @author kuangcp
 */
public class ReadAnnotation {

    @Test
    public void readConfig() throws ClassNotFoundException {
        final Class cls = Class.forName("com.kuangcp.mythpoi.utils.Employee");

//        final Method[] methodList = cls.getMethods();
//        for(Method method : methodList){
//            System.out.println(method.toString());
//        }
        final Field[] fields = cls.getDeclaredFields();
        for (Field field : fields){
            if(field.isAnnotationPresent(ExcelConfig.class)){
                System.out.print("具有注解  ");
                ExcelConfig excelConfig = field.getAnnotation(ExcelConfig.class);
                System.out.print(excelConfig.name());
            }
            System.out.println(field.getName());
        }

        // 判断是否有指定注解类型的注解
//        if (cls.isAnnotationPresent(MainConfig.class)) {
//            // 根据注解类型返回指定类型的注解
//            MainConfig des = (MainConfig) cls.getAnnotation(MainConfig.class);
//            System.out.println("注解描述:" + des.name());
//        }else{
//            System.out.println("没有?");
//        }
    }

}
