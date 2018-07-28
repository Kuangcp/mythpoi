package com.kuangcp.mythpoi.utils.config;

import com.kuangcp.mythpoi.excel.base.MainConfig;
import com.kuangcp.mythpoi.utils.db.BaseConfig;
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
    // TODO 同一个属性set两次就会输出不了, 这又是什么Bug
    MainConfig config = MainConfig.getInstance();
    config.setContentStartNum(11);
    config.setStartColNum(1);
    config.setTitleTotalNum(3);
    config.setStartRowNum(1);
    System.out.println(path);
    boolean result = YamlUtil.createFile(config, path);
    Assert.assertTrue(result);
  }

  // 测试读取配置文件
  @Test
  public void testRead() {
    MainConfig con = YamlUtil.readFile(MainConfig.class, path);
    assert con != null;
    System.out.println(con.toString());
  }

  @Test
  public void testMysql() {
    BaseConfig config = new BaseConfig();
    config.initDriver("com.mysql.jdbc.Driver").initDatabase("test")
        .initHost("localhost").initPort(3306)
        .initUsername("myth").initPassword("ad");
    System.out.println(config.toString());
    YamlUtil.createFile(config, path);
  }

  @Test
  public void testReadM() {
    BaseConfig baseConfig = YamlUtil.readFile(BaseConfig.class, path);
    assert baseConfig != null;
    System.out.println(baseConfig.toString());
  }
}