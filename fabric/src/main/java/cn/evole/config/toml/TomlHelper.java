package cn.evole.config.toml;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

class TomlHelper {
    public static List<?> arrayToList(Object array) {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < Array.getLength(array); i++) {
            Object o = Array.get(array, i);

            if (o.getClass().isArray()) {
                list.add(arrayToList(o));
            } else {
                list.add(o);
            }
        }
        return list;
    }
}
