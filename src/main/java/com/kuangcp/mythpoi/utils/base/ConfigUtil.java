package com.kuangcp.mythpoi.utils.base;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by https://github.com/kuangcp on 18-2-22  下午4:14
 *
 * @author kuangcp
 */
public class ConfigUtil {

    /**
     * 获取 Excel对应实体的标题的Map
     * @return
     */
    public Map<String, String> getFieldTitleMap(Class target){
        Map<String, String> result = new HashMap<>();
        final Field[] fields = target.getDeclaredFields();
        for (Field field : fields){
            if(field.isAnnotationPresent(ExcelConfig.class)){
                ExcelConfig excelConfig = field.getAnnotation(ExcelConfig.class);
                result.put(field.getName(), excelConfig.name());
            }
        }
        return result;
    }

    /**
     * TODO 想把注解类也动态传入, 但是示例的问题怎么办
     * @param target 具有注解的类
     * @param annotationClass 注解类
     * @return map简单方便,虽然不易读, 但是数据结构很简单没必要使用新类
     */
    public Map<String, String> getFieldAnnotationMap(Class target, Class annotationClass){
        Map<String, String> result = new HashMap<>();
        final Field[] fields = target.getDeclaredFields();
        for (Field field : fields){
            if(field.isAnnotationPresent(annotationClass)){
                ExcelConfig excelConfig = field.getAnnotation(ExcelConfig.class);
                result.put(field.getName(), excelConfig.name());
            }
        }
        return result;
    }
}
