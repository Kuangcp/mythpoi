package com.kuangcp.mythpoi.excel.base;

import com.kuangcp.mythpoi.config.ExternalConfig;
import com.kuangcp.mythpoi.utils.config.YamlUtil;
import java.net.URL;
import lombok.Data;

/**
 * Created by https://github.com/kuangcp on 18-2-27  下午12:32
 * Excel的一般性导出配置, 原本是想使用单例化的,但是构造器不能私有,Yaml估计是要要反射
 *
 * @author kuangcp
 */
@Data
public class MainConfig {

  private int startColNum = 0;
  private int startRowNum = 0;
  private int titleTotalNum = 2;
  private int contentStartNum = 3;
  private String dateFormat = "yyyy/MM/dd";

  private static MainConfig mainConfig = null;

  /**
   * 获取配置实例,
   * 如果有配置文件就覆盖这里默认的配置, 否则采用默认
   *
   * @return 返回配置对象
   */
  public static MainConfig getInstance() {
    if (mainConfig == null) {
      synchronized (MainConfig.class) {
        if (mainConfig == null) {
          // TODO 打包成jar别的项目引用就不会有问题,这里运行就会有问题,src前要加上项目名,估计是idea的问题
//            mainConfig = YamlUtil.readFile(MainConfig.class, "src/main/resources/excel.main.yml");

          // TODO 测试该方式
          ClassLoader classLoader = MainConfig.class.getClassLoader();
          URL resource = classLoader.getResource(ExternalConfig.EXCEL_DATA_FORMAT_CONFIG);
          if (resource != null) {
            String path = resource.getPath();
            mainConfig = YamlUtil.readFile(MainConfig.class, path);
          }
          if (mainConfig == null) {
            mainConfig = new MainConfig();
          }
        }
      }
    }
    return mainConfig;
  }

  private MainConfig() {
  }
}
