package com.kuangcp.mythpoi.utils.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by https://github.com/kuangcp on 18-2-28  下午7:05
 *
 * @author kuangcp
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelSheet {
    String exportTitle() default "";
    String importTitle() default "";
}
