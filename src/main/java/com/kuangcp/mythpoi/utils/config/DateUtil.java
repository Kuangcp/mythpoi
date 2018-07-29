package com.kuangcp.mythpoi.utils.config;

import com.kuangcp.mythpoi.excel.base.MainConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by https://github.com/kuangcp on 18-3-3  下午7:23
 * 重写Date格式化工具类, 更健壮
 *
 * @author kuangcp
 */
public class DateUtil {

  private static MainConfig mainConfig = MainConfig.getInstance();
  private static SimpleDateFormat dateFormat = new SimpleDateFormat(mainConfig.getDateFormat());

  public static String format(Object date) {
    if (Objects.isNull(date) || date.toString().isEmpty()) {
      return null;
    }
    return dateFormat.format(date);
  }

  public static Date parse(String date) throws ParseException {
    if (Objects.isNull(date) || date.isEmpty()) {
      return null;
    }
    return dateFormat.parse(date);
  }
}
