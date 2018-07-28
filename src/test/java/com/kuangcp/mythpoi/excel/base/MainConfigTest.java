package com.kuangcp.mythpoi.excel.base;

import org.junit.Test;

/**
 * Created by https://github.com/kuangcp on 18-2-27  下午5:00
 *
 * @author kuangcp
 */
public class MainConfigTest {

  @Test
  public void testRead() {
    System.out.println(MainConfig.getInstance().toString());
  }
}