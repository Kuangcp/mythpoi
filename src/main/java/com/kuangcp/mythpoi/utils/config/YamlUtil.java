package com.kuangcp.mythpoi.utils.config;

import org.ho.yaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by https://github.com/kuangcp on 18-2-27  下午3:37
 * Yaml的读写工具类
 * @author kuangcp
 */
public class YamlUtil {

    /**
     * TODO 项目根目录问题, 为什么是项目的上级目录???
     * @param object  对象  对象的定义切记要有setget, 而且不能重载这些方法, 不然就会稀奇古怪的错误
     * @param filePath 绝对路径,目前存在问题
     * @return true 创建成功
     */
    public static boolean createFile(Object object, String filePath){
        File dumpFile=new File(filePath);
        System.out.println("配置文件绝对路径: "+dumpFile.getAbsolutePath());
        try {
            System.out.println(Yaml.dump(object));
            Yaml.dump(object, dumpFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 读取yml文件
     * @param target 配置文件对应的类
     * @param filePath 配置文件路径
     * @return 对象,找不到则是null
     */
    public static <T> T readFile(Class<T> target, String filePath){
        File readFile=new File(filePath);
//        System.out.println("配置文件绝对路径: "+readFile.getAbsolutePath());
        try {
            return Yaml.loadType(readFile, target);
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
