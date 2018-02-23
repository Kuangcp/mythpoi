package com.kuangcp.mythpoi.utils;

import com.kuangcp.mythpoi.excel.base.ExcelTransform;
import com.kuangcp.mythpoi.utils.base.ExcelConfig;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Employee implements ExcelTransform{
    @ExcelConfig(name = "姓名")
    private String name;
    @ExcelConfig(name = "性别")
    private String sex;
    @ExcelConfig(name = "联系电话")
    private String phone;
    @ExcelConfig(name = "QQ号码")
    private String QQ;

    private String email;

    //导出的Excel的表的标题及文件名
    public String exportExcelTitle() {
        return "职工信息清单";
    }

    //导入的Excel的表的标题及文件名
    public String importExcelTitle() {
        return "职工信息清单";
    }

    private static List<String> exprotFields = new ArrayList<String>(0);

    static {
        exprotFields.add("name");
        exprotFields.add("sex");
        exprotFields.add("phone");
        exprotFields.add("QQ");
    }

    //返回excel导入的字段名
    public String[] getImportExcelDataField() {
        String[] temp = new String[exprotFields.size()];
        exprotFields.toArray(temp);
        return temp;
    }
    @Override
    public String toString() {
        return "Employee [name=" + name + ", sex=" + sex + ", phone=" + phone + ", QQ=" + QQ + "]";
    }


    @Override
    public String getExcelTitle() {
        return "雇员表";
    }
}