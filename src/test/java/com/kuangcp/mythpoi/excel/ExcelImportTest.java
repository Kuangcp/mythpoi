package com.kuangcp.mythpoi.excel;

import com.kuangcp.mythpoi.utils.Employee;
import org.junit.Test;

/**
 * Created by https://github.com/kuangcp on 18-2-23  下午9:54
 *
 * @author kuangcp
 */
public class ExcelImportTest {

    @Test
    public void testImportExcel(){
        ExcelImport excelImport = new ExcelImport();
        excelImport.importExcel("/home/kcp/test/employee.xls", Employee.class);
    }
}

