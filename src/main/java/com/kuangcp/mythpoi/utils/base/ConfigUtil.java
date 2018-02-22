package com.kuangcp.mythpoi.utils.base;

import java.lang.annotation.Annotation;
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
     * @param target 目标类
     * @return 返回Map
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
     * TODO 想把注解类也动态传入, 但是注解类的实例的问题怎么办 只能拿到注解的实例,不好去细致的调用方法
     * TODO 但是能够写一个字符串的处理方法来达到同样的效果
     * @param target 具有注解的类
     * @param annotationClass 注解类
     * @return map简单方便,虽然不易读, 但是数据结构很简单没必要使用新类
     */
    public Map<String, String> getFieldAnnotationMap(Class target, Class<? extends Annotation> annotationClass){
        Map<String, String> result = new HashMap<>();
        final Field[] fields = target.getDeclaredFields();
        for (Field field : fields){
            if(field.isAnnotationPresent(annotationClass)){
                Object obj = annotationClass.cast(field.getAnnotation(annotationClass));
                result.put(field.getName(), obj.toString());
            }
        }
        return result;
    }
}
