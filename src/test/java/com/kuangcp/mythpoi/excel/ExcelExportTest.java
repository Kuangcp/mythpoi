package com.kuangcp.mythpoi.excel;

import com.kuangcp.mythpoi.utils.Employee;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by https://github.com/kuangcp on 18-2-21  下午10:10
 *
 * @author kuangcp
 */
public class ExcelExportTest {

    @Test
    public void testExports() throws Exception {
        Employee e1 = new Employee();
        e1.setQQ("QQ1");
        e1.setNames("Name1");
        e1.setPhone("Phone1");
        e1.setSex("sex1");
        e1.setEmail("email");

        Employee e2 = new Employee();
        e2.setNames("name2");
        e2.setPhone("phone2");
        e2.setSex("sex2");
        e2.setQQ("QQ2");

        Employee e3 = new Employee();
        e3.setNames("name2");
        e3.setPhone("phone2");
        e3.setSex("sex2");
        e3.setQQ("QQ2");

        List<Employee> originList = new ArrayList<>(0);
        originList.add(e1);
        originList.add(e2);
        originList.add(e3);

//        List<String[]> result = ReadAnnotationUtil.getContentByList(Employee.class, originList);
//        for(String [] temp: result){
//            for(String l : temp){
//                System.out.println(l);
//            }
//        }
        Boolean results = ExcelExport.exportExcel("/home/kcp/test/employee.xls", originList, Employee.class);
        assert results;
    }

}