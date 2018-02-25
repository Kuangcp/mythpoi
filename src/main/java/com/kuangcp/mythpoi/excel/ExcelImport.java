package com.kuangcp.mythpoi.excel;

import com.kuangcp.mythpoi.excel.base.ExcelTransform;
import com.kuangcp.mythpoi.utils.base.ReadAnnotationUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by https://github.com/kuangcp on 18-2-23  下午9:52
 * Excel导入的操作
 *     通过Excel中Sheet的名字判断对应实体
 *     通过列的中文名得到实体的属性对象,
 *     通过属性对象进行设置值,将Excel的每一行转换成对象
 * 问题:
 * 一个Sheet对应一个类, 怎么处理多Sheet, 用Map?
 *
 * @author kuangcp
 */
public class ExcelImport {
    private HSSFWorkbook wb;

    /**
     * 根据Excel文件 将Excel转换成对象集合, 只读第一个Sheet
     * @param filePath Excel文件绝对路径
     * @param target 对象集合
     * @return List集合, 否则返回Null
     */
    public List<? extends ExcelTransform> importExcel(String filePath, Class target) {
        return importExcel(filePath, target, 0);
    }

    /**
     * 根据Excel文件输入流 将Excel转换成对象集合,只读第一个Sheet
     * @param input FileInputStream 输入流
     * @param target 实体类
     * @return List集合, 或者Null
     */
    public List<? extends ExcelTransform> importExcel(FileInputStream input, Class target) {
        return importExcel(input, target, 0);
    }

    /**
     * 根据Excel文件 将Excel转换成对象集合
     * @param filePath Excel文件绝对路径
     * @param target 对象集合
     * @param sheetNum Sheet标号 0开始
     * @return List集合, 否则返回Null
     */
    public List<? extends ExcelTransform> importExcel(String filePath, Class target, int sheetNum) {
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(filePath);
            return importExcel(inputStream, target, sheetNum);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据Excel文件输入流 将Excel转换成对象集合
     * @param input FileInputStream 输入流
     * @param target 实体类
     * @param sheetNum Sheet标号 0开始
     * @return List集合, 或者Null
     */
    public List<? extends ExcelTransform> importExcel(FileInputStream input, Class target, int sheetNum) {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(input);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readExcelSheet(sheetNum, target);
    }

    private static void readTitle(Sheet sheet){

    }
    /**
     * 根据sheetNum读取数据
     * @param sheetNum sheet标号 0开始
     * @return 数据集合对象
     */
    private List readExcelSheet(int sheetNum, Class<? extends ExcelTransform> target) {
        List result = new ArrayList<>(0);
        List<ExcelCellMeta> metaList = ReadAnnotationUtil.getCellMetaData(target);
        HSSFSheet sheet = wb.getSheetAt(sheetNum);
        int rowNum = sheet.getLastRowNum();
        // 0 是标题 1 NPE? 2标题行
        HSSFRow row = sheet.getRow(2);
        int colNum = row.getPhysicalNumberOfCells();
        System.out.println("得到行"+rowNum+"列"+colNum);
        String[] titleList = new String[colNum];

        for(int i=0; i<colNum;i++){
            String temp = row.getCell(i).getStringCellValue();
            for(ExcelCellMeta meta : metaList){
                if(meta.getTitle().equals(temp)){
                    titleList[i] = temp;
//                    System.out.println(temp+"|"+meta.getField());
                }
            }
        }

        for (int j = 3; j <= rowNum; j++) {
            row = sheet.getRow(j);
            ExcelTransform obj = null;
            try {
                obj = target.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < colNum; i++) {
                String temp = row.getCell(i).getStringCellValue();
                for(ExcelCellMeta meta : metaList){
                    if(meta.getTitle().equals(titleList[i])){
                        meta.getField().setAccessible(true);
                        try {
                            meta.getField().set(obj, temp);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            result.add(obj);
        }

//        for (String s : titleList) {
//            System.out.println(s);
//        }
        result.forEach(item -> System.out.println(item.toString()));
        return null;
    }
}
