package com.kuangcp.mythpoi.utils.db;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by https://github.com/kuangcp on 18-3-13  下午11:04
 *
 * @author kuangcp
 */
public class BaseConfigTest {

    @Test
    public void testInitByYaml() throws Exception {
        BaseConfig result = BaseConfig.initByYaml();
        System.out.println(result.toString());
    }
}