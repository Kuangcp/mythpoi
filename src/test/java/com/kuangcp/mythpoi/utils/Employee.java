package com.kuangcp.mythpoi.utils;

import com.kuangcp.mythpoi.excel.base.ExcelTransform;
import com.kuangcp.mythpoi.utils.base.ExcelConfig;
import com.kuangcp.mythpoi.utils.base.ExcelSheet;
import lombok.Data;

@Data
@ExcelSheet(exportTitle = "雇员表", importTitle = "雇员表")
public class Employee implements ExcelTransform{

    @ExcelConfig("姓名")
    private String names;
    @ExcelConfig("性别")
    private String sex;
//    @ExcelConfig("年龄")
//    private int age;
    @ExcelConfig("联系电话")
    private String phone;
    @ExcelConfig("QQ号码")
    private String QQ;

    private String email;

}