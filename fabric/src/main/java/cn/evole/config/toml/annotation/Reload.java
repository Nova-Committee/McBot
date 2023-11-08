package cn.evole.config.toml.annotation;

import cn.evole.config.toml.AutoReloadToml;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 应注解在继承自 {@link AutoReloadToml} 类的单例字段上
 * <pre>
 * public class AppToml extends AutoReloadToml {
 *
 *     &#064;Reload("config/app.toml")
 *     private static AppToml INSTANCE = TomlUtil.parseConfig("config/app.toml", AppToml.class, true);
 *
 * }
 * </pre>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Reload {
    /**
     * 配置文件路径
     */
    String value();

    /**
     * 在文件被修改时进行重载
     */
    boolean autoReload() default false;
}
