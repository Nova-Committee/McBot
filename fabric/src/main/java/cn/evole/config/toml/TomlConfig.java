package cn.evole.config.toml;

import cn.evole.config.toml.annotation.TableField;
import cn.evole.config.toml.util.ReflectUtil;
import org.tomlj.TomlTable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置基类
 *
 * @author Fndream
 */
public abstract class TomlConfig {
    @TableField(ignore = true)
    protected TomlTable source;

    public TomlConfig(TomlTable source) {
        this.source = source;
    }

    public TomlTable getSource() {
        return this.source;
    }

    public String toToml() {
        return toToml(KeyGenerator.HYPHENATED);
    }

    public String toToml(KeyGenerator keyGenerator) {
        return new TomlWriter(keyGenerator).writeMap(this.toMetaMap(), "").build().trim();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        Field[] fields = ReflectUtil.getFields(this.getClass());
        List<Field> filterFields = new ArrayList<>();
        List<Field> tableFields = new ArrayList<>();
        List<Field> arrayFields = new ArrayList<>();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) {
                continue;
            }

            TableField tableField = field.getAnnotation(TableField.class);
            if (tableField != null && tableField.ignore()) {
                continue;
            }

            Class<?> fieldType = field.getType();
            if (fieldType.isArray() || List.class.isAssignableFrom(fieldType)) {
                arrayFields.add(field);
            } else if (TomlTable.class.isAssignableFrom(fieldType) || TomlConfig.class.isAssignableFrom(fieldType)) {
                tableFields.add(field);
            } else {
                filterFields.add(field);
            }
        }
        filterFields.addAll(arrayFields);
        filterFields.addAll(tableFields);

        for (Field field : filterFields) {
            try {
                field.setAccessible(true);
                Object value = field.get(this);
                if (value != null) {
                    resultMap.put(field.getName(), value);
                }
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
        return resultMap;
    }

    Map<String, MetaValue> toMetaMap() {
        Map<String, MetaValue> resultMap = new LinkedHashMap<>();
        Field[] fields = ReflectUtil.getFields(this.getClass());
        List<Field> filterFields = new ArrayList<>();
        List<Field> tableFields = new ArrayList<>();
        List<Field> arrayFields = new ArrayList<>();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) {
                continue;
            }

            TableField tableField = field.getAnnotation(TableField.class);
            if (tableField != null && (tableField.ignore() || tableField.ignoreWrite())) {
                continue;
            }

            Class<?> fieldType = field.getType();
            if (fieldType.isArray() || List.class.isAssignableFrom(fieldType)) {
                arrayFields.add(field);
            } else if (TomlTable.class.isAssignableFrom(fieldType) || TomlConfig.class.isAssignableFrom(fieldType)) {
                tableFields.add(field);
            } else {
                filterFields.add(field);
            }
        }
        filterFields.addAll(arrayFields);
        filterFields.addAll(tableFields);

        for (Field field : filterFields) {
            try {
                field.setAccessible(true);
                Object value = field.get(this);
                if (value != null) {
                    resultMap.put(field.getName(), new MetaValue(value, field.getAnnotation(TableField.class)));
                }
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
        return resultMap;
    }

    record MetaValue(Object value, TableField tableField) {
        public MetaValue(Object value) {
            this(value, null);
        }
    }
}
