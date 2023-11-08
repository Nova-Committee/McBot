package cn.evole.config.toml.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectUtil {
    /**
     * 获取类和其父类的所有字段声明
     * @param clazz 要获取的类
     * @return 包含父类的所有字段声明
     */
    public static Field[] getFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        List<Class<?>> classes = new ArrayList<>();
        while (clazz != null && clazz != Object.class) {
            classes.add(clazz);
            clazz = clazz.getSuperclass();
        }
        for (int i = classes.size() - 1; i >= 0; i--) {
            Class<?> c = classes.get(i);
            fieldList.addAll(Arrays.stream(c.getDeclaredFields()).toList());
        }
        return fieldList.toArray(Field[]::new);
    }
}
