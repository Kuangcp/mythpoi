package com.kuangcp.mythpoi.excel.type;

import com.kuangcp.mythpoi.utils.config.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Created by https://github.com/kuangcp on 18-3-12  下午7:21
 *
 * @author kuangcp
 */
public class DateHandler implements LoadCellValue{
    @Override
    public HSSFCell loadValue(HSSFRow row, int index, Object value) {
        HSSFCell cell = row.createCell(index, HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(DateUtil.format(value));
        return cell;
    }
}
