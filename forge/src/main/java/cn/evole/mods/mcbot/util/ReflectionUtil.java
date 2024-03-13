package cn.evole.mods.mcbot.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * from i18nupdatemod
 */

public class ReflectionUtil {
    private final Class<?> clazz;
    private Object instance;

    public ReflectionUtil(Class<?> clazz) {
        this.clazz = clazz;
    }

    public ReflectionUtil(Object instance) {
        if (instance == null) {
            throw new IllegalArgumentException("Instance cannot be null");
        }
        this.clazz = instance.getClass();
        this.instance = instance;
    }

    public static ReflectionUtil clazz(String className) throws ClassNotFoundException {
        return new ReflectionUtil(Class.forName(className));
    }

    public static ReflectionUtil clazz(Class<?> clazz) {
        return new ReflectionUtil(clazz);
    }

    public static ReflectionUtil clazz(Object instance) {
        return new ReflectionUtil(instance);
    }

    private ReflectionUtil getField(String field) throws Exception {
        Field field0 = clazz.getDeclaredField(field);
        field0.setAccessible(true);
        return new ReflectionUtil(field0.get(instance));
    }

    private ReflectionUtil invokeMethod(String method) throws Exception {
        Method method1 = clazz.getDeclaredMethod(method);
        method1.setAccessible(true);
        return new ReflectionUtil(method1.invoke(instance));
    }

    /**
     * Get field or invoke method
     *
     * @param fieldOrMethod fieldName or methodName()
     * @return result
     * @throws Exception
     */
    public ReflectionUtil get(String fieldOrMethod) throws Exception {
//        System.out.println("Getting " + fieldOrMethod);
        if (fieldOrMethod.endsWith(")")) {
            return invokeMethod(fieldOrMethod.replace("()", ""));
        } else {
            return getField(fieldOrMethod);
        }
    }

    public Object get() {
        return instance;
    }
}
