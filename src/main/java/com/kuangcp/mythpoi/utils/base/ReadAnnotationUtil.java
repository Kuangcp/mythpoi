package com.kuangcp.mythpoi.utils.base;

import com.kuangcp.mythpoi.excel.ExcelCellMeta;
import com.kuangcp.mythpoi.excel.base.ExcelTransform;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
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
     *
     * @param target 目标类
     * @param export true/false 导出标题/导入标题
     * @return String 标题
     */
    public static String getSheetTitle(Class<? extends ExcelTransform> target, boolean export) {
        ExcelSheet excelSheet = target.getAnnotation(ExcelSheet.class);
        if (excelSheet == null) return " ";
        if (export) {
            return excelSheet.exportTitle();
        }
        return excelSheet.importTitle();
    }

    /**
     * 将原始对象集合转化成二维数据
     *
     * @param target     目标类
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
                if (excelConfig.exportFlag()) {
                    list.add(new ExcelCellMeta(field, excelConfig.value()));
                }
            }
        }
        return list;
    }

    private static List<Object[]> getContentByMetas(Class target, List<ExcelCellMeta> metaData,
                                                    List<? extends ExcelTransform> originData) throws Exception {
        // 说明类里没有注解了的字段
        if (metaData.size() == 0) {
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
                if (result == null) {
                    line[index] = "";
                } else {
                    line[index] = result;
                }
                index++;
            }
            dataList.add(line);
        }
//        dataList.forEach(item -> System.out.println(Arrays.toString(item)));
        return dataList;
    }
}