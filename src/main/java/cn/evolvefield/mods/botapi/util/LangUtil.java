package cn.evolvefield.mods.botapi.util;

import net.minecraft.util.StatCollector;

import java.util.IllegalFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/6 11:56
 * Version: 1.0
 */
public class LangUtil {
    private static Pattern pluralPattern = Pattern.compile("\\[\\[(.*)\\|\\|(.*)\\]\\]");

    public static String translate(String id) {
        return StatCollector.translateToLocal(id).replace("\\n", "\n");
    }

    public static String translate(String id, Object... args) {
        return translate(false, id, args);
    }

    public static String translate(boolean plural, String id, Object... args) {
        return format(translate(id), plural, args);
    }

    public static String format(String s, boolean plural, Object... args) {
        if (s == null) return s;
        try {
            Matcher matcher = pluralPattern.matcher(s);
            while (matcher.find()) {
                s = matcher.replaceFirst(matcher.group(plural ? 2 : 1));
                matcher = pluralPattern.matcher(s);
            }
            return String.format(s, args);
        } catch (IllegalFormatException e) {
            return "Format Exception: " + s;
        }
    }
}
