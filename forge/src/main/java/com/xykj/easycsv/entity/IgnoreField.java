package com.xykj.easycsv.entity;

import java.lang.annotation.*;

/**
 * 在属性上添加此注解，输出csv文件时改字段将不会写入
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreField {
}
