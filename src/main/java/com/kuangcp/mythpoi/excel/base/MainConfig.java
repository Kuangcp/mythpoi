package com.kuangcp.mythpoi.excel.base;

import com.kuangcp.mythpoi.utils.config.YamlUtil;
import lombok.Data;
import sun.applet.Main;

/**
 * Created by https://github.com/kuangcp on 18-2-27  下午12:32
 *
 * @author kuangcp
 */
@Data
public class MainConfig {
    // 配置属性
    private int startColNum = 0;
    private int startRowNum = 0;
    // 下标从0开始,1结束,也就是说标题要占两行
    private int titleRowEndNum = 1;
    private int titleTotalNum = 2;
    private int contentStartNum = 3;

    private static MainConfig mainConfig = null;

    /**
     * 获取配置实例, 如果没有配置文件就采用这里的默认配置,有配置文件就覆盖这里的配置
     * @return
     */
    public static MainConfig getInstance() {
        if(mainConfig == null){
            mainConfig = YamlUtil.readFile(MainConfig.class, "excel.main.yml");
            if (mainConfig == null){
                mainConfig = new MainConfig();
            }
        }
        return mainConfig;
    }
}
