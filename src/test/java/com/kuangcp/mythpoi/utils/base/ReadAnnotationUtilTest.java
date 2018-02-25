package com.kuangcp.mythpoi.utils.base;

import com.kuangcp.mythpoi.utils.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by https://github.com/kuangcp on 18-2-22  下午4:20
 *
 * @author kuangcp
 */
public class ReadAnnotationUtilTest {

//    @Test
//    public void testGetFieldTitleMap() {
//        Map<String, String> result = ReadAnnotationUtil.getFieldTitleMap(Employee.class);
//        for(Map.Entry<String, String> entry:result.entrySet()){
//            System.out.println(entry.getKey() + " | " + entry.getValue());
//        }
//    }

//    @Test
//    public void testgetFieldAnnotationMap(){
//        Map<String, String> result = ReadAnnotationUtil.getFieldAnnotationMap(Employee.class, ExcelConfig.class, "name");
//        for(Map.Entry<String, String> entry:result.entrySet()){
//            System.out.println(entry.getKey() + " | " + entry.getValue());
//        }
//    }
    @Test
    public void testTransform() throws Exception {
        Employee e1 = new Employee();
        e1.setQQ("QQ1");
        e1.setNames("Name1");
        e1.setPhone("Phone1");
//        e1.setSex("sex1");
        e1.setEmail("email");

        Employee e2 = new Employee();
//        e2.setName("name2");
        e2.setPhone("phone2");
        e2.setSex("sex2");
        e2.setQQ("QQ2");
        List<Employee> originList = new ArrayList<>(0);
        originList.add(e1);
        originList.add(e2);
        List<String[]> result = ReadAnnotationUtil.getContentByList(Employee.class, originList);
        for(String [] temp : result){
//            System.out.println(temp.toString());
            for(String n : temp){
                System.out.print(n);
            }
            System.out.println();
        }




    }
}