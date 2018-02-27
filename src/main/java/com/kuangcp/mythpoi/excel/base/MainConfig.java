package com.kuangcp.mythpoi.excel.base;

import com.kuangcp.mythpoi.utils.config.YamlUtil;
import lombok.Data;

/**
 * Created by https://github.com/kuangcp on 18-2-27  下午12:32
 * Excel的一般性导出配置, 原本是想使用单例化的,但是构造器不能私有,Yaml估计是要要反射
 * @author kuangcp
 */
@Data
public class MainConfig {
    private int startColNum = 0;
    private int startRowNum = 0;
    private int titleTotalNum = 2;
    private int contentStartNum = 3;


    private static MainConfig mainConfig = null;
    /**
     * 获取配置实例, 如果没有配置文件就采用这里的默认配置,有配置文件就覆盖这里的配置
     * @return 返回配置对象
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
