package com.kuangcp.mythpoi.excel.base;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by https://github.com/kuangcp on 18-2-27  下午5:00
 *
 * @author kuangcp
 */
public class MainConfigTest {

  /**
   * 测试 读物 yml 文件, 实例化 MainConfig
   */
  @Test
  public void testRead() {
    MainConfig instance = MainConfig.getInstance();

    Assert.assertNotNull(instance);

    System.out.println(instance.toString());
  }
}