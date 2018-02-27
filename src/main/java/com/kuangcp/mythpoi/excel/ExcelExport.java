package com.kuangcp.mythpoi.excel;

import com.kuangcp.mythpoi.excel.base.ExcelTransform;
import com.kuangcp.mythpoi.excel.base.MainConfig;
import com.kuangcp.mythpoi.utils.base.ReadAnnotationUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by https://github.com/kuangcp on 18-2-21  下午12:47
 * Excel导出工具类
 * @author kuangcp
 */
public class ExcelExport {
    private static MainConfig mainConfig = MainConfig.getInstance();
    /**
     * @param out 输出流
     * @param sheetTitle Sheet标题
     * @param originData 原始对象集合
     * @param target Excel对应的类对象
     */
    public static void exportExcel(OutputStream out, String sheetTitle, List<? extends ExcelTransform> originData,
                              Class target) throws Exception {
        List<String[]> dataList = ReadAnnotationUtil.getContentByList(target, originData);
        // 得到元数据
        List<ExcelCellMeta> metaList = ReadAnnotationUtil.getCellMetaData(target);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetTitle);

        // 产生表格标题行
        HSSFRow row = sheet.createRow(mainConfig.getStartColNum());
        HSSFCell cellTitle = row.createCell(mainConfig.getStartColNum());

        //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
        HSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);
        HSSFCellStyle style = getStyle(workbook);
        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(mainConfig.getStartRowNum(), mainConfig.getTitleTotalNum()-1,
                mainConfig.getStartColNum(), dataList.get(0).length - 1));
        cellTitle.setCellStyle(columnTopStyle);
        cellTitle.setCellValue(sheetTitle);

        // 定义所需列数
        int columnNum = dataList.get(mainConfig.getStartRowNum()).length;
        // 在索引2的位置创建行(最顶端的行开始的第二行)
        HSSFRow rowRowName = sheet.createRow(mainConfig.getTitleTotalNum());

        // 设置sheet的列头
        for (int n = 0; n < columnNum; n++) {
            //创建列头对应个数的单元格
            HSSFCell cellRowName = rowRowName.createCell(n);
            //设置列头单元格的数据类型
            cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);
            HSSFRichTextString text = new HSSFRichTextString(metaList.get(n).getTitle());
            cellRowName.setCellValue(text);
            cellRowName.setCellStyle(columnTopStyle);
        }

        // 填充sheet内容
        for (int m = 0; m < dataList.size(); m++) {
            String[] obj = dataList.get(m);
            //创建所需的行数
            row = sheet.createRow(m + mainConfig.getContentStartNum());
            for (int j = 0; j < obj.length; j++) {
                HSSFCell cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(obj[j]);
                cell.setCellStyle(style);
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
     */
    private static HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("Courier New");

        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        style.setTopBorderColor(HSSFColor.BLACK.index);

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
     */
    private static HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short)10);
        font.setFontName("Arial");

        HSSFCellStyle style = workbook.createCellStyle();
        // 设置边框风格和颜色
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setTopBorderColor(HSSFColor.BLACK.index);

        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }
    //    /**
//     * 在指定的sheet中在指定位置追加一些行
//     * @param workbook 工作簿
//     * @param sheet sheet
//     * @param rowIndex 起始行标?
//     * @param dataList List<String[]> 二维数据
//     * @return int 添加的行数
//     */
//    public static int addRows(HSSFWorkbook workbook, HSSFSheet sheet, int rowIndex, List<String[]> dataList) {
//        HSSFCellStyle style = getStyle(workbook);
////        int columnNum = dataList.get(0).length;
//        //将查询出的数据设置到sheet对应的单元格中
//        int i = 0;
//        for (; i < dataList.size(); i++) {
//            String[] obj = dataList.get(i);//遍历每个对象
//            HSSFRow row = sheet.createRow(rowIndex + i);//创建所需的行数
//            for (int j = 0; j < obj.length; j++) {
//                HSSFCell cell;   //设置单元格的数据类型
//                // 一行中的列数为0
//                if (j == 0) {
//                    cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
//                    // 这个代码什么意思, 减3?, 减去Sheet标题,列标题一共三行,需要脱离出来
//                    cell.setCellValue(rowIndex + i - 3);
//                } else {
//                    cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
//                    cell.setCellValue(obj[j]);//设置单元格的值
//                }
//                cell.setCellStyle(style);//设置单元格样式
//            }
//        }
//        rowIndex = rowIndex + i;
//        return rowIndex;
//    }
}
