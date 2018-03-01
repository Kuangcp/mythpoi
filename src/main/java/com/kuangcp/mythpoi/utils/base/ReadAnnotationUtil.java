package com.kuangcp.mythpoi.utils.base;

import com.kuangcp.mythpoi.excel.ExcelCellMeta;
import com.kuangcp.mythpoi.excel.base.ExcelTransform;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by https://github.com/kuangcp on 18-2-22  下午4:14
 * 读取具有注解类的注解值
 *
 * @author kuangcp
 */
public class ReadAnnotationUtil {

    /**
     * 获取类上注解, 得到Sheet的标题
     * @param target 目标类
     * @param export true/false 导出标题/导入标题
     * @return String 标题
     */
    public static String getSheetTitle(Class<? extends ExcelTransform> target, boolean export){
        ExcelSheet excelSheet = target.getAnnotation(ExcelSheet.class);
        if(excelSheet == null) return " ";
        if(export){
            return excelSheet.exportTitle();
        }
        return excelSheet.importTitle();
    }
    /**
     * 将原始对象集合转化成二维数据
     * @param target 目标类
     * @param originData 原始对象集合
     * @return List String [] 二维数据 也就是Excel的内容
     */
    public static List<Object[]> getContentByList(Class target,
                                                  List<? extends ExcelTransform> originData) throws Exception {
        return getContentByMetas(target, getCellMetaData(target), originData);
    }

    /**
     * 获取具有注解的属性,封装成list, 也就是Excel的标题行
     */
    public static List<ExcelCellMeta> getCellMetaData(Class target) {
        List<ExcelCellMeta> list = new ArrayList<>(0);
        final Field[] fields = target.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelConfig.class)) {
                ExcelConfig excelConfig = field.getAnnotation(ExcelConfig.class);
                if (excelConfig.exportFlag()) {list.add(new ExcelCellMeta(field, excelConfig.value()));}
            }
        }
        return list;
    }

    /**
     * 根据元数据, 将对象集合准换成二维数组
     */
//    private static List<String[]> getContentByMeta(Class target, List<ExcelCellMeta> metaData,
//                                                   List<? extends ExcelTransform> originData) throws Exception {
//        // 说明类里没有注解了的字段
//        if(metaData.size()==0){
//            return null;
//        }
//        List<String[]> dataList = new ArrayList<>(0);
//        for (ExcelTransform model : originData) {
//            String[] line = new String[metaData.size()];
//            int index = 0;
//            for (ExcelCellMeta meta : metaData) {
////                Object result = meta.getField().get(model); //这样是要public修饰才行
//                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(meta.getField().getName(), target);
//                //就是得到了属性的get方法
//                Method method = propertyDescriptor.getReadMethod();
//                Object result = method.invoke(model);
//                if(result == null){
//                    line[index] = "";
//                }else {
//                    line[index] = result.toString();
//                }
//                index++;
//            }
//            dataList.add(line);
//        }
//        return dataList;
//    }
    private static List<Object[]> getContentByMetas(Class target, List<ExcelCellMeta> metaData,
                                                   List<? extends ExcelTransform> originData) throws Exception {
        // 说明类里没有注解了的字段
        if(metaData.size()==0){
            return null;
        }
        List<Object[]> dataList = new ArrayList<>(0);
        for (ExcelTransform model : originData) {
            Object[] line = new Object[metaData.size()];
            int index = 0;
            for (ExcelCellMeta meta : metaData) {
//                Object result = meta.getField().get(model); //这样是要public修饰才行
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(meta.getField().getName(), target);
                //就是得到了属性的get方法
                Method method = propertyDescriptor.getReadMethod();
                Object result = method.invoke(model);
                if(result == null){
                    line[index] = "";
                }else {
                    line[index] = result;
                }
                index++;
            }
            dataList.add(line);
        }
        return dataList;
    }

    //    /**
//     * 获取 Excel对应实体的标题的Map
//     * @param target 目标类
//     * @return 返回Map field-value
//     */
//    public static Map<String, String> getFieldTitleMap(Class target){
//        Map<String, String> result = new HashMap<>();
//        final Field[] fields = target.getDeclaredFields();
//        for (Field field : fields){
//            if(field.isAnnotationPresent(MainConfig.class)){
//                MainConfig excelConfig = field.getAnnotation(MainConfig.class);
//                if(excelConfig.exportFlag()){
//                    result.put(field.getName(), excelConfig.name());
//                }
//            }
//        }
//        return result;
//    }
    // 没有找到使用场景没必要这样进行抽象, 可读性降低了
//    /**
//     * 想把注解类也动态传入, 但是注解类的实例的问题怎么办 只能拿到注解的实例,不好去细致的调用方法
//     * 但是能够写一个字符串的处理方法来达到同样的效果
//     * @param target 具有注解的类
//     * @param annotationClass 注解类
//     * @param name 注解的属性, 需要获取的值
//     * @return Map fieldName-Value 简单方便,虽然不易读, 但是数据结构很简单没必要使用新类
//     */
//    public static  Map<String, String> getFieldAnnotationMap(Class target, Class<? extends Annotation> annotationClass, String name){
//        Map<String, String> result = new HashMap<>();
//        final Field[] fields = target.getDeclaredFields();
//        for (Field field : fields){
//            if(field.isAnnotationPresent(annotationClass)){
//                Object obj = annotationClass.cast(field.getAnnotation(annotationClass));
//                result.put(field.getName(), getValue(obj.toString(), name));
//            }
//        }
//        return result;
//    }

//    /**
//     * 通过将对象的String进行截取拆分得到属性对应的值
//     * @param str 注解类的toString
//     * @param name 注解类中的属性名
//     * @return 值
//     */
//    private static String getValue(String str, String name){
//        if(str.contains(name)){
//            String [] temp = str.split(name);
//            return temp[1].substring(1, temp[1].length()-1);
//        }
//        return "";
//    }
}
