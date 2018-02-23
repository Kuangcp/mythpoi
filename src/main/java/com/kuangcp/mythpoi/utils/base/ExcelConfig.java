package com.kuangcp.mythpoi.utils.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by https://github.com/kuangcp on 18-2-22  下午3:27 <br/>
 * Excel配置注解, 用于Excel的导入导出<br/>
 *      name  实体的属性对应的列标题
 * @author kuangcp
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelConfig {
    String name();
    // 是否需要导出, 默认是导出true
    boolean exportFlag() default true;
}
