# myth-poi
> More easy to Export & Import Excel&Word file.

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/5ffa0f4b455e4eba8fe66464792ccd7b)](https://www.codacy.com/app/Kuangcp/mythpoi?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Kuangcp/mythpoi&amp;utm_campaign=Badge_Grade)
[![codebeat badge](https://codebeat.co/badges/ab5fad57-0c61-49f6-a5ec-eb975b9d5c66)](https://codebeat.co/projects/github-com-kuangcp-mythpoi-master)
[![Java Version](https://img.shields.io/badge/Java-JRE%208-red.svg)](https://www.java.com/download/)

## How to install

- 1.Clone this Repo And install 
    - `git clone https://github.com/Kuangcp/mythpoi.git`
    - `cd mythpoi && gradle install`

- 2.Add Dependency  

_Maven_
```xml
<dependency>
    <groupId>com.github.kuangcp</groupId>
    <artifactId>myth-poi</artifactId>
    <version>0.1.1</version>
</dependency>
```

_Gradle_
```groovy
    compile("com.github.kuangcp:myth-poi:0.1.1")
```

## How to use
### Excel 
- 1.Implement Interface:
    - use annotation to define excelSheet column title and Sheet 
    - *Just support String field*
```java
@Data
@ExcelSheet(exportTitle = "雇员表", importTitle = "雇员表")
public class Employee implements ExcelTransform{
    @ExcelConfig("姓名")
    private String names;
    @ExcelConfig("住址")
    private String address;
    
    // not export this field
    @ExcelConfig(value = "QQ号码", exportFlag = false)
    private Strign qq;
}
```

**********
- 2.Export Excel file  
    - `ExcelExport.exportExcel("/home/kcp/test/employee.xls", originList);` `originList` is a Employee List   

- 3.Import Excel file
    - `List<Employee> result = ExcelImport.importExcel("/home/kcp/test/employee.xls", Employee.class);`
    
### Word
