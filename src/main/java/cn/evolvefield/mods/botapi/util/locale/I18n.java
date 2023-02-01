package cn.evolvefield.mods.botapi.util.locale;

import java.util.IllegalFormatException;

public class I18n {
    private static volatile Translation language = Translation.getInstance();

    private I18n() {
    }

    static void setLanguage(Translation language) {
        I18n.language = language;
    }

    public static String get(String string, Object... objects) {
        String string2 = language.getOrDefault(string);

        try {
            return String.format(string2, objects);
        } catch (IllegalFormatException var4) {
            return "Format error: " + string2;
        }
    }

    public static boolean exists(String string) {
        return language.has(string);
    }
}
