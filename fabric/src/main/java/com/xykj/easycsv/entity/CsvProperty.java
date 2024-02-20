package com.xykj.easycsv.entity;

import java.lang.annotation.*;

/**
 *
 * excelparse注解
 * @author wangyiqian
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvProperty {
    /**
     * Excel列的的标题
     * @return
     */
    String value() default "";

    int index() default -1;
}
