package com.kuangcp.mythpoi.utils;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by https://github.com/kuangcp on 18-2-21  下午10:10
 *
 * @author kuangcp
 */
public class ExcelExportTest {

    @Test
    public void testExports(){
        File f= new File("/home/kcp/test/employee.xls") ; // 声明File对象
        // 第2步、通过子类实例化父类对象
        OutputStream out = null ; // 准备好一个输出的对象
        try {
            out = new FileOutputStream(f)  ;
        } catch (FileNotFoundException e11) {
            e11.printStackTrace();
        }

        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"2", "2"});

        try {
            ExcelExport.export(out,"23232", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ok");
    }
    @Test
    public void testAddRows() throws Exception {
        int result = ExcelExport.addRows(null, null, 0, null);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testExport() throws Exception {
        ExcelExport.export(null, "title", null);
    }

    @Test
    public void testCreateSheet() throws Exception {
        HSSFSheet result = ExcelExport.createSheet(null, "sheetTitle", "tableTitle", new String[]{"columnTitle"}, Integer.valueOf(0));
        Assert.assertEquals(null, result);
    }
}