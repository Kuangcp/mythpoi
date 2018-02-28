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
        // TODO 接收就需要extends关键字???, 这里是因为方法重载过多,没有修改对应的导致的误判
        List<? super ExcelTransform> result = ExcelImport.importExcel("/home/kcp/test/employee.xls", Employee.class);
        result.forEach(item -> {
            System.out.println(item.toString());
//            Employee e = (Employee)item;
//            System.out.print(e.getNames());
//            System.out.print(e.getPhone());
//            System.out.print(e.getQQ());
//            System.out.println("<<<<<");
        });

        Map<String, String> list = new HashMap<String, String>(0){};
    }
}

