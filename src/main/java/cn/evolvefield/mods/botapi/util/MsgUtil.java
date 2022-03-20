package cn.evolvefield.mods.botapi.util;

import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/18 21:22
 * Version: 1.0
 */
public class MsgUtil {
    public static String setListMessage(List<String> msg) {
        if (msg.size() <= 1) {
            return msg.get(0);
        }
        String Message = null;
        for (String a : msg) {
            if (Message == null) {
                Message = a;
                continue;
            }
            Message = Message + "\n" + a;
        }
        return Message;
    }
}
