package com.kuangcp.mythpoi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Employee{
//    @excelExportAnnotation(name = "姓名")
    private String name;//姓名
//    @excelExportAnnotation(name = "性别")
    private String sex;//性别:男，女
//    @excelExportAnnotation(name = "联系电话")
    private String phone;//联系电话
//    @excelExportAnnotation(name = "QQ号码")
    private String QQ;//QQ号码

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
//    @JsonIgnore
    public String[] getImportExcelDataField() {
        String[] temp = new String[exprotFields.size()];
        exprotFields.toArray(temp);
        return temp;
    }

    //把map对象转换成pojo对象
//    public BasicModel createModel(Map<String, String> fieldValues) {
//
//        return new Employee();
//    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ == null ? null : QQ.trim();
    }

    @Override
    public String toString() {
        return "Employee [name=" + name + ", sex=" + sex + ", phone=" + phone + ", QQ=" + QQ + "]";
    }


}