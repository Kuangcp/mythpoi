package com.kuangcp.mythpoi.excel;

import com.kuangcp.mythpoi.excel.base.ExcelTransform;
import com.kuangcp.mythpoi.excel.base.MainConfig;
import com.kuangcp.mythpoi.utils.base.ReadAnnotationUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.util.List;

/**
 * Created by https://github.com/kuangcp on 18-2-21  下午12:47
 * Excel导出工具类
 *   TODO 异常的合理处理
 * @author kuangcp
 */
public class ExcelExport {
    private static MainConfig mainConfig = MainConfig.getInstance();
    private static HSSFWorkbook workbook = new HSSFWorkbook();
    /**
     * @param filePath 文件的绝对路径
     * @param originData 主要数据
     * @param target 实体的类对象
     */
    public static boolean exportExcel(String  filePath, List<? extends ExcelTransform> originData,
                                   Class<? extends ExcelTransform> target) {
        File f = new File(filePath); // 声明File对象
        OutputStream out = null;
        try {
            out = new FileOutputStream(f);

        } catch (FileNotFoundException e11) {
            e11.printStackTrace();
        }
        return exportExcel(out, originData, target);
    }
    /**
     * @param out 输出流
     * @param originData 原始对象集合
     * @param target Excel对应的类对象
     */
    public static boolean exportExcel(OutputStream out, List<? extends ExcelTransform> originData,
                                   Class<? extends ExcelTransform> target){
        try {
            String sheetTitle = ReadAnnotationUtil.getSheetTitle(target, true);
            List<String[]> dataList = ReadAnnotationUtil.getContentByList(target, originData);
            if(dataList == null){
                System.out.println(target.getSimpleName()+" 中没有已注解的字段, 导出失败");
                return false;
            }
            List<ExcelCellMeta> metaList = ReadAnnotationUtil.getCellMetaData(target);
            HSSFSheet sheet = workbook.createSheet(sheetTitle);
            // 设置表格标题行
            HSSFRow row = sheet.createRow(mainConfig.getStartColNum());
            HSSFCell cellTitle = row.createCell(mainConfig.getStartColNum());
            HSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);
            HSSFCellStyle style = getStyle(workbook);
            int columnNum = dataList.get(mainConfig.getStartRowNum()).length;
            sheet.addMergedRegion(new CellRangeAddress(mainConfig.getStartRowNum(), mainConfig.getTitleTotalNum()-1,
                    mainConfig.getStartColNum(), dataList.get(0).length - 1));
            cellTitle.setCellStyle(columnTopStyle);
            cellTitle.setCellValue(sheetTitle);
            // 开始设置正文
            row = sheet.createRow(mainConfig.getTitleTotalNum());
            // 设置sheet的列头
            for (int n = 0; n < columnNum; n++) {
                //创建列头对应个数的单元格
                HSSFCell cellRowName = row.createCell(n);
                //设置列头单元格的数据类型
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);
                HSSFRichTextString text = new HSSFRichTextString(metaList.get(n).getTitle());
                cellRowName.setCellValue(text);
                cellRowName.setCellStyle(columnTopStyle);
            }
            // 填充sheet内容
            for (int m = 0; m < dataList.size(); m++) {
                String[] obj = dataList.get(m);
                row = sheet.createRow(m + mainConfig.getContentStartNum());
                for (int j = 0; j < obj.length; j++) {
                    HSSFCell cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(obj[j]);
                    cell.setCellStyle(style);
                }
            }
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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
}
