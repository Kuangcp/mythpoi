package com.kuangcp.mythpoi.utils;

import com.kuangcp.mythpoi.excel.base.ExcelTransform;
import com.kuangcp.mythpoi.utils.base.ExcelConfig;
import lombok.Data;

@Data
public class Employee implements ExcelTransform{

    public static String EXPORT_TITLE="雇员表";
    public static String IMPORT_TITLE="雇员表";

    @ExcelConfig(name = "姓名")
    private String names;
    @ExcelConfig(name = "性别")
    private String sex;
    @ExcelConfig(name = "联系电话")
    private String phone;
    @ExcelConfig(name = "QQ号码")
    private String QQ;

    private String email;

}