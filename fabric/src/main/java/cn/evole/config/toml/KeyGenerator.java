package cn.evole.config.toml;

import cn.evole.config.toml.util.NamingUtil;

import java.util.function.Function;

/**
 * key生成器，用于在写出toml时指定key的命名规则
 * @author Fndream
 */
public enum KeyGenerator {
    /**
     * 小驼峰 "intValue"
     */
    LOWER_CAMEL_CASE(fieldName -> fieldName),

    /**
     * 下划线 "int_value"
     */
    UNDERLINE(NamingUtil::lowerCamelCaseToUnderline),

    /**
     * 横线 "int-value"
     */
    HYPHENATED(NamingUtil::lowerCamelCaseToHyphenated);

    private final Function<String, String> generator;

    KeyGenerator(Function<String, String> generator) {
        this.generator = generator;
    }

    public Function<String, String> getGenerator() {
        return generator;
    }
}
