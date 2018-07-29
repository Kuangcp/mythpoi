package com.kuangcp.mythpoi.excel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;

/**
 * Created by https://github.com/kuangcp on 18-2-23  上午10:43
 * Excel单元格的元数据类, 实体的属性,以及注解的标题名
 *
 * @author kuangcp
 */
@Data
@AllArgsConstructor
public class ExcelCellMeta {

  /**
   * 对象的属性对象
   */
  private Field field;
  /**
   * 对象的标题名
   */
  private String title;
}
