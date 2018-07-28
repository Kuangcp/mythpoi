package com.kuangcp.mythpoi.excel;

import com.kuangcp.mythpoi.excel.base.ExcelTransform;
import com.kuangcp.mythpoi.excel.base.MainConfig;
import com.kuangcp.mythpoi.excel.type.*;
import com.kuangcp.mythpoi.utils.base.ReadAnnotationUtil;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by https://github.com/kuangcp on 18-2-21  下午12:47
 * Excel导出工具类
 * TODO 异常的合理处理
 * 目前单元格的类型尚不支持公式和错误
 * Boolean 缺省为false,
 * 字符串缺省为空串, 数值类型为空则是0
 *
 * @author kuangcp
 */
@Slf4j
public class ExcelExport {

  private static MainConfig mainConfig = MainConfig.getInstance();
  private static HSSFWorkbook workbook = new HSSFWorkbook();
  private static Map<String, LoadCellValue> handlerMap = new HashMap<>(7);

  // 字典结合策略模式简化代码
  static {
    handlerMap.put("String", new StringHandler());
    handlerMap.put("Date", new DateHandler());
    handlerMap.put("Boolean", new BooleanHandler());
    handlerMap.put("Long", new LongHandler());
    handlerMap.put("Integer", new IntegerHandler());
    handlerMap.put("Double", new DoubleHandler());
    handlerMap.put("Float", new FloatHandler());
  }

  /**
   * @param filePath 文件的绝对路径
   * @param originData 主要数据
   */
  public static boolean exportExcel(String filePath, List<? extends ExcelTransform> originData) {
    try {
      File file = new File(filePath);
      OutputStream out = new FileOutputStream(file);
      return exportExcel(out, originData);
    } catch (FileNotFoundException e) {
      log.error("file not found", e);
      return false;
    }
  }

  /**
   * @param outputStream 输出流
   * @param originData 原始对象集合
   */
  public static boolean exportExcel(OutputStream outputStream,
      List<? extends ExcelTransform> originData) {
    try {
      if (Objects.isNull(originData) || originData.isEmpty()) {
        return false;
      }
      Class<? extends ExcelTransform> target = originData.get(0).getClass();
      String sheetTitle = ReadAnnotationUtil.getSheetTitle(target, true);
      List<Object[]> dataList = ReadAnnotationUtil.getContentByList(target, originData);

      if (Objects.isNull(dataList)) {
        log.error("{} 中没有已注解的字段, 导出失败", target.getSimpleName());
        return false;
      }

      HSSFSheet sheet = workbook.createSheet(sheetTitle);
      HSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);
      setSheetTitle(sheet, dataList, sheetTitle, columnTopStyle);
      setColumnTitle(dataList, sheet, target, columnTopStyle);
      setContent(dataList, sheet);
      workbook.write(outputStream);
    } catch (Exception e) {
      log.error("export error ", e);
    }
    return true;
  }

  /**
   * 设置sheet的列头
   */
  private static void setColumnTitle(List<Object[]> dataList, HSSFSheet sheet, Class target,
      HSSFCellStyle columnTopStyle) {
    List<ExcelCellMeta> metaList = ReadAnnotationUtil.getCellMetaData(target);
    HSSFRow row = sheet.createRow(mainConfig.getTitleTotalNum());
    int columnNum = dataList.get(mainConfig.getStartRowNum()).length;
    for (int n = 0; n < columnNum; n++) {
      //创建列头对应个数的单元格
      HSSFCell cellRowName = row.createCell(n);
      //设置列头单元格的数据类型

      cellRowName.setCellType(CellType.STRING);
      HSSFRichTextString text = new HSSFRichTextString(metaList.get(n).getTitle());
      cellRowName.setCellValue(text);
      cellRowName.setCellStyle(columnTopStyle);
    }
  }

  /**
   * 根据List来创造出一行的cell, 使用策略模式是因为要从多种的对象类型转换成Excel的特定类型
   */
  private static void createRowCell(Object[] obj, int index, HSSFRow row) {
    HSSFCellStyle style = getStyle(workbook);
    Object temp = obj[index];
    HSSFCell cell = handlerMap.get(temp.getClass().getSimpleName()).loadValue(row, index, temp);

    if (!Objects.isNull(cell)) {
      cell.setCellStyle(style);
    }
  }

  /**
   * cell分为: 空格 布尔类型(TRUE FALSE) 字符串 数值 | 错误 公式
   * 填充sheet内容
   */
  private static void setContent(List<Object[]> dataList, HSSFSheet sheet) {
    for (int m = 0; m < dataList.size(); m++) {
      Object[] obj = dataList.get(m);
      HSSFRow row = sheet.createRow(m + mainConfig.getContentStartNum());
      for (int j = 0; j < obj.length; j++) {
        createRowCell(obj, j, row);
      }
    }
  }

  /**
   * 设置表格标题行
   */
  private static void setSheetTitle(HSSFSheet sheet, List<Object[]> dataList, String sheetTitle,
      HSSFCellStyle columnTopStyle) {

    HSSFRow row = sheet.createRow(mainConfig.getStartColNum());
    HSSFCell cellTitle = row.createCell(mainConfig.getStartColNum());
    sheet.addMergedRegion(new CellRangeAddress(mainConfig.getStartRowNum(),
        mainConfig.getTitleTotalNum() - 1,
        mainConfig.getStartColNum(), dataList.get(0).length - 1));

    cellTitle.setCellStyle(columnTopStyle);
    cellTitle.setCellValue(sheetTitle);
  }

  /**
   * 列头单元格样式
   */
  private static HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
    HSSFFont font = workbook.createFont();
    font.setFontHeightInPoints((short) 12);
    font.setBold(true);
    font.setFontName("Courier New");

    HSSFCellStyle style = workbook.createCellStyle();
    style.setBorderBottom(BorderStyle.MEDIUM);
    style.setBorderRight(BorderStyle.MEDIUM);
    style.setBorderTop(BorderStyle.MEDIUM);

    style.setFont(font);
    //设置自动换行;
    style.setWrapText(false);
    //设置水平对齐的样式为居中对齐;
    style.setAlignment(HorizontalAlignment.CENTER);
    //设置垂直对齐的样式为居中对齐;
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    return style;
  }

  /**
   * 列数据信息单元格样式
   */
  private static HSSFCellStyle getStyle(HSSFWorkbook workbook) {
    // 设置字体
    HSSFFont font = workbook.createFont();
    font.setFontHeightInPoints((short) 10);
    font.setFontName("Arial");

    HSSFCellStyle style = workbook.createCellStyle();
    // 设置边框风格和颜色
    style.setBorderBottom(BorderStyle.THIN);
    style.setBorderRight(BorderStyle.THIN);
    style.setBorderTop(BorderStyle.THIN);

    style.setFont(font);
    //设置自动换行;
    style.setWrapText(false);
    //设置水平对齐的样式为居中对齐;
    style.setAlignment(HorizontalAlignment.CENTER);
    //设置垂直对齐的样式为居中对齐;
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    return style;
  }
}
