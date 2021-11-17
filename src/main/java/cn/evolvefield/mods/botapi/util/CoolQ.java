package cn.evolvefield.mods.botapi.util;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.message.SendMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoolQ {
    public static final String LINE_CHAR = "\n";

    public static String clearImage(String str) {
        String regex = "\\[CQ:image,[(\\s\\S)]*]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String replaceAt(String origin) {
        String regex = "\\[CQ:at,qq=(\\d*)]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(origin);

        try {
            while (m.find()) {
                String username = SendMessage.getUsernameFromInfo(
                        SendMessage.getProfile(BotApi.config.getCommon().getGroupId(), Long.parseLong(m.group(1)))
                );

                origin = m.replaceFirst("@" + username);
                m = p.matcher(origin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return origin;
    }

    public static String replaceCharset(String origin) {
        return origin.replace("&#91;", "[").replace("&#93;", "]");
    }
}
