package com.kuangcp.mythpoi.utils.base;

import com.kuangcp.mythpoi.utils.Employee;
import java.lang.reflect.Field;
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
    List<Employee> originList = new ArrayList<>();
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

  /**
   * 测试读取注解信息
   * 注意: 注解在什么对象上就拿到该对应对象调用 isAnnotationPresent 方法即可判断是否有对应注解存在
   */
  @Test
  public void readConfig() throws ClassNotFoundException {
    final Class cls = Class.forName("com.kuangcp.mythpoi.utils.Employee");

//        final Method[] methodList = cls.getMethods();
//        for(Method method : methodList){
//            System.out.println(method.toString());
//        }
    final Field[] fields = cls.getDeclaredFields();
    for (Field field : fields) {
      if (field.isAnnotationPresent(ExcelConfig.class)) {
        System.out.print("具有注解  ");
        ExcelConfig excelConfig = field.getAnnotation(ExcelConfig.class);
        System.out.print(excelConfig.value());
      }
      System.out.println(field.getName());
    }

    // 判断是否有指定注解类型的注解
//        if (cls.isAnnotationPresent(MainConfig.class)) {
//            // 根据注解类型返回指定类型的注解
//            MainConfig des = (MainConfig) cls.getAnnotation(MainConfig.class);
//            System.out.println("注解描述:" + des.name());
//        }else{
//            System.out.println("没有?");
//        }
  }
}