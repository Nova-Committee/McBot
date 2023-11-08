package cn.evole.config.toml;

import cn.evole.config.toml.annotation.TableField;
import org.tomlj.TomlArray;
import org.tomlj.TomlTable;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class TomlWriter {
    private final StringBuilder sb = new StringBuilder();
    private final KeyGenerator keyGenerator;

    private boolean inLineArray;

    TomlWriter(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    String build() {
        return sb.toString();
    }

    TomlWriter writeMap(Map<String, TomlConfig.MetaValue> map, String path) {
        if (inLineArray) {
            sb.append("{ ");
            int i = 0;
            int size = map.size();
            for (Map.Entry<String, TomlConfig.MetaValue> entry : map.entrySet()) {
                sb.append(formatKey(entry.getKey())).append(" = ");
                writeValue(entry.getValue(), path);
                if (i++ < size - 1) {
                    sb.append(", ");
                }
            }
            sb.append(" }");
        } else {
            for (Map.Entry<String, TomlConfig.MetaValue> entry : map.entrySet()) {
                TomlConfig.MetaValue metaValue = entry.getValue();
                TableField tableField = metaValue.tableField();
                Object value = metaValue.value();
                String key = tableField == null || tableField.value().isEmpty() ? formatKey(entry.getKey()) : tableField.value();

                String newPath = (path.isEmpty() ? "" : path + ".") + key;

                boolean hasTopComment = tableField != null && tableField.topComment().length > 0;
                boolean hasRightComment = tableField != null && tableField.rightComment().length > 0;
                boolean isTable = isTable(value);
                boolean isArrayTable = isTableArray(value);

                if (isTable) {
                    sb.append("\n");
                    if (hasTopComment) {
                        sb.append("\n");
                        writeLine(tableField.prefixLine());
                        writeTopComment(tableField.topComment());
                    }
                    sb.append("\n[").append(newPath).append("]");
                } else if (!isArrayTable) {
                    if (hasTopComment) {
                        sb.append("\n");
                        writeLine(tableField.prefixLine());
                        writeTopComment(tableField.topComment());
                    }
                    sb.append("\n").append(key).append(" = ");
                }

                writeValue(metaValue, newPath);

                if (!isTable && !isArrayTable && hasRightComment) {
                    writeRightComment(tableField.rightComment());
                }

                if (!isTable && !isArrayTable && hasTopComment) {
                    writeLine(tableField.suffixLine());
                }
            }
        }
        return this;
    }

    TomlWriter writeList(List<?> list, String path) {
        if (!inLineArray && isTableArray(list)) {
            for (Object element : list) {
                sb.append("\n\n");
                sb.append("[[").append(formatKey(path)).append("]]");
                writeMap(toMetaMap(element), path);
            }
        } else {
            inLineArray = true;
            sb.append("[");
            for (int i = 0; i < list.size(); i++) {
                writeValue(new TomlConfig.MetaValue(list.get(i)), path);
                if (i < list.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            inLineArray = false;
        }
        return this;
    }


    private void writeValue(TomlConfig.MetaValue metaValue, String path) {
        Object value = metaValue.value();
        Class<?> type = value.getClass();
        if (type == Integer.class || type == Long.class || type == Double.class || type == Boolean.class || type == LocalDate.class) {
            sb.append(value);
        } else if (type.isEnum()) {
            sb.append("\"").append(value).append("\"");
        } else if (value instanceof String str) {
            if (str.contains("\n")) {
                sb.append("\"\"\"\n");
                sb.append(str);
                sb.append("\"\"\"");
            } else {
                sb.append("\"").append(str).append("\"");
            }
        } else if (value instanceof LocalDateTime dateTime) {
            sb.append(dateTime.format(DateTimeFormatter.ISO_DATE_TIME));
        } else if (isTable(value)) {
            writeMap(toMetaMap(value), path);
        } else if (isArray(value)) {
            writeList(toList(value), path);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + value.getClass().getName());
        }
    }

    private void writeTopComment(String[] comment) {
        String commentString = String.join("\n", comment).replaceAll("(\n)+$", "").replace("\n", "\n# ");
        sb.append("# ").append(new String(commentString.getBytes(), StandardCharsets.UTF_8));
    }

    private void writeRightComment(String[] comment) {
        String commentString = String.join("\n", comment).replaceAll("(\n)+$", "").replace("\n", " ");
        sb.append("  # ").append(new String(commentString.getBytes(), StandardCharsets.UTF_8));
    }

    private void writeLine(int count) {
        sb.append("\n".repeat(Math.max(0, count)));
    }

    private String formatKey(String key) {
        return keyGenerator.getGenerator().apply(key);
    }

    private String escapeString(String value) {
        return value.replace("\"", "\\\"");
    }

    private boolean isTable(Object obj) {
        return obj instanceof TomlConfig || obj instanceof TomlTable || obj instanceof Map<?, ?>;
    }

    private boolean isArray(Object obj) {
        return obj.getClass().isArray() || obj instanceof TomlArray ||  obj instanceof List<?>;
    }

    private boolean isTableArray(Object obj) {
        Class<?> clazz = obj.getClass();
        if (clazz.isArray()) {
            Class<?> componentType = clazz.getComponentType();
            return TomlConfig.class.isAssignableFrom(componentType) || TomlTable.class.isAssignableFrom(componentType);
        } else if (obj instanceof List<?> list) {
            for (Object e : list) {
                if (!(e instanceof TomlConfig) && !(e instanceof TomlTable) && !(e instanceof Map<?, ?>)) {
                    return false;
                }
            }
            return list.size() != 0;
        } else if (obj instanceof TomlArray array) {
            for (int i = 0; i < array.size(); i++) {
                Object e = array.get(i);
                if (!(e instanceof TomlConfig) && !(e instanceof TomlTable) && !(e instanceof Map<?, ?>)) {
                    return false;
                }
            }
            return array.size() != 0;
        }
        return false;
    }

    private Map<String, TomlConfig.MetaValue> toMetaMap(Object obj) {
        if (obj instanceof TomlConfig tomlConfig) {
            return tomlConfig.toMetaMap();
        } else if (obj instanceof TomlTable tomlTable) {
            Map<String, TomlConfig.MetaValue> resultMap = new LinkedHashMap<>();
            Map<String, TomlConfig.MetaValue> arrayMap = new LinkedHashMap<>();
            Map<String, TomlConfig.MetaValue> tableMap = new LinkedHashMap<>();
            for (Map.Entry<String, Object> entry : tomlTable.toMap().entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof TomlArray) {
                    arrayMap.put(key, new TomlConfig.MetaValue(value));
                } else if (value instanceof TomlTable) {
                    tableMap.put(key, new TomlConfig.MetaValue(value));
                } else {
                    resultMap.put(key, new TomlConfig.MetaValue(value));
                }
            }
            resultMap.putAll(arrayMap);
            resultMap.putAll(tableMap);
            return resultMap;
        } else if (obj instanceof Map<?, ?> map) {
            Map<String, TomlConfig.MetaValue> resultMap = new LinkedHashMap<>();
            Map<String, TomlConfig.MetaValue> arrayMap = new LinkedHashMap<>();
            Map<String, TomlConfig.MetaValue> tableMap = new LinkedHashMap<>();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                String key = (String) entry.getKey();
                Object value = entry.getValue();
                if (value instanceof TomlArray) {
                    arrayMap.put(key, new TomlConfig.MetaValue(value, null));
                } else if (value instanceof TomlTable) {
                    tableMap.put(key, new TomlConfig.MetaValue(value, null));
                } else {
                    resultMap.put(key, new TomlConfig.MetaValue(value, null));
                }
            }
            resultMap.putAll(arrayMap);
            resultMap.putAll(tableMap);
            return resultMap;
        }
        throw new IllegalArgumentException("Unsupported " + obj.getClass().getName() + " is not a toml table type");
    }

    private List<?> toList(Object obj) {
        if (obj.getClass().isArray()) {
            return TomlHelper.arrayToList(obj);
        } else if (obj instanceof TomlArray array) {
            return array.toList();
        } else if (obj instanceof List<?> list) {
            return list;
        }
        throw new IllegalArgumentException(obj.getClass().getName() + " is not a toml array type");
    }
}
