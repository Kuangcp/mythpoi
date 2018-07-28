package com.kuangcp.mythpoi.excel.type;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Created by https://github.com/kuangcp on 18-3-12  上午11:14
 *
 * @author kuangcp
 */
public interface LoadCellValue {

  /**
   * 创建row的cell, 根据传入的String值
   *
   * @param row HSSFRow
   * @param index cell的下标0开始
   * @param value 装载的值
   * @return HSSFCell 单元格对象
   */
  HSSFCell loadValue(HSSFRow row, int index, Object value);
}
