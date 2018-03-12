package com.kuangcp.mythpoi.excel.type;

import com.kuangcp.mythpoi.excel.base.LoadCellValue;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Created by https://github.com/kuangcp on 18-3-12  上午11:16
 *
 * @author kuangcp
 */
public class StringHandler implements LoadCellValue{
    @Override
    public HSSFCell loadValue(HSSFRow row, int index, String value) {
        HSSFCell cell = row.createCell(index, HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(value);
        return cell;
    }
}
