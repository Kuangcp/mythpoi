package com.kuangcp.mythpoi.excel.util;

import com.kuangcp.mythpoi.excel.ExcelCellMeta;
import com.kuangcp.mythpoi.utils.config.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by https://github.com/kuangcp on 18-2-25  下午11:00
 * Excel一些零碎的工具方法
 * @author kuangcp
 */
public class ExcelUtil {

    private static Map<String, Field> metaMap;

    /**
     * 降低时间复杂度,减少一个for循环
     * @param list metaList
     * @param title Excel的
     * @return 属性对象
     */
    public static Field getOneByTitle(List<ExcelCellMeta> list, String title){
        if(metaMap == null || metaMap.size()==0) {
            metaMap = new HashMap<>(0);
            list.forEach(item->metaMap.put(item.getTitle(), item.getField()));
        }
        return metaMap.get(title);
    }

    public static void invokeValue(CellType type, Field colField, Object target, HSSFCell cell) throws IllegalAccessException, ParseException {
        String fieldType = colField.getType().getSimpleName();
        if(CellType.STRING.equals(type)){
            if (Date.class.getSimpleName().equals(fieldType)) {
                colField.set(target, DateUtil.parse(cell.getStringCellValue()));
            }else {
                colField.set(target, cell.getStringCellValue());
            }
        }

        if(CellType.NUMERIC.equals(type)){
            if (Integer.class.getSimpleName().equals(fieldType) || int.class.getSimpleName().equals(fieldType)) {
                colField.set(target, (int) cell.getNumericCellValue());
            }else if (Long.class.getSimpleName().equals(fieldType) || long.class.getSimpleName().equals(fieldType)) {
                colField.set(target, (long) cell.getNumericCellValue());
            }else {
                colField.set(target, cell.getNumericCellValue());
            }
        }
        if(CellType.BOOLEAN.equals(type)){
            colField.set(target, cell.getBooleanCellValue());
        }

        if(CellType.BLANK.equals(type)){
            colField.set(target, "");
        }
    }
}
