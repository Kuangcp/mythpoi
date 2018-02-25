package com.kuangcp.mythpoi.excel.util;

import com.kuangcp.mythpoi.excel.ExcelCellMeta;

import java.lang.reflect.Field;
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
}
