package com.kuangcp.mythpoi.utils.base;

import com.kuangcp.mythpoi.utils.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by https://github.com/kuangcp on 18-2-22  下午4:20
 *
 * @author kuangcp
 */
@Slf4j
public class ReadAnnotationUtilTest {

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
    e2.setBirth(new Date());
    List<Employee> originList = new ArrayList<>(0);
    originList.add(e1);
    originList.add(e2);
    List<Object[]> result = ReadAnnotationUtil.getContentByList(Employee.class, originList);
    for (Object[] temp : result) {
      for (Object n : temp) {
        System.out.print(n);
      }
      System.out.println();
    }
  }
}