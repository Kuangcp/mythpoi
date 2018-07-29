package com.kuangcp.mythpoi.excel.util;

import com.kuangcp.mythpoi.excel.ExcelCellMeta;
import com.kuangcp.mythpoi.utils.config.DateUtil;
import java.util.Objects;
import org.apache.poi.hssf.usermodel.HSSFCell;
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
 *
 * @author kuangcp
 */
public class ExcelUtil {

  private static Map<String, Field> metaMap;

  /**
   * 降低时间复杂度,减少一个for循环
   *
   * @param list metaList
   * @param title Excel的标题
   * @return 属性对象
   */
  public static Field getColFieldByTitle(List<ExcelCellMeta> list, String title) {
    if (Objects.isNull(metaMap) || metaMap.isEmpty()) {
      metaMap = new HashMap<>(0);
      list.forEach(item -> metaMap.put(item.getTitle(), item.getField()));
    }
    return metaMap.get(title);
  }

  /**
   * 根据 cell 装载值 到目标对象上
   *
   * @param type 类型
   * @param colField 列类型
   * @param target 目标对
   * @param cell 单元格对象
   */
  public static void loadCellValue(CellType type, Field colField, Object target, HSSFCell cell)
      throws IllegalAccessException, ParseException {
    String fieldType = colField.getType().getSimpleName();

    switch (type) {
      case NUMERIC:
        if (Integer.class.getSimpleName().equals(fieldType) || int.class.getSimpleName()
            .equals(fieldType)) {
          colField.set(target, (int) cell.getNumericCellValue());
          break;
        }
        if (Long.class.getSimpleName().equals(fieldType) || long.class.getSimpleName()
            .equals(fieldType)) {
          colField.set(target, (long) cell.getNumericCellValue());
          break;
        }
        colField.set(target, cell.getNumericCellValue());
        break;
      case STRING:
        if (Date.class.getSimpleName().equals(fieldType)) {
          colField.set(target, DateUtil.parse(cell.getStringCellValue()));
        } else {
          colField.set(target, cell.getStringCellValue());
        }
        break;
      case BOOLEAN:
        colField.set(target, cell.getBooleanCellValue());
        break;
      case BLANK:
        colField.set(target, "");
        break;
    }
  }
}
