package cn.evole.config.toml.util;

public class NamingUtil {
    public static String upperCamelCaseToConstant(String str) {
        StringBuilder constant = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if (Character.isUpperCase(ch)) {
                if (i > 0) {
                    constant.append('_');
                }
                constant.append(ch);
            } else {
                constant.append(Character.toUpperCase(ch));
            }
        }

        return constant.toString();
    }

    public static String lowerCamelCaseToHyphenated(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (Character.isUpperCase(ch)) {
                result.append('-').append(Character.toLowerCase(ch));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static String upperCamelCaseToHyphenated(String str) {
        return str.replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase();
    }

    public static String lowerCamelCaseToUnderline(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (Character.isUpperCase(ch)) {
                result.append('_').append(Character.toLowerCase(ch));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }
}
