package cn.evole.config.toml.annotation;

import cn.evole.config.toml.AutoLoadTomlConfig;
import cn.evole.config.toml.TomlConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 设置字段加载与写出时的行为。<br/>
 * 加载行为作用于自动加载 {@link AutoLoadTomlConfig}，写出行为仅作用于 {@link TomlConfig}.toToml()方法
 * @author Fndream
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableField {
    /**
     * 加载与写出时生效：key名称
     */
    String value() default "";

    /**
     * 加载时生效：字段是否必需存在
     */
    boolean required() default false;

    /**
     * 加载与写出时生效：是否忽略此字段的加载与写出
     */
    boolean ignore() default false;

    /**
     * 加载时生效：是否忽略此字段的加载
     */
    boolean ignoreLoad() default false;

    /**
     * 写出时生效：是否忽略此字段的写出
     */
    boolean ignoreWrite() default false;

    /**
     * 加载时生效：默认值，支持int、long、double、boolean、String、Enum、array/List、LocalDate、LocalDateTime类型的字符串表达式。
     * @deprecated 存在众多功能之间兼容问题，建议为字段赋值初始值作为设置默认值的方式。
     */
    @Deprecated
    String defaultValue() default "";

    /**
     * 写出时生效：在字段上方的注释
     */
    String[] topComment() default {};

    /**
     * 写出时生效：在字段右侧的注释
     */
    String[] rightComment() default {};

    /**
     * 写出时生效：写出top注释前添加空行的数量
     */
    int prefixLine() default 0;

    /**
     * 写出时生效：写出top注释和键值对后添加空行的数量
     */
    int suffixLine() default 1;
}
