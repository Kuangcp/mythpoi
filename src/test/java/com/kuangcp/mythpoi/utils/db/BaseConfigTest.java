package com.kuangcp.mythpoi.utils.db;

import org.junit.Test;

/**
 * Created by https://github.com/kuangcp on 18-3-13  下午11:04
 *
 * @author kuangcp
 */
public class BaseConfigTest {

  @Test
  public void testInitByYml() throws Exception {
    BaseConfig result = BaseConfig.initByYaml();
    System.out.println("读取配置结果 " + result.toString());
  }
}