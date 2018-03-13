package com.kuangcp.mythpoi.utils.db;

import com.kuangcp.mythpoi.utils.config.YamlUtil;
import lombok.Data;

/**
 * Created by https://github.com/kuangcp on 18-3-13  下午9:22
 * Mysql 基础配置
 * @author kuangcp
 */
@Data
public class BaseConfig {

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private String driver = "com.mysql.jdbc.Driver";

    public static BaseConfig initByYaml(){
        BaseConfig config =  YamlUtil.readFile(BaseConfig.class, "src/main/resources/mysql.yml");
        return config==null?new BaseConfig():config;
    }
    public BaseConfig initDriver(String driver) {
        this.driver = driver;
        return this;
    }

    public BaseConfig initHost(String host) {
        this.host = host;
        return this;
    }

    public BaseConfig initPort(int port) {
        this.port = port;
        return this;
    }

    public BaseConfig initDatabase(String database) {
        this.database = database;
        return this;
    }

    public BaseConfig initUsername(String username) {
        this.username = username;
        return this;
    }

    public BaseConfig initPassword(String password) {
        this.password = password;
        return this;
    }

}
