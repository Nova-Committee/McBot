package cn.evolvefield.mods.botapi.core.bot;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.events.GroupMessageEvent;
import cn.evolvefield.mods.botapi.api.events.PrivateMessageEvent;
import cn.evolvefield.mods.botapi.api.message.MiraiMessage;
import cn.evolvefield.mods.botapi.init.callbacks.BotEvents;
import cn.evolvefield.mods.botapi.util.JsonsObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/18 18:57
 * Version: 1.0
 */
public class MiraiBot {
    private String Json;//消息原本json文本

    private String SessionKey; //机器人发送消息所需的SessionKey
    private String type;//机器人收到事件类型

    private long group_id;//消息群号
    private String group_name;//群名称
    private String nickname;//发送人名称
    private String memberName;//发送人群名片
    private String permission;//发送人群权限

    private long user_id;//发送人qq、撤回消息qq、事件触发qq

    private List<MiraiMessage> raw_message;//收到消息
    //消息类型
    private String msgType;//消息类型

    private JsonArray origin;//被引用回复的原消息的消息链对象

    public MiraiBot(String jsonStr, JsonsObject json) {
        this.Json = jsonStr;//消息原本json文本

        if (!json.has("data")) {
            return;
        }

        if (Json.contains("session")) {
            SessionKey = new JsonsObject(json.optJSONObject("data")).optString("session");
            BotData.setSessionKey(this.SessionKey);
        }


        JsonsObject data = new JsonsObject(json.optJSONObject("data"));

        if (data.has("type")) {
            this.type = data.optString("type");
        } else {
            return;
        }
        //私聊消息
        if (type.equals("FriendMessage")) {
            //聊天消息
            JsonArray jsonArray = data.optJSONArray("messageChain");


            raw_message = getMessageList(jsonArray);

            //发送人信息
            JsonsObject sender = new JsonsObject(data.optJSONObject("sender"));
            user_id = sender.optLong("id");
            nickname = sender.optString("nickname");

            //触发好友事件
            PrivateMessageEvent event = new PrivateMessageEvent(Json, raw_message, user_id, nickname);
            BotEvents.PRIVATE_MSG_EVENT.invoker().onPrivateMsg(event);
        }

        //临时会话
        if (type.equals("TempMessage ")) {
            //聊天消息
            JsonArray jsonArray = data.optJSONArray("messageChain");
            raw_message = getMessageList(jsonArray);

            //发送人信息
            JsonsObject sender = new JsonsObject(data.optJSONObject("sender"));
            user_id = sender.optLong("id");
            memberName = sender.optString("memberName");

            //群信息
            JsonsObject group = new JsonsObject(data.optJSONObject("group"));
            group_id = group.optLong("id");
            group_name = group.optString("name");

            //触发私聊事件
            PrivateMessageEvent event = new PrivateMessageEvent(Json, raw_message, user_id, memberName, group_id, group_name);
            BotEvents.PRIVATE_MSG_EVENT.invoker().onPrivateMsg(event);
        }


        //群聊消息
        if (type.equals("GroupMessage")) {
            //聊天消息
            JsonArray jsonArray = data.optJSONArray("messageChain");
            raw_message = getMessageList(jsonArray);

            //发送人信息
            JsonsObject sender = new JsonsObject(data.optJSONObject("sender"));
            user_id = sender.optLong("id");
            memberName = sender.optString("memberName");
            permission = sender.optString("permission");
            //群信息
            JsonsObject group = new JsonsObject(data.optJSONObject("group"));
            group_id = group.optLong("id");
            group_name = group.optString("name");

            //触发群聊事件
            GroupMessageEvent event = new GroupMessageEvent(Json, raw_message, user_id, permission, memberName, group_id, group_name);
            BotEvents.GROUP_MSG_EVENT.invoker().onGroupMsg(event);


        }
    }


    public List<MiraiMessage> getMessageList(JsonArray json) {
        List<MiraiMessage> message = new ArrayList<>();
        for (Object sz : json) {
            MiraiMessage mm = getMessage(new JsonsObject((JsonObject) (sz)));

            if (((JsonObject) sz).has("origin")) {
                origin = new JsonsObject((JsonObject) (sz)).optJSONArray("origin");

                List<MiraiMessage> originList = new ArrayList<>();
                for (Object ol : origin) {
                    originList.add(getMessage(new JsonsObject((JsonObject) (sz))));
                }
                mm.setOrigin(originList);
            }
            message.add(mm);
        }


        if (BotApi.config.getCommon().isDebuggable()) {
            System.out.println("▌ §cMirai消息解析 §6┈━═☆");
            for (MiraiMessage mm : message) {
                mm.deBug();
            }
        }

        return message;
    }

    public MiraiMessage getMessage(JsonsObject json) {
        this.msgType = json.optString("type", "");

        MiraiMessage mm = new MiraiMessage();

        mm.setJson(json.toString());
        mm.setType(msgType);

        //消息的数据Source类型永远为chain的第一个元素
        switch (msgType) {
            case "Source" -> {
                mm.setTime(json.optInt("time"));
                mm.setId(json.optInt("id"));
            }
            //普通消息
            case "Plain" -> mm.setText(json.optString("text"));

            //图片消息
            case "Image" -> { //图片消息
                mm.setImageId(json.optString("imageId"));
                mm.setUrl(json.optString("url"));
                mm.setPath(json.optString("path"));
                mm.setBase64(json.optString("base64"));
            }
            //引用消息
            case "Quote" -> {
                mm.setId(json.optInt("id"));
                mm.setGroupId(json.optLong("groupId"));
                mm.setSenderId(json.optLong("senderId"));
                mm.setTargetId(json.optLong("targetId"));
            }
            //艾特消息
            case "At" -> {
                mm.setTarget(json.optLong("target"));
                mm.setDisplay(json.optString("dispaly"));
            }
        }


        return mm;
    }
}
