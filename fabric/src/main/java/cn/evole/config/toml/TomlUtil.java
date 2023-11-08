package cn.evole.config.toml;

import cn.evole.config.toml.exception.TomlInvalidTypeException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tomlj.Toml;
import org.tomlj.TomlArray;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.*;

/**
 * 对Tomlj库中{@link TomlTable}类的一系列Getter方法封装，以及读取和写出TOML文件的方法
 *
 * @author Fndream
 */
public class TomlUtil {
    private TomlUtil() {
    }

    /**
     * 解析为配置源
     * @param toml Toml字符串
     * @return {@link TomlTable} 实例
     */
    public static TomlTable parseTable(String toml) {
        return Toml.parse(toml);
    }

    /**
     * 解析为配置源
     * @param file 文件
     * @return {@link TomlTable} 实例
     */
    public static TomlTable parseTable(File file) {
        try {
            return Toml.parse(file.toPath());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 解析为配置源
     * @param path 文件路径
     * @return {@link TomlTable} 实例
     */
    public static TomlTable parseTable(Path path) {
        try {
            return Toml.parse(path);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 读取配置文件
     * @param path 文件路径
     * @param clazz 配置类，继承自 {@link TomlConfig}
     * @return 指定的配置类实例
     */
    public static <T> T readConfig(String path, Class<T> clazz) {
        return readConfig(Paths.get(path), clazz, false);
    }

    /**
     * 读取配置文件
     * @param file 文件
     * @param clazz 配置类，继承自 {@link TomlConfig}
     * @return 指定的配置类实例
     */
    public static <T> T readConfig(File file, Class<T> clazz) {
        return readConfig(file.toPath(), clazz, false);
    }

    /**
     * 读取配置文件
     * @param path 文件路径
     * @param clazz 配置类，继承自 {@link TomlConfig}
     * @return 指定的配置类实例
     */
    public static <T> T readConfig(Path path, Class<T> clazz) {
        return readConfig(path, clazz, false);
    }

    /**
     * 读取配置文件
     * @param path 文件路径
     * @param clazz 配置类，继承自 {@link TomlConfig}
     * @param create 如果文件不存在，是否创建默认配置文件
     * @return 指定的配置类实例。当文件不存在时，返回默认配置实例。
     */
    public static <T> T readConfig(String path, Class<T> clazz, boolean create) {
        return readConfig(Paths.get(path), clazz, create);
    }

    /**
     * 读取配置文件
     * @param file 文件
     * @param clazz 配置类，继承自 {@link TomlConfig}
     * @param create 如果文件不存在，是否创建默认配置文件
     * @return 指定的配置类实例。当文件不存在时，返回默认配置实例。
     */
    public static <T> T readConfig(File file, Class<T> clazz, boolean create) {
        return readConfig(file.toPath(), clazz, create);
    }

    /**
     * 读取配置文件
     * @param path 文件路径
     * @param clazz 配置类，继承自 {@link TomlConfig}
     * @param create 如果文件不存在，是否创建默认配置文件
     * @return 指定的配置类实例。如果文件不存在，返回默认配置实例。
     */
    public static <T> T readConfig(Path path, Class<T> clazz, boolean create) {
        try {
            if (!path.toFile().exists() && create) {
                writeConfig(path, (TomlConfig) parseConfig(null, clazz));
            }
            TomlParseResult toml = Toml.parse(path);
            return parseConfig(toml, clazz);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 解析配置类
     * @param source 配置源
     * @param clazz 配置类
     * @return 通过配置源构造的配置类实例。如果配置源为null，构造无参配置类实例返回
     */
    @NotNull
    public static <T> T parseConfig(TomlTable source, Class<T> clazz) {
        if (!TomlConfig.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Clazz is not a TomlConfig");
        }
        try {
            if (source == null) {
                Constructor<T> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            } else {
                Constructor<T> constructor = clazz.getDeclaredConstructor(TomlTable.class);
                constructor.setAccessible(true);
                return constructor.newInstance(source);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 写入配置文件
     * @param path 文件路径
     * @param config 配置类实例
     * @return 被写入的文件
     */
    public static File writeConfig(String path, TomlConfig config) {
        return writeConfig(new File(path), config, KeyGenerator.HYPHENATED);
    }

    /**
     * 写入配置文件
     * @param path 文件路径
     * @param config 配置类实例
     * @return 被写入的文件
     */
    public static File writeConfig(Path path, TomlConfig config) {
        return writeConfig(path.toFile(), config, KeyGenerator.HYPHENATED);
    }

    /**
     * 写入配置文件
     * @param file 文件
     * @param config 配置类实例
     * @return 被写入的文件
     */
    public static File writeConfig(File file, TomlConfig config) {
        return writeConfig(file, config, KeyGenerator.HYPHENATED);
    }

    /**
     * 写入配置文件
     * @param path 文件路径
     * @param config 配置类实例
     * @param keyGenerator key生成方式，见 {@link KeyGenerator}
     * @return 被写入的文件
     */
    public static File writeConfig(String path, TomlConfig config, KeyGenerator keyGenerator) {
        return writeConfig(new File(path), config, keyGenerator);
    }

    /**
     * 写入配置文件
     * @param path 文件路径
     * @param config 配置类实例
     * @param keyGenerator key生成方式，见 {@link KeyGenerator}
     * @return 被写入的文件
     */
    public static File writeConfig(Path path, TomlConfig config, KeyGenerator keyGenerator) {
        return writeConfig(path.toFile(), config, keyGenerator);
    }

    /**
     * 写入配置文件
     * @param file 文件
     * @param config 配置类实例
     * @param keyGenerator key生成方式，见 {@link KeyGenerator}
     * @return 被写入的文件
     */
    public static File writeConfig(File file, TomlConfig config, KeyGenerator keyGenerator) {
        return writeStringToFile(file, config.toToml(keyGenerator));
    }

    /**
     * 将字符串写入到文件
     * @param file 文件
     * @param str 字符串
     * @return 被写入的文件
     */
    public static File writeStringToFile(File file, String str) {
        return writeStringToFile(file, str, false);
    }

    /**
     * 将字符串写入到文件
     * @param file 写入的文件
     * @param str 字符串
     * @param append true，以追加形式写入；false，以覆盖形式写入
     * @return 被写入的文件
     */
    public static File writeStringToFile(File file, String str, boolean append) {
        try {
            File presentFile = checkCreateFile(file);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(presentFile, append))) {
                writer.write(new String(str.getBytes(), StandardCharsets.UTF_8));
                writer.flush();
            }
            return presentFile;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 获取值
     */
    @Nullable
    public static Object getValue(TomlTable table, String key) {
        if (table == null) {
            return null;
        }
        Object value = table.get(key);
        return value != null ? concreteTomljValue(value) : null;
    }

    @Nullable
    public static <T> T getTomlConfig(TomlTable table, String key, Class<T> clazz) {
        return getTomlConfig(table, key, clazz, () -> null);
    }

    public static <T> T getTomlConfig(TomlTable table, String key, Class<T> clazz, Supplier<T> defaultValue) {
        if (table == null) {
            return null;
        }
        TomlTable value = table.getTable(key);
        return value != null ? (T) parseConfig(value, clazz) : defaultValue.get();
    }

    @Nullable
    public static TomlTable getTomlTable(TomlTable table, String key) {
        if (table == null) {
            return null;
        }
        return table.getTable(key);
    }

    @Nullable
    public static TomlArray getTomlArray(TomlTable table, String key) {
        if (table == null) {
            return null;
        }
        return table.getArray(key);
    }

    @Nullable
    public static Integer getInt(TomlTable table, String key) {
        if (table == null) {
            return null;
        }
        Long value = table.getLong(key);
        return value != null ? Math.toIntExact(value) : null;
    }

    public static int getInt(TomlTable table, String key, int defaultValue) {
        if (table == null) {
            return defaultValue;
        }
        Long value = table.getLong(key);
        return value != null ? Math.toIntExact(value) : defaultValue;
    }

    public static int getInt(TomlTable table, String key, IntSupplier defaultValue) {
        if (table == null) {
            return defaultValue.getAsInt();
        }
        Long value = table.getLong(key);
        return value != null ? Math.toIntExact(value) : defaultValue.getAsInt();
    }

    @Nullable
    public static Long getLong(TomlTable table, String key) {
        return table.getLong(key);
    }

    public static long getLong(TomlTable table, String key, long defaultValue) {
        return table.getLong(key, () -> defaultValue);
    }

    public static long getLong(TomlTable table, String key, LongSupplier defaultValue) {
        return table.getLong(key, defaultValue);
    }

    @Nullable
    public static Double getDouble(TomlTable table, String key) {
        return table.getDouble(key);
    }

    public static double getDouble(TomlTable table, String key, double defaultValue) {
        return table.getDouble(key, () -> defaultValue);
    }

    public static double getDouble(TomlTable table, String key, DoubleSupplier defaultValue) {
        return table.getDouble(key, defaultValue);
    }

    @Nullable
    public static Boolean getBoolean(TomlTable table, String key) {
        return table.getBoolean(key);
    }

    public static boolean getBoolean(TomlTable table, String key, boolean defaultValue) {
        return table.getBoolean(key, () -> defaultValue);
    }

    public static boolean getBoolean(TomlTable table, String key, BooleanSupplier defaultValue) {
        return table.getBoolean(key, defaultValue);
    }

    @Nullable
    public static String getString(TomlTable table, String key) {
        return table.getString(key);
    }

    public static String getString(TomlTable table, String key, String defaultValue) {
        return table.getString(key, () -> defaultValue);
    }

    public static String getString(TomlTable table, String key, Supplier<String> defaultValue) {
        return table.getString(key, defaultValue);
    }

    @Nullable
    public static LocalDate getLocalDate(TomlTable table, String key) {
        return table.getLocalDate(key);
    }

    public static LocalDate getLocalDate(TomlTable table, String key, LocalDate defaultValue) {
        return table.getLocalDate(key, () -> defaultValue);
    }

    public static LocalDate getLocalDate(TomlTable table, String key, Supplier<LocalDate> defaultValue) {
        return table.getLocalDate(key, defaultValue);
    }

    @Nullable
    public static LocalDateTime getLocalDateTime(TomlTable table, String key) {
        return table.getLocalDateTime(key);
    }

    public static LocalDateTime getLocalDateTime(TomlTable table, String key, LocalDateTime defaultValue) {
        return table.getLocalDateTime(key, () -> defaultValue);
    }

    public static LocalDateTime getLocalDateTime(TomlTable table, String key, Supplier<LocalDateTime> defaultValue) {
        return table.getLocalDateTime(key, defaultValue);
    }

    public static <T> T getEnum(TomlTable table, String key, Class<T> clazz) {
        return getEnum(table, key, clazz, (T) null);
    }

    public static <T> T getEnum(TomlTable table, String key, Class<T> clazz, String defaultValue) {
        try {
            Method valueOf = clazz.getDeclaredMethod("valueOf", String.class);
            valueOf.setAccessible(true);
            try {
                Object defaultEnum = valueOf.invoke(null, defaultValue);
                return getEnum(table, key, clazz, (T) defaultEnum);
            } catch (Exception e) {
                throw new TomlInvalidTypeException("Default value of '" + defaultValue + "' is not a " + clazz.getSimpleName(), e);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static <T> T getEnum(TomlTable table, String key, Class<T> clazz, T defaultValue) {
        String value = table.getString(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            Method valueOf = clazz.getDeclaredMethod("valueOf", String.class);
            valueOf.setAccessible(true);
            Object result;
            try {
                result = valueOf.invoke(null, value);
            } catch (Exception e) {
                throw new TomlInvalidTypeException("Value of '" + value + "' is not a " + clazz.getSimpleName(), e);
            }
            return (T) result;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 获取N维数组
     * <blockquote>
     * <pre>
     * int[] array = getArray(table, "intArray", int[].class);
     * int[][][] array = getArray(table, "intTMultArray", int[][][].class);
     * </pre>
     * </blockquote>
     *
     * @param table 表
     * @param key   键
     * @param clazz 数组类型：<code>int[].class long[].class</code>
     * @return 数组或null
     */
    @Nullable
    public static <T> T getArray(TomlTable table, String key, Class<T> clazz) {
        return getArray(table, key, clazz, () -> null);
    }

    /**
     * 获取N维数组
     * <blockquote>
     * <pre>
     * int[] array = getArray(table, "intArray", int[].class, new int[]{0, 1, 2});
     *
     * int[][][] array = getArray(table, "intMultArray", int[][][].class, () -> new int[][][]{...});
     * </pre>
     * </blockquote>
     *
     * @param table        表
     * @param key          键
     * @param clazz        数组类型：<code>int[].class long[].class</code>
     * @param defaultValue 默认值
     * @return 数组或默认值
     */
    public static <T> T getArray(TomlTable table, String key, Class<T> clazz, Supplier<T> defaultValue) {
        Object value = table.get(key);
        if (value == null) {
            return defaultValue.get();
        }
        if (!(value instanceof TomlArray array)) {
            throw new TomlInvalidTypeException(
                    "Value of '" + key + "' is a " + value.getClass().getSimpleName());
        }
        return tomlArrayToArray(clazz, array);
    }

    public static int[] getIntArray(TomlTable table, String key) {
        return getIntArray(table, key, () -> null);
    }

    public static int[] getIntArray(TomlTable table, String key, Supplier<int[]> defaultValue) {
        return getArray(table, key, int[].class, defaultValue);
    }

    public static long[] getLongArray(TomlTable table, String key) {
        return getLongArray(table, key, () -> null);
    }

    public static long[] getLongArray(TomlTable table, String key, Supplier<long[]> defaultValue) {
        return getArray(table, key, long[].class, defaultValue);
    }

    public static double[] getDoubleArray(TomlTable table, String key) {
        return getDoubleArray(table, key, () -> null);
    }

    public static double[] getDoubleArray(TomlTable table, String key, Supplier<double[]> defaultValue) {
        return getArray(table, key, double[].class, defaultValue);
    }

    public static boolean[] getBooleanArray(TomlTable table, String key) {
        return getBooleanArray(table, key, () -> null);
    }

    public static boolean[] getBooleanArray(TomlTable table, String key, Supplier<boolean[]> defaultValue) {
        return getArray(table, key, boolean[].class, defaultValue);
    }

    public static String[] getStringArray(TomlTable table, String key) {
        return getStringArray(table, key, () -> null);
    }

    public static String[] getStringArray(TomlTable table, String key, Supplier<String[]> defaultValue) {
        return getArray(table, key, String[].class, defaultValue);
    }

    /**
     * 获取List
     * @param table 表
     * @param key 键
     * @param arrayClazz 数组类型：<code>int[].class long[].class</code>
     * @return ArrayList实例或null
     */
    public static List<?> getList(TomlTable table, String key, Class<?> arrayClazz) {
        return getList(table, key, arrayClazz, () -> null);
    }

    /**
     * 获取List
     * @param table 表
     * @param key 键
     * @param arrayClazz 数组类型：<code>int[].class long[].class</code>
     * @param defaultValue 默认值
     * @return ArrayList实例或默认值
     */
    public static List<?> getList(TomlTable table, String key, Class<?> arrayClazz, Supplier<List<?>> defaultValue) {
        Object array = getArray(table, key, arrayClazz);
        return array != null ? TomlHelper.arrayToList(array) : defaultValue.get();
    }

    public static List<Integer> getIntegerList(TomlTable table, String key) {
        return getIntegerList(table, key, () -> null);
    }

    public static List<Integer> getIntegerList(TomlTable table, String key, Supplier<List<Integer>> defaultValue) {
        int[] array = getIntArray(table, key);
        return array != null ? (List<Integer>) TomlHelper.arrayToList(array) : defaultValue.get();
    }

    public static List<Long> getLongList(TomlTable table, String key) {
        return getLongList(table, key, () -> null);
    }

    public static List<Long> getLongList(TomlTable table, String key, Supplier<List<Long>> defaultValue) {
        long[] array = getLongArray(table, key);
        return array != null ? (List<Long>) TomlHelper.arrayToList(array) : defaultValue.get();
    }

    public static List<Double> getDoubleList(TomlTable table, String key) {
        return getDoubleList(table, key, () -> null);
    }

    public static List<Double> getDoubleList(TomlTable table, String key, Supplier<List<Double>> defaultValue) {
        double[] array = getDoubleArray(table, key);
        return array != null ? (List<Double>) TomlHelper.arrayToList(array) : defaultValue.get();
    }

    public static List<Boolean> getBooleanList(TomlTable table, String key) {
        return getBooleanList(table, key, () -> null);
    }

    public static List<Boolean> getBooleanList(TomlTable table, String key, Supplier<List<Boolean>> defaultValue) {
        double[] array = getDoubleArray(table, key);
        return array != null ? (List<Boolean>) TomlHelper.arrayToList(array) : defaultValue.get();
    }

    public static List<String> getStringList(TomlTable table, String key) {
        return getStringList(table, key, () -> null);
    }

    public static List<String> getStringList(TomlTable table, String key, Supplier<List<String>> defaultValue) {
        String[] array = getStringArray(table, key);
        return array != null ? (List<String>) TomlHelper.arrayToList(array) : defaultValue.get();
    }

    public static Object getRequiredValue(TomlTable table, String key) {
        Object value = getValue(table, key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static <T> T getRequiredTomlConfig(TomlTable table, String key, Class<T> clazz) {
        T value = getTomlConfig(table, key, clazz);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static TomlTable getRequiredTomlTable(TomlTable table, String key) {
        TomlTable value = getTomlTable(table, key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static TomlArray getRequiredTomlArray(TomlTable table, String key) {
        TomlArray value = table.getArray(key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static int getRequiredInt(TomlTable table, String key) {
        Long value = table.getLong(key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return Math.toIntExact(value);
    }

    public static long getRequiredLong(TomlTable table, String key) {
        Long value = table.getLong(key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static double getRequiredDouble(TomlTable table, String key) {
        Double value = table.getDouble(key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static boolean getRequiredBoolean(TomlTable table, String key) {
        Boolean value = table.getBoolean(key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static String getRequiredString(TomlTable table, String key) {
        String value = table.getString(key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static LocalDate getRequiredLocalDate(TomlTable table, String key) {
        LocalDate value = getLocalDate(table, key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static LocalDateTime getRequiredLocalDateTime(TomlTable table, String key) {
        LocalDateTime value = getLocalDateTime(table, key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static <T> T getRequiredEnum(TomlTable table, String key, Class<T> clazz) {
        T value = getEnum(table, key, clazz);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static <T> T getRequiredArray(TomlTable table, String key, Class<T> clazz) {
        T value = getArray(table, key, clazz);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static int[] getRequiredIntArray(TomlTable table, String key) {
        int[] value = getIntArray(table, key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static long[] getRequiredLongArray(TomlTable table, String key) {
        long[] value = getLongArray(table, key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static double[] getRequiredDoubleArray(TomlTable table, String key) {
        double[] value = getDoubleArray(table, key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static boolean[] getRequiredBooleanArray(TomlTable table, String key) {
        boolean[] value = getBooleanArray(table, key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static String[] getRequiredStringArray(TomlTable table, String key) {
        String[] value = getStringArray(table, key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static List<?> getRequiredList(TomlTable table, String key, Class<?> arrayClazz) {
        List<?> value = getList(table, key, arrayClazz);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static List<Integer> getRequiredIntegerList(TomlTable table, String key) {
        List<Integer> value = getIntegerList(table, key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static List<Long> getRequiredLongList(TomlTable table, String key) {
        List<Long> value = getLongList(table, key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static List<Double> getRequiredDoubleList(TomlTable table, String key) {
        List<Double> value = getDoubleList(table, key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static List<Boolean> getRequiredBooleanList(TomlTable table, String key) {
        List<Boolean> value = getBooleanList(table, key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    public static List<String> getRequiredStringList(TomlTable table, String key) {
        List<String> value = getStringList(table, key);
        if (value == null) {
            throw new IllegalStateException("Required key '" + key + "' not found");
        }
        return value;
    }

    private static File checkCreateFile(File file) throws IOException {
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.exists()) {
                parentFile.mkdirs();
            }
            file.createNewFile();
        }
        return file;
    }

    private static <T> T tomlArrayToArray(Class<T> clazz, TomlArray array) {
        Class<?> componentType = clazz.getComponentType();
        T value = (T) Array.newInstance(componentType, array.size());

        for (int i = 0; i < array.size(); i++) {
            Object obj = array.get(i);

            if (obj instanceof TomlArray arr) {
                if (componentType == null || !componentType.isArray()) {
                    throw new IllegalArgumentException("Target array type dimensions does not match TomlArray value dimensions");
                }
                Array.set(value, i, tomlArrayToArray(componentType, arr));
            } else {
                Array.set(value, i, concreteTomljValue(componentType, obj));
            }
        }
        return value;
    }

    private static Object concreteTomljValue(Object value) {
        return concreteTomljValue(null, value);
    }

    private static Object concreteTomljValue(Class<?> configType, Object value) {
        if (value == null) {
            return null;
        }

        Class<?> clazz = value.getClass();

        if (value instanceof Long l) {
            if (l <= Integer.MAX_VALUE && l >= Integer.MIN_VALUE) {
                return Math.toIntExact(l);
            }
            return l;
        }

        if (value instanceof TomlTable table) {
            if (configType != null && TomlConfig.class.isAssignableFrom(configType)) {
                return parseConfig(table, configType);
            } else {
                return table;
            }
        }

        if (value instanceof TomlArray tomlArray) {
            Class<?> arrayClazz = tomlArrayToArrayType(tomlArray);
            if (arrayClazz == TomlArray.class) {
                return tomlArray;
            }

            Object array = Array.newInstance(arrayClazz.getComponentType(), tomlArray.size());
            for (int i = 0; i < tomlArray.size(); i++) {
                Array.set(array, i, concreteTomljValue(tomlArray.get(i)));
            }
            return array;
        }
        return value;
    }

    private static Class<?> tomlArrayToArrayType(TomlArray tomlArray) {
        Class<?>[] arrayType = {null};
        int[] dimensions = {0};
        tomlArrayToArrayType(tomlArray, arrayType, dimensions);

        if (arrayType[0] == null || arrayType[0] == TomlArray.class) {
            return TomlArray.class;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[".repeat(dimensions[0]));
        if (arrayType[0] == int.class) {
            sb.append("I");
        } else if (arrayType[0] == long.class) {
            sb.append("J");
        } else if (arrayType[0] == double.class) {
            sb.append("D");
        } else if (arrayType[0] == boolean.class) {
            sb.append("Z");
        } else if (!arrayType[0].isPrimitive()) {
            sb.append("L").append(arrayType[0].getName()).append(";");
        } else {
            throw new IllegalStateException("Unsupported type for " + arrayType[0].getName());
        }

        try {
            return Class.forName(sb.toString());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void tomlArrayToArrayType(TomlArray tomlArray, Class<?>[] arrayType, int[] dimensions) {
        dimensions[0]++;
        for (int i = 0; i < tomlArray.size(); i++) {
            Object element = tomlArray.get(i);
            Class<?> elementType;

            if (arrayType[0] == TomlArray.class) {
                return;
            }

            if (element instanceof TomlArray array) {
                if (i > 0) {
                    dimensions[0]--;
                }
                tomlArrayToArrayType(array, arrayType, dimensions);
                continue;
            } else if (element instanceof Long longValue) {
                if (longValue <= Integer.MAX_VALUE && longValue >= Integer.MIN_VALUE) {
                    elementType = int.class;
                } else {
                    elementType = long.class;
                }
            } else if (element instanceof Double) {
                elementType = double.class;
            } else if (element instanceof Boolean) {
                elementType = boolean.class;
            } else {
                elementType = element.getClass();
            }

            if (arrayType[0] == null) {
                arrayType[0] = elementType;
                continue;
            }

            if (elementType == int.class && arrayType[0] == long.class) {
                elementType = long.class;
            } else if (elementType == long.class && arrayType[0] == int.class) {
                arrayType[0] = long.class;
            }

            if (elementType != arrayType[0]) {
                arrayType[0] = TomlArray.class;
            }
        }
    }
}
