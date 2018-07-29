package com.kuangcp.mythpoi.utils;

import com.kuangcp.mythpoi.excel.base.ExcelTransform;
import com.kuangcp.mythpoi.utils.base.ExcelConfig;
import com.kuangcp.mythpoi.utils.base.ExcelSheet;
import lombok.Data;

import java.util.Date;

@Data
@ExcelSheet(exportTitle = "雇员表", importTitle = "雇员表")
public class Employee implements ExcelTransform {

  @ExcelConfig("姓名")
  private String names;
  @ExcelConfig("性别")
  private String sex;

  @ExcelConfig("年龄")
  private int age;

  @ExcelConfig("生日")
  private Date birth;
  @ExcelConfig("活跃")
  private Boolean death;
  @ExcelConfig("分数")
  private double score;


  @ExcelConfig("联系电话")
  private String phone;
  @ExcelConfig("QQ号码")
  private String QQ;

  @ExcelConfig("邮箱")
  private String email = "";

}