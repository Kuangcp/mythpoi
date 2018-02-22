package com.kuangcp.mythpoi.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by https://github.com/kuangcp on 18-2-21  下午12:47
 * Excel导出类
 *
 * @author kuangcp
 */
public class ExcelExport {
    //title显示的导出表的标题
    //rowName导出表的列名
    // dataList 导出的数据

    /**
     * 在指定的sheet中在指定位置追加一些行
     * @param workbook 工作簿
     * @param sheet sheet
     * @param rowIndex 起始行标?
     * @param dataList List<String[]> 二维数据
     * @return int 添加的行数
     */
    public static int addRows(HSSFWorkbook workbook, HSSFSheet sheet, int rowIndex, List<String[]> dataList) {
        HSSFCellStyle style = getStyle(workbook);
//        int columnNum = dataList.get(0).length;
        //将查询出的数据设置到sheet对应的单元格中
        int i = 0;
        for (; i < dataList.size(); i++) {
            String[] obj = dataList.get(i);//遍历每个对象
            HSSFRow row = sheet.createRow(rowIndex + i);//创建所需的行数
            for (int j = 0; j < obj.length; j++) {
                HSSFCell cell;   //设置单元格的数据类型
                // 一行中的列数为0
                if (j == 0) {
                    cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
                    // 这个代码什么意思, 减3?, 减去Sheet标题,列标题一共三行,需要脱离出来
                    cell.setCellValue(rowIndex + i - 3);
                    // System.out.println("增加"+(rowIndex+i));
                } else {
                    cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                    if (!"".equals(obj[j]) && obj[j] != null) {
                        cell.setCellValue(obj[j]);//设置单元格的值
                    } else {
                        cell.setCellValue("");
                    }
                }
                cell.setCellStyle(style);                                   //设置单元格样式
            }
        }
        rowIndex = rowIndex + i;
        return rowIndex;
    }

    /**
     *
     * @param out 输出流
     * @param sheetTitle 标题
     * @param dataList 数据集合 第一行是列标题
     * @throws Exception
     */
    public static void export(OutputStream out, String sheetTitle, List<String[]> dataList){
        HSSFWorkbook workbook = new HSSFWorkbook();// 创建工作簿对象
        HSSFSheet sheet = workbook.createSheet(sheetTitle);// 创建工作表

        // 产生表格标题行
        HSSFRow rowm = sheet.createRow(0);
        HSSFCell cellTiltle = rowm.createCell(0);

        //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
        HSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);//获取列头样式对象
        HSSFCellStyle style = getStyle(workbook);//单元格样式对象

        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (dataList.get(0).length - 1)));
        cellTiltle.setCellStyle(columnTopStyle);
        cellTiltle.setCellValue(sheetTitle);

        // 定义所需列数
        int columnNum = dataList.get(0).length;
        HSSFRow rowRowName = sheet.createRow(2);                // 在索引2的位置创建行(最顶端的行开始的第二行)

        // 将列头设置到sheet的单元格中
        for (int n = 0; n < columnNum; n++) {
            HSSFCell cellRowName = rowRowName.createCell(n);               //创建列头对应个数的单元格
            cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);             //设置列头单元格的数据类型
            HSSFRichTextString text = new HSSFRichTextString(dataList.get(0)[n]);
            cellRowName.setCellValue(text);                                 //设置列头单元格的值
            cellRowName.setCellStyle(columnTopStyle);                       //设置列头单元格样式
        }

        //将查询出的数据设置到sheet对应的单元格中
        for (int i = 0, m = 1; m < dataList.size(); i++, m++) {

            String[] obj = dataList.get(m);//遍历每个对象
            HSSFRow row = sheet.createRow(i + 3);//创建所需的行数

            for (int j = 0; j < obj.length; j++) {
                HSSFCell cell;   //设置单元格的数据类型
                if (j == 0) {
                    cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(i + 1);
                } else {
                    cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                    if (!"".equals(obj[j]) && obj[j] != null) {
                        cell.setCellValue(obj[j]);                       //设置单元格的值
                    } else {
                        cell.setCellValue("");
                    }
                }
                cell.setCellStyle(style);                                   //设置单元格样式
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 列头单元格样式
     * @param workbook 工作簿
     * @return
     */
    private static HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 11);
        //字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }

    /**
     * 列数据信息单元格样式
     * @param workbook
     * @return
     */
    private static HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        //font.setFontHeightInPoints((short)10);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }

    /**
     * 创建sheet
     * @param workbook 工作簿
     * @param sheetTitle 表标题
     * @param tableTitle sheet标题
     * @param columnTitle 列标题
     * @param year 年
     * @return
     * @throws Exception
     */
    public static HSSFSheet createSheet(HSSFWorkbook workbook, String sheetTitle, String tableTitle, String[] columnTitle, Integer year) throws Exception {
        // 创建工作簿对象
        HSSFSheet sheet = workbook.createSheet(sheetTitle);                  // 创建工作表

        // 产生表格标题行
        HSSFRow rowm = sheet.createRow(0);
        HSSFCell cellTiltle = rowm.createCell(0);

        //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
        HSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);//获取列头样式对象
        HSSFCellStyle style = getStyle(workbook);                  //单元格样式对象

        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (columnTitle.length - 1)));
        cellTiltle.setCellStyle(columnTopStyle);
        cellTiltle.setCellValue(tableTitle);  //单元表格的标题

        // 定义所需列数
        int columnNum = columnTitle.length;
        HSSFRow rowRowName = sheet.createRow(2);
        for (int n = 0; n < columnNum; n++) {
            HSSFCell cellRowName = rowRowName.createCell(n);               //创建列头对应个数的单元格
            cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); //设置列头单元格的数据类型
            HSSFRichTextString text = null;
            if (n == 0) {
                text = new HSSFRichTextString("登记部门:外语学院 ");
                cellRowName.setCellValue(text);                                 //设置列头单元格的值
                //cellRowName.setCellStyle(columnTopStyle);
            } else if (n == columnNum - 2) {
                text = new HSSFRichTextString("年度： " + year);
                cellRowName.setCellValue(text);                                 //设置列头单元格的值
                //cellRowName.setCellStyle(columnTopStyle);
            } else {

            }
            //设置列头单元格样式
        }
        rowRowName = sheet.createRow(3);// 在索引2的位置创建行(最顶端的行开始的第二行)

        // 将列头设置到sheet的单元格中
        for (int n = 0; n < columnNum; n++) {
            HSSFCell cellRowName = rowRowName.createCell(n);               //创建列头对应个数的单元格
            cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);             //设置列头单元格的数据类型
            HSSFRichTextString text = new HSSFRichTextString(columnTitle[n]);
            cellRowName.setCellValue(text);                                 //设置列头单元格的值
            cellRowName.setCellStyle(columnTopStyle);                       //设置列头单元格样式
        }
        return sheet;
    }

}
