package com.kuangcp.mythpoi.excel.base;

import com.kuangcp.mythpoi.config.ExternalConfig;
import com.kuangcp.mythpoi.utils.config.YamlUtil;
import java.net.URL;
import java.util.Objects;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by https://github.com/kuangcp on 18-2-27  下午12:32
 * Excel的一般性导出配置, 原本是想使用单例化的,但是构造器不能私有,Yaml估计是要要反射
 *
 * @author kuangcp
 */
@Data
@Slf4j
public class MainConfig {

  /**
   * 列起始位置 0 1 2 ...
   */
  private int startColNum = 0;
  /**
   * 行起始位置 0 1 2 ... 对应到Excel要加1
   */
  private int startRowNum = 0;
  /**
   * 合并的大标题的最后一行下标 和Excel中行数一致(从1开始)
   */
  private int titleLastRowNum = 2;
  /**
   * Excel 内容的起始行数 和Excel中行数一致(从0开始)
   * 一般这个是 titleLastRowNum +1 , 因为有一行是列标题, 但是也可用来自定义别的行进来
   */
  private int contentStartNum = 3;
  /**
   * Excel 中所有日期的格式
   */
  private String dateFormat = "yyyy/MM/dd";

  private short titleFontSize = 12;
  private String titleFontName="Courier New";
  private boolean isTitleFontBold = true;

  private short contentFontSize = 10;
  private String contentFontName="Arial";
  private boolean isContentFontBold = false;


  private static MainConfig mainConfig = null;

  /**
   * 获取配置实例,
   * 如果有配置文件就覆盖这里默认的配置, 否则采用默认
   *
   * @return 返回配置对象
   */
  public static MainConfig getInstance() throws RuntimeException {
    if (mainConfig == null) {
      synchronized (MainConfig.class) {
        if (mainConfig == null) {
          ClassLoader classLoader = MainConfig.class.getClassLoader();
          URL resource = classLoader.getResource(ExternalConfig.EXCEL_DATA_FORMAT_CONFIG);
          if (!Objects.isNull(resource)) {
            String path = resource.getPath();
            mainConfig = YamlUtil.readFile(MainConfig.class, path);
          }
          if (mainConfig == null) {
            mainConfig = new MainConfig();
          }
        }
      }
    }
    try {
      checkConfig(mainConfig);
    } catch (RuntimeException e) {
      log.error(e.getMessage(), e);
      System.exit(1);
    }
    return mainConfig;
  }

  private MainConfig() {
  }

  private static void checkConfig(MainConfig config) throws RuntimeException {

    if (config.getTitleLastRowNum() < config.getStartRowNum() + 2) {
      throw new RuntimeException("titleLastRowNum must more than startRowNum of 2");
    }

  }
}
