package cn.evolvefield.onebot.client.util;

import cn.evolvefield.onebot.sdk.event.Event;
import cn.evolvefield.onebot.sdk.map.MessageMap;
import cn.evolvefield.onebot.sdk.util.json.JsonsObject;
import com.google.gson.JsonObject;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/14 17:03
 * Version: 1.0
 */
public class ListenerUtils {
    public static Class<? extends Event> getMessageType(String message) {
        return getMessageType(new JsonsObject(message).get());
    }

    /**
     * 获取消息对应的实体类型
     *
     * @param jsonObject json
     * @return
     */
    public static Class<? extends Event> getMessageType(JsonObject jsonObject) {
        String type = null;
        JsonsObject object = new JsonsObject(jsonObject);
        String postType = object.optString("post_type");
        if ("message".equals(postType)) {
            type = "wholeMessage";
            //消息类型
            String messageType = object.optString("message_type");
            if ("group".equals(messageType)) {
                //群聊消息类型
                type = "groupMessage";
            } else if ("private".equals(messageType)) {
                //私聊消息类型
                type = "privateMessage";
            } else if ("guild".equals(messageType)){
                //频道消息，暂不支持私信
                type = "guildMessage";
            }
        } else if ("request".equals(postType)) {
            //请求类型
            type = object.optString("request_type");
        } else if ("notice".equals(postType)) {
            //通知类型
            type = object.optString("notice_type");
        }
        return MessageMap.messageMap.get(type);
    }
}
