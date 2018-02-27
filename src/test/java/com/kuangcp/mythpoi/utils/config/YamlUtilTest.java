package com.kuangcp.mythpoi.utils.config;

import com.kuangcp.mythpoi.excel.base.BaseConfig;
import com.kuangcp.mythpoi.excel.base.MainConfig;
import org.ho.yaml.Yaml;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sun.applet.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by https://github.com/kuangcp on 18-2-27  下午4:10
 *
 * @author kuangcp
 */
public class YamlUtilTest {

    @Test
    public void testCreateFile() throws Exception {
        // TODO 同一个属性set两次就会输出不了, 这又是什么Bug
        MainConfig config = MainConfig.getInstance();
        config.setContentStartNum(11);
        config.setStartColNum(1);
        config.setTitleRowEndNum(3);
        config.setStartRowNum(1);
//        MainConfig config1 = MainConfig.getInstance();
//        config1.setContentStartNum(11);
//        config1.setStartColNum(1);
//        config1.setTitleRowEndNum(3);
//        config1.setStartRowNum(1);
//        List list = new ArrayList<>();
//        list.add(config);
//        list.add(config1);
//        String sss = Yaml.dumpStream(list.iterator());
//        System.out.println(sss);
        boolean result = YamlUtil.createFile(config, "excel.main.yml");
        Assert.assertEquals(true, result);
    }

    @Test
    public void testRead() {
        MainConfig con = YamlUtil.readFile(MainConfig.class, "excel.main.yml");
        System.out.println(con.toString());
    }
}