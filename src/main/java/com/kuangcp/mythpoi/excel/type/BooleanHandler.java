package com.kuangcp.mythpoi.excel.type;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.CellType;

/**
 * Created by https://github.com/kuangcp on 18-3-12  下午7:23
 *
 * @author kuangcp
 */
public class BooleanHandler implements LoadCellValue{
    @Override
    public HSSFCell loadValue(HSSFRow row, int index, Object value) {

        HSSFCell cell = row.createCell(index, CellType.BOOLEAN);
        cell.setCellValue(Boolean.valueOf(value.toString()));
        return cell;
    }
}
