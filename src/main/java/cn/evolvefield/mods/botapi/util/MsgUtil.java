package cn.evolvefield.mods.botapi.util;

import cn.evolvefield.mods.botapi.api.message.MiraiMessage;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
        JsonArray array = new JsonArray();

        for (MiraiMessage mm : ListMM) {
            JsonObject m = new JsonObject();

            System.out.println(mm.getType());

            switch (mm.getType()) {
                case "Plain" -> {
                    m.addProperty("type", "Plain");
                    m.addProperty("text", mm.getMessage());
                }
                case "Image" -> {
                    m.addProperty("type", "Image");
                    m.addProperty("url", mm.getUrl());
                    m.addProperty("path", mm.getPath());
                    m.addProperty("base64", mm.getBase64());
                }
                case "At" -> {
                    m.addProperty("type", "At");
                    m.addProperty("target", mm.getTarget());
                }
            }

            array.add(m);
        }

        return array.toString();
    }

    public static JsonArray getMessage(String message) {
        JsonObject msg = new JsonObject();
        msg.addProperty("type", "Plain");
        msg.addProperty("text", message);

        JsonArray array = new JsonArray();
        array.add(msg);

        return array;
    }

    public static JsonArray getMessage(List<String> message) {
        JsonObject msg = new JsonObject();
        msg.addProperty("type", "Plain");
        msg.addProperty("text", setListMessage(message));

        JsonArray array = new JsonArray();
        array.add(msg);

        return array;
    }
}
