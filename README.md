# mythpoi
> Easy to Export & Import Excel file.

## How to use
- 1.Clone this Repo 
    - `git clone https://github.com/Kuangcp/mythpoi.git`
    - `gradle install`

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

- 3.Implement Interface:
    - must have EXPORT_TITLE & IMPORT_TITLE field
    - use annotation to define excelSheet column title 
    - *Just support String field*
```java
@Data
public class Employee implements ExcelTransform{
    public static String EXPORT_TITLE="雇员表";
    public static String IMPORT_TITLE="雇员表";

    @ExcelConfig("姓名")
    private String names;
}
```

**********
- 4.Export Excel file  
    - `ExcelExport.exportExcel("/home/kcp/test/employee.xls", Employee.EXPORT_TITLE, originList, Employee.class);`

- 5.Import Excel file
    - `List<? super ExcelTransform> result = ExcelImport.importExcel("/home/kcp/test/employee.xls", Employee.class);`