package cn.evole.config.toml;


import cn.evole.config.toml.annotation.TableField;
import cn.evole.config.toml.util.NamingUtil;
import org.tomlj.TomlTable;

import java.lang.reflect.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 自动加载配置类
 *
 * @author Fndream
 */
public abstract class AutoLoadTomlConfig extends TomlConfig {

    public AutoLoadTomlConfig(TomlTable source) {
        super(source);
    }

    /**
     * 加载数据源到字段，不包含父类字段
     *
     * @param clazz 要加载的类
     */
    protected void load(Class<? extends AutoLoadTomlConfig> clazz) {
        if (this.source != null) {
            loadSource(clazz.getDeclaredFields());
        }
    }

    private void loadSource(Field[] fields) {
        for (Field field : fields) {
            TableField tableField = field.getAnnotation(TableField.class);

            if ((tableField != null && (tableField.ignore() || tableField.ignoreLoad())) || Modifier.isTransient(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            String fieldHyphenatedName = NamingUtil.lowerCamelCaseToHyphenated(field.getName());
            String fieldUnderlineName = NamingUtil.lowerCamelCaseToUnderline(field.getName());
            String annotationName = tableField != null && !tableField.value().isBlank() ? tableField.value() : "";

            try {
                String fieldKey = null;
                if (!annotationName.isEmpty() && this.source.contains(annotationName)) {
                    fieldKey = annotationName;
                } else if (this.source.contains(field.getName())) {
                    fieldKey = field.getName();
                } else if (this.source.contains(fieldHyphenatedName)) {
                    fieldKey = fieldHyphenatedName;
                } else if (this.source.contains(fieldUnderlineName)) {
                    fieldKey = fieldUnderlineName;
                }

                if (fieldKey == null) {
                    continue;
                }

                Object value = getValue(field, fieldKey, tableField);
                if (value != null) {
                    field.setAccessible(true);
                    field.set(this, value);
                }
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private Object getValue(Field field, String fieldKey, TableField tableField) {
        Class<?> fieldType = field.getType();
        Object o = this.source.get(fieldKey);
        if (o != null && fieldType.isAssignableFrom(o.getClass())) {
            return o;
        }

        boolean required = tableField != null && tableField.required();
        String defaultValue = tableField != null ? tableField.defaultValue() : "";

        Object value;
        if (TomlConfig.class.isAssignableFrom(fieldType)) {
            value = getConfigValue(fieldType, fieldKey, required);
        } else if (Map.class.isAssignableFrom(fieldType)) {
            value = getMapValue(fieldType, fieldKey, required);
        } else if (field.getType().isArray()) {
            value = getArrayValue(fieldType, fieldKey, required, defaultValue);
        } else if (List.class.isAssignableFrom(field.getType())) {
            value = getListValue(field.getGenericType(), fieldKey, required, defaultValue);
        } else {
            value = getPrimitiveValue(fieldType, fieldKey, required, defaultValue);
        }
        return value;
    }

    private Object getConfigValue(Class<?> fieldType, String fieldKey, boolean required) {
        if (required) {
            return TomlUtil.getRequiredTomlConfig(this.source, fieldKey, fieldType);
        }
        return TomlUtil.getTomlConfig(this.source, fieldKey, fieldType);
    }

    private Object getMapValue(Class<?> fieldType, String fieldKey, boolean required) {
        if (required) {
            return TomlUtil.getRequiredTomlTable(this.source, fieldKey).toMap();
        }
        return Optional.ofNullable(TomlUtil.getTomlTable(this.source, fieldKey)).map(TomlTable::toMap).orElse(null);
    }

    private Object getArrayValue(Class<?> filedType, String fieldKey, boolean required, String defaultValue) {
        if (required) {
            return TomlUtil.getRequiredArray(this.source, fieldKey, filedType);
        }
        Object result = TomlUtil.getArray(this.source, fieldKey, filedType);
        return result != null ? result : (!defaultValue.isEmpty() ? parseDefaultValue(filedType, defaultValue) : null);
    }

    private Object getListValue(Type filedType, String fieldKey, boolean required, String defaultValue) {
        if (required) {
            return TomlUtil.getRequiredList(this.source, fieldKey, listTypeToArrayClass(filedType));
        }
        Class<?> arrayClass = listTypeToArrayClass(filedType);
        Object result = TomlUtil.getList(this.source, fieldKey, arrayClass);
        return result != null ? result : (!defaultValue.isEmpty() ? TomlHelper.arrayToList(parseDefaultValue(arrayClass, defaultValue)) : null);
    }

    private Object getPrimitiveValue(Class<?> fieldType, String fieldKey, boolean required, String defaultValue) {
        if (fieldType == int.class || fieldType == Integer.class) {
            if (required) {
                return TomlUtil.getRequiredInt(this.source, fieldKey);
            }
            if (!defaultValue.isEmpty()) {
                return TomlUtil.getInt(this.source, fieldKey, Integer.parseInt(defaultValue));
            }
            return TomlUtil.getInt(this.source, fieldKey);
        }

        if (fieldType == long.class || fieldType == Long.class) {
            if (required) {
                return TomlUtil.getRequiredLong(this.source, fieldKey);
            }
            if (!defaultValue.isEmpty()) {
                return TomlUtil.getLong(this.source, fieldKey, Long.parseLong(defaultValue));
            }
            return TomlUtil.getLong(this.source, fieldKey);
        }

        if (fieldType == double.class || fieldType == Double.class) {
            if (required) {
                return TomlUtil.getRequiredDouble(this.source, fieldKey);
            }
            if (!defaultValue.isEmpty()) {
                return TomlUtil.getDouble(this.source, fieldKey, Double.parseDouble(defaultValue));
            }
            return TomlUtil.getDouble(this.source, fieldKey);
        }

        if (fieldType == boolean.class || fieldType == Boolean.class) {
            if (required) {
                return TomlUtil.getRequiredBoolean(this.source, fieldKey);
            }
            if (!defaultValue.isEmpty()) {
                return TomlUtil.getBoolean(this.source, fieldKey, Boolean.parseBoolean(defaultValue));
            }
            return TomlUtil.getBoolean(this.source, fieldKey);
        }

        if (fieldType == String.class) {
            if (required) {
                return TomlUtil.getRequiredString(this.source, fieldKey);
            }
            if (!defaultValue.isEmpty()) {
                return TomlUtil.getString(this.source, fieldKey, defaultValue);
            }
            return TomlUtil.getString(this.source, fieldKey, "");
        }

        if (fieldType == LocalDate.class) {
            if (required) {
                return TomlUtil.getRequiredLocalDate(this.source, fieldKey);
            }
            if (!defaultValue.isEmpty()) {
                return TomlUtil.getLocalDate(this.source, fieldKey, LocalDate.parse(defaultValue));
            }
            return TomlUtil.getLocalDate(this.source, fieldKey);
        }

        if (fieldType == LocalDateTime.class) {
            if (required) {
                return TomlUtil.getRequiredLocalDateTime(this.source, fieldKey);
            }
            if (!defaultValue.isEmpty()) {
                return TomlUtil.getLocalDateTime(this.source, fieldKey, LocalDateTime.parse(defaultValue));
            }
            return TomlUtil.getLocalDateTime(this.source, fieldKey);
        }

        if (fieldType.isEnum()) {
            if (required) {
                return TomlUtil.getRequiredEnum(this.source, fieldKey, fieldType);
            }
            if (!defaultValue.isEmpty()) {
                return TomlUtil.getEnum(this.source, fieldKey, fieldType, defaultValue);
            }
            return TomlUtil.getEnum(this.source, fieldKey, fieldType);
        }
        return null;
    }

    private Object parseDefaultValue(Class<?> fieldType, String defaultValue) {
        if (fieldType == int.class || fieldType == Integer.class) {
            return Integer.parseInt(defaultValue);
        }
        if (fieldType == long.class || fieldType == Long.class) {
            return Long.parseLong(defaultValue);
        }
        if (fieldType == double.class || fieldType == Double.class) {
            return Double.parseDouble(defaultValue);
        }
        if (fieldType == boolean.class || fieldType == Boolean.class) {
            return Boolean.parseBoolean(defaultValue);
        }
        if (fieldType == String.class) {
            return defaultValue;
        }
        if (fieldType.isArray()) {
            return parseArrayDefaultValue(fieldType, defaultValue.replaceAll("^\\[|]$", ""), new int[]{0});
        }
        throw new IllegalStateException("Unsupported default value type: " + fieldType.getName());
    }

    private Object parseArrayDefaultValue(Class<?> fieldType, String defaultValue, int[] x) {
        StringBuilder sb = new StringBuilder();
        List<Object> result = new ArrayList<>();

        char[] chars = defaultValue.toCharArray();
        for (int i = x[0]; i < chars.length; i++) {
            char ch = chars[i];

            // 所有递归同步遍历进度
            x[0] = i;

            switch (ch) {
                // 栈入
                case '[' -> {
                    Class<?> componentType = fieldType.getComponentType();
                    if (componentType == null) {
                        throw new IllegalStateException("Default value array dimensions does not match field type dimensions");
                    }
                    x[0] = i + 1;
                    result.add(parseArrayDefaultValue(componentType, defaultValue, x));
                    i = x[0];
                }
                // 栈出
                case ']' -> {
                    if (sb.length() > 0) {
                        result.add(parseDefaultValue(fieldType.getComponentType(), sb.toString()));
                        sb.setLength(0);
                    }
                    Object array = Array.newInstance(fieldType.getComponentType(), result.size());
                    for (int j = 0; j < result.size(); j++) {
                        Array.set(array, j, result.get(j));
                    }
                    return array;
                }
                // 转义
                case '\\' -> {
                    String trans = i + 1 < chars.length ? ("" + ch + chars[++i]) : "\0";
                    sb.append(trans.translateEscapes());
                }
                // 分割
                case ',' -> {
                    if (sb.length() > 0) {
                        result.add(parseDefaultValue(fieldType.getComponentType(), sb.toString()));
                        sb.setLength(0);
                    }
                }
                // 构建
                default -> {
                    sb.append(ch);
                }
            }
        }

        if (sb.length() > 0) {
            result.add(parseDefaultValue(fieldType.getComponentType(), sb.toString()));
        }

        Object array = Array.newInstance(fieldType.getComponentType(), result.size());
        for (int j = 0; j < result.size(); j++) {
            Array.set(array, j, result.get(j));
        }
        return array;
    }

    private Class<?> listTypeToArrayClass(Type fieldType) {
        if (!List.class.isAssignableFrom((Class<?>) fieldType)) {
            throw new IllegalArgumentException("Field is not a List");
        }

        StringBuilder sb = new StringBuilder();
        listParameterizedTypeToArrayClassName(fieldType, sb);
        try {
            return Class.forName(sb.toString());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void listParameterizedTypeToArrayClassName(Type type, StringBuilder sb) {
        if (type instanceof ParameterizedType pt) {
            Type actualType = pt.getActualTypeArguments()[0];
            if (actualType instanceof ParameterizedType p && !List.class.isAssignableFrom((Class<?>) p.getRawType())) {
                throw new IllegalArgumentException("Parameterized type " + ((Class<?>) p.getRawType()).getName() + " is not a List");
            }
            sb.append("[");
            listParameterizedTypeToArrayClassName(actualType, sb);
        } else if (type instanceof Class<?> clazz) {
            if (clazz == Integer.class) {
                sb.append("I");
            } else if (clazz == Long.class) {
                sb.append("J");
            } else if (clazz == Double.class) {
                sb.append("D");
            } else if (clazz == Boolean.class) {
                sb.append("Z");
            } else if (clazz == String.class) {
                sb.append("L").append(String.class.getName()).append(";");
            } else if (TomlConfig.class.isAssignableFrom(clazz)) {
                sb.append("L").append(clazz.getName()).append(";");
            }
        } else {
            throw new IllegalStateException("Unexpected type: " + type.getClass().getName());
        }
    }
}
