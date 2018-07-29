package com.kuangcp.mythpoi.utils.config;

import com.kuangcp.mythpoi.excel.base.MainConfig;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by https://github.com/kuangcp on 18-2-27  下午4:10
 * yml 配置文件的读写
 *
 * @author kuangcp
 */
@Ignore
public class YamlUtilTest {

  private String path = "src/main/resources/excel.main.yml";

  // 生成配置文件
  @Test
  public void testCreateFile() throws Exception {
    MainConfig config = MainConfig.getInstance();
    config.setContentStartNum(5);
    System.out.println(path);
    boolean result = YamlUtil.createFile(config, path);
    Assert.assertTrue(result);
  }

  // 测试读取配置文件
  @Test
  public void testRead() {
    MainConfig mainConfig = YamlUtil.readFile(MainConfig.class, path);
    Assert.assertNotNull(mainConfig);
    System.out.println(mainConfig.toString());
  }
}