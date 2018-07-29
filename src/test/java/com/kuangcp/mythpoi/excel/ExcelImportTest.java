package com.kuangcp.mythpoi.excel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuangcp.mythpoi.utils.Employee;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by https://github.com/kuangcp on 18-2-23  下午9:54
 *
 * @author kuangcp
 *
 */
@Ignore
@Slf4j
public class ExcelImportTest {

  @Test
  public void testImportExcel() {
    List<Employee> result = ExcelImport.importExcel("/home/kcp/test/employee.xls", Employee.class);
    result.forEach(item -> System.out.println(item.toString()));

    ObjectMapper mapper = new ObjectMapper();
    try {
      String json = mapper.writeValueAsString(result);
      log.debug("result : json={}", json);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

  }

  @Test
  public void testStreamImport() throws FileNotFoundException {
    FileInputStream inputStream = new FileInputStream("/home/kcp/test/employee.xls");
    List<Employee> result = ExcelImport.importExcel(inputStream, Employee.class);
    result.forEach(item -> {
      System.out.println(item.toString());
    });
  }


}

