//package com.kuangcp.mythpoi.excel.db;
//
///**
// * Created by https://github.com/kuangcp on 18-2-25  下午1:53
// *
// * @author kuangcp
// */
//import lombok.extern.log4j.Log4j;
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.omg.CORBA.ExceptionList;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.DecimalFormat;
//import java.text.NumberFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//
///**
// * Created by https://github.com/kuangcp on 17-10-3  下午4:59
// * 负责阅读上传的Excel文档，进行数据库的添加操作
// * TODO 思路要重构，一堆的隐藏bug
// */
//@Log4j
//public class ReadExcel {
//    private HSSFWorkbook wb;
//    private int AllRows;//内容的行数
//    private int AllCols;//内容的列数
//    private ExcelData contents; // excel数据的对象
//
//
//    /**
//     * 根据路径来读取文件
//     * @param path 文件路径
//     * @throws Exception 文件异常或者输入流异常
//     * @return 数据集合对象
//     */
//    public ExcelData readFile(String path) throws Exception{
//        FileInputStream inputStream;
//        try {
//            inputStream = new FileInputStream(path);
//        } catch (FileNotFoundException e) {
//            throw new LoadFileException(ExceptionList.LOAD_FILE_EXCEPTION, e, ReadExcel.class);
//        }
//        return readFileInputStream(inputStream);
//    }
//
//    /**
//     * 由输入流直接读取文件,得到封装成对象的数据
//     * @param input 文件输入流
//     * @throws Exception 文件异常或者其他
//     * @return 数据集合对象
//     */
//    public ExcelData readFileInputStream(FileInputStream input) throws Exception {
//        try {
//            POIFSFileSystem fs = new POIFSFileSystem(input);
//            wb = new HSSFWorkbook(fs);
//        }catch (IOException e) {
//            throw new Exception(ExceptionList.IO_READ_EXCEL_EXCEPTION, e, ReadExcel.class);
//        }
//        int currentSheet = 0;
//        contents = readExcelContent(currentSheet);
//        try {
//            input.close();
//        } catch (IOException e) {
//            throw new Exception(ExceptionList.CLOSE_EXCEPTION,e, ReadExcel.class);
//        }
//        return contents;
//    }
//
//    /**
//     * 读取指定Sheet的表格数据内容 注意 del删除的行，默认是还在的，要右击删除才不在
//     * @param sheetNum sheet标号 1开始
//     * @return Map 普通Excel表格的数据
//     */
//    private ExcelData readExcelContent(int sheetNum) throws Exception {
//        Map<Position,String> content = new HashMap<>();
//        HSSFSheet sheet = wb.getSheetAt(sheetNum);
//        //得到总行数 下标从0开始，自己定义的就是从1开始 TODO 隐藏的可能混淆
//        int rowNum = sheet.getLastRowNum();
//
//        AllRows = rowNum+1;
////        log.info("正文得到的行数 不真实："+rowNum);// 默认去除了第一行的内容 TODO 隐藏bug
//        HSSFRow row = sheet.getRow(0);// 得到第一行的行对象，方便得到之后的实际列数，TODO 还是有隐藏bug
//        int colNum = row.getPhysicalNumberOfCells();
//        AllCols = colNum;
//        log.info("正文得到的列数："+colNum);
//        int realRow = 1;
//        for (int i = 1; i <= rowNum; i++) {  //遍历所有行，除了第一行  数组规则
//            realRow ++;
//            row = sheet.getRow(i);
//            if(row == null){
//                throw new Exception(ExceptionList.READ_EXCEL_ROW_NPE, ReadExcel.class);
//            }
//            for(int j=0; j<colNum;j++) {
//                if (row.getCell(j) != null){
//                    String cellValue = getStringCellValue(row.getCell(j)).trim();
////                    if ("".equals(cellValue)){
////                        realRow--;
////                        break;// 一行中存在空格就不存这一行 直接break
////                    }
//                    content.put(new Position(i, j+1), cellValue); //这里创建的对象，地址是已经固定了，也不可得到,重写HashCode方法即可让值和地址绑定
//                }
//            }
//        }
//        AllRows = realRow; // 去除空行，将实际行数放进去
//        log.info("最终得到的Excel有效数据 行数: "+realRow+" 单元格数："+content.size());
//        return new ExcelData(AllRows, AllCols, content);
//    }
//    /**
//     * 获取单元格数据内容为字符串，数值，时间类型的数据 并返回String
//     * @param cell Excel单元格
//     * @return String 单元格数据内容
//     */
//    private String getStringCellValue(HSSFCell cell) {
//        String strCell;
//        switch (cell.getCellType()) {
//            case Cell.CELL_TYPE_FORMULA:
//                try{
//                    /*
//                     * 此处判断使用公式生成的字符串有问题，因为HSSFDateUtil.isCellDateFormatted(cell)判断过程中cell.getNumericCellValue();方法
//                     * 会抛出java.lang.NumberFormatException异常
//                     */
//                    if (HSSFDateUtil.isCellDateFormatted(cell)) {//判断是日期类型
//                        Date date = cell.getDateCellValue();//从单元格获取日期数据
//                        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");//设定转换的格式
//                        strCell = formater.format(date);//将日期数据（Date 或者直接输入的格式正确的字符串）转换成String类型
//                        break;
//                    }else{
//                        double Num = cell.getNumericCellValue();   //普通数字类型
//                        DecimalFormat formatCell = (DecimalFormat) NumberFormat.getPercentInstance();
//                        formatCell.applyPattern("0");
//                        strCell = formatCell.format(Num);
//                        if(Double.parseDouble(strCell)!=Num){
//                            formatCell.applyPattern(Double.toString(Num));
//                            strCell = formatCell.format(Num);
//                        }
//
//                        if(strCell.length()>2&&".0".equals(strCell.subSequence(strCell.length()-2, strCell.length()))){
//                            strCell = Integer.parseInt(strCell.substring(0,strCell.length()-2))+"";
//                        }
//                        System.out.println("数字："+strCell);
//                    }
//                }catch(IllegalStateException e){
//                    strCell = String.valueOf(cell.getRichStringCellValue());
//                }
//                break;
//            case HSSFCell.CELL_TYPE_STRING:
//                strCell = cell.getStringCellValue();
//                break;
//            case HSSFCell.CELL_TYPE_NUMERIC:
//                if (HSSFDateUtil.isCellDateFormatted(cell)) {//判断是日期类型
//                    Date date = cell.getDateCellValue();//从单元格获取日期数据
//                    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");//设定转换的格式
//                    strCell = formater.format(date);//将日期数据（Date 或者直接输入的格式正确的字符串）转换成String类型
//                }else{
//                    double Num = cell.getNumericCellValue();   //普通数字类型
//                    DecimalFormat formatCell = (DecimalFormat) NumberFormat.getPercentInstance();
//                    formatCell.applyPattern("0");
//                    strCell = formatCell.format(Num);
//                    if(Double.parseDouble(strCell)!=Num){
//                        formatCell.applyPattern(Double.toString(Num));
//                        strCell = formatCell.format(Num);
//                    }
//                    //System.out.println(strCell+strCell.length());
//                    if(strCell.length()>2 && ".0".equals(strCell.subSequence(strCell.length()-2, strCell.length())))strCell = Integer.parseInt(strCell.substring(0,strCell.length()-2))+"";
//                    //System.out.println("数字："+strCell);
//                }
//                break;
//            case HSSFCell.CELL_TYPE_BOOLEAN:
//                strCell = String.valueOf(cell.getBooleanCellValue());
//                break;
//            case HSSFCell.CELL_TYPE_BLANK:
//                strCell = "";
//                //System.out.print("空格");
//                break;
//            default:
//                strCell = "";
//                break;
//        }
//        if (strCell.equals("")) {
//            return "";
//        }
//        return strCell;
//    }
//    /**将获取到的数据在控制台输出*/
//    public void showExcel(){
//        System.out.println();
//        //控制台输出内容
//        System.out.println("获得Excel表格的内容:");
//        for(int i=1;i<=AllRows;i++){
//            for(int j=1; j<=AllCols; j++){
//                System.out.print(" - "+contents.getContents().get(Position.init(i,j)));
//            }
//            System.out.println();
//        }
////        for (int i=1; i<=AllRows; i++) {
////            for (int k=1;k<=AllCols;k++)
////                System.out.print(new Position(i,k).toString()+""+map.get(new Position(i,k))+"-"); //这里的get就没有办法获取到值，因为创建的对象是一个新的地址
////            System.out.println();
////        }
//    }
//
//    /**计算得出有效的Sheet*/
//    public void showSheet(){
//        //若有多个Sheet，这里只处理单Sheet
//        int sheetNum = wb.getNumberOfSheets();
//        System.out.println("Sheet数量："+ sheetNum);
//        int tempSheet=0;
//
//        for(int i = 0; i< sheetNum; i++){
//            HSSFSheet sheet = wb.getSheetAt(i);
//            int EffectiveRow=0;//有效行数
//            for (Row row : sheet){
//                if(row==null) continue;
//                EffectiveRow++;
//            }
//            System.out.println("有效的行数："+EffectiveRow);
//            if(EffectiveRow==0){
//                System.out.println("空Sheet");
//                continue;
//            }
//            tempSheet++;
//        }
//        sheetNum = tempSheet;
//        System.out.println("实际有效的Sheet："+ sheetNum);
//    }
//}
