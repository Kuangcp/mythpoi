package com.kuangcp.mythpoi.utils.config;

import com.kuangcp.mythpoi.excel.base.MainConfig;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by https://github.com/kuangcp on 18-2-27  下午4:10
 * yml 配置文件的读写
 * @author kuangcp
 */
public class YamlUtilTest {

    // 生成配置文件
    @Test
    public void testCreateFile() throws Exception {
        // TODO 同一个属性set两次就会输出不了, 这又是什么Bug
        MainConfig config = MainConfig.getInstance();
        config.setContentStartNum(11);
        config.setStartColNum(0);
        config.setTitleTotalNum(3);
        config.setStartRowNum(0);

        boolean result = YamlUtil.createFile(config, "src/main/resources/excel.main.yml");
        Assert.assertEquals(true, result);
    }

    // 测试读取配置文件
    @Test
    public void testRead() {
        MainConfig con = YamlUtil.readFile(MainConfig.class, "src/main/resources/excel.main.yml");
        System.out.println(con.toString());
    }
}