package com.kuangcp.mythpoi.excel;

import com.kuangcp.mythpoi.excel.base.ExcelTransform;
import com.kuangcp.mythpoi.utils.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by https://github.com/kuangcp on 18-2-23  下午9:54
 *
 * @author kuangcp
 */
public class ExcelImportTest {

    @Test
    public void testImportExcel(){
//        ExcelImport excelImport = new ExcelImport<Employee>();
        // TODO 接收对应类型
        List<Employee> result = ExcelImport.importExcel("/home/kcp/test/employee.xls", Employee.class);
        result.forEach(item -> {
//            System.out.println(item.toString());
            System.out.println(item.getEmail());
            System.out.println(item.getNames());
            System.out.println(item.getQQ());

        });

        Map<String, String> list = new HashMap<String, String>(0){};
    }
}

