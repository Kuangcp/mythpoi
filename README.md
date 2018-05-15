# myth-poi
> More easy to Export & Import Excel&Word file.

[![Maintainability](https://api.codeclimate.com/v1/badges/f780223b0a257f652c4c/maintainability)](https://codeclimate.com/github/Kuangcp/mythpoi/maintainability)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/5ffa0f4b455e4eba8fe66464792ccd7b)](https://www.codacy.com/app/Kuangcp/mythpoi?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Kuangcp/mythpoi&amp;utm_campaign=Badge_Grade)
[![codebeat badge](https://codebeat.co/badges/ab5fad57-0c61-49f6-a5ec-eb975b9d5c66)](https://codebeat.co/projects/github-com-kuangcp-mythpoi-master)
[![Java Version](https://img.shields.io/badge/Java-JRE%208-red.svg)](https://www.java.com/download/)

## 1. How to install
### A way: Build
- Clone this Repo And install 
    - `git clone https://github.com/Kuangcp/mythpoi.git`
    - `cd mythpoi && gradle install`

### Another way: Use gitee repository
#### Gradle 
```groovy
repositories {
    maven{
        url "https://gitee.com/kcp1104/MavenRepos/raw/master"
    }
} 
```
#### Maven 

************
## 2. Add Dependency

_2.1 Maven_
```xml
    <dependency>
        <groupId>com.github.kuangcp</groupId>
        <artifactId>myth-poi</artifactId>
        <version>0.2.4-SNAPSHOT</version>
    </dependency>
```
_2.2 Gradle_
```groovy
    compile("com.github.kuangcp:myth-poi:0.2.4-SNAPSHOT")
```

_2.3 Jar_
Download About Jar

http://mvnrepository.com/artifact/org.jyaml/jyaml  
http://mvnrepository.com/artifact/org.apache.poi/poi  
https://gitee.com/kcp1104/MavenRepos/tree/master/com/github/kuangcp/myth-poi  

********************
## 3. How to use
### Excel 
- 1.Implement Interface:
    - use annotation to define excelSheet column title and Sheet 
    - *Just support BaseType field*

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
    private String qq;
}
```
- 2.Export Excel file  
```java
List<Employee> originList = new ArrayList<>();
// add some Employee Object ...
ExcelExport.exportExcel("/home/kcp/test/employee.xls", originList);
```

- 3.Import Excel file
```java
List<Employee> result = ExcelImport.importExcel("/home/kcp/test/employee.xls", Employee.class);
```

_Excel File :_

![excel.png](https://raw.githubusercontent.com/Kuangcp/ImageRepos/master/Image/mythpoi/excel.png)

### Word
