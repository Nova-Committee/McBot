package cn.evolvefield.mods.botapi.util;

import cn.evolvefield.mods.botapi.api.message.MiraiMessage;
import cn.evolvefield.mods.botapi.util.json.JSONArray;
import cn.evolvefield.mods.botapi.util.json.JSONObject;

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

    public static String toString(List<MiraiMessage> ListMM) {
        JSONArray array = new JSONArray();

        for (MiraiMessage mm : ListMM) {
            JSONObject m = new JSONObject();

            System.out.println(mm.getType());

            if (mm.getType().equals("Plain")) {
                m.put("type", "Plain");
                m.put("text", mm.getMessage());
            } else if (mm.getType().equals("Image")) {
                m.put("type", "Image");
                m.put("url", mm.getUrl());
                m.put("path", mm.getPath());
                m.put("base64", mm.getBase64());
            } else if (mm.getType().equals("At")) {
                m.put("type", "At");
                m.put("target", mm.getTarget());
            }

            array.put(m);
        }

        return array.toString();
    }

    public static JSONArray getMessage(String message) {
        JSONObject msg = new JSONObject();
        msg.put("type", "Plain");
        msg.put("text", message);

        JSONArray array = new JSONArray();
        array.put(msg);

        return array;
    }

    public static JSONArray getMessage(List<String> message) {
        JSONObject msg = new JSONObject();
        msg.put("type", "Plain");
        msg.put("text", setListMessage(message));

        JSONArray array = new JSONArray();
        array.put(msg);

        return array;
    }
}
