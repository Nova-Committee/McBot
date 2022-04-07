package cn.evolvefield.mods.botapi.core.bot;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.events.GroupMessageEvent;
import cn.evolvefield.mods.botapi.api.events.PrivateMessageEvent;
import cn.evolvefield.mods.botapi.api.message.MiraiMessage;
import cn.evolvefield.mods.botapi.util.json.JSONArray;
import cn.evolvefield.mods.botapi.util.json.JSONObject;
import net.minecraftforge.common.MinecraftForge;

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

    private JSONArray origin;//被引用回复的原消息的消息链对象

    public MiraiBot(String jsonStr, JSONObject json) {
        this.Json = jsonStr;//消息原本json文本

        if (!json.has("data")) {
            return;
        }

        if (Json.contains("session")) {
            SessionKey = json.getJSONObject("data").getString("session");
            BotData.setSessionKey(this.SessionKey);
        }


        JSONObject data = json.getJSONObject("data");

        if (data.has("type")) {
            this.type = data.getString("type");
        } else {
            return;
        }
        //私聊消息
        if (type.equals("FriendMessage")) {
            //聊天消息
            JSONArray jsonArray = data.getJSONArray("messageChain");


            raw_message = getMessageList(jsonArray);

            //发送人信息
            JSONObject sender = data.getJSONObject("sender");
            user_id = sender.getLong("id");
            nickname = sender.getString("nickname");

            //触发好友事件
            PrivateMessageEvent event = new PrivateMessageEvent(Json, raw_message, user_id, nickname);
            MinecraftForge.EVENT_BUS.post(event);
        }

        //临时会话
        if (type.equals("TempMessage ")) {
            //聊天消息
            JSONArray jsonArray = data.getJSONArray("messageChain");
            raw_message = getMessageList(jsonArray);

            //发送人信息
            JSONObject sender = data.getJSONObject("sender");
            user_id = sender.getLong("id");
            memberName = sender.getString("memberName");

            //群信息
            JSONObject group = sender.getJSONObject("group");
            group_id = group.getLong("id");
            group_name = group.getString("name");

            //触发私聊事件
            PrivateMessageEvent event = new PrivateMessageEvent(Json, raw_message, user_id, memberName, group_id, group_name);
            MinecraftForge.EVENT_BUS.post(event);
        }


        //群聊消息
        if (type.equals("GroupMessage")) {
            //聊天消息
            JSONArray jsonArray = data.getJSONArray("messageChain");
            raw_message = getMessageList(jsonArray);

            //发送人信息
            JSONObject sender = data.getJSONObject("sender");
            user_id = sender.getLong("id");
            memberName = sender.getString("memberName");
            permission = sender.getString("permission");
            //群信息
            JSONObject group = sender.getJSONObject("group");
            group_id = group.getLong("id");
            group_name = group.getString("name");

            //触发群聊事件
            GroupMessageEvent event = new GroupMessageEvent(Json, raw_message, user_id, permission, memberName, group_id, group_name);
            MinecraftForge.EVENT_BUS.post(event);

        }
    }


    public List<MiraiMessage> getMessageList(JSONArray json) {
        List<MiraiMessage> message = new ArrayList<>();
        for (Object sz : json) {
            MiraiMessage mm = getMessage((JSONObject) (sz));

            if (((JSONObject) sz).has("origin")) {
                origin = ((JSONObject) sz).getJSONArray("origin");

                List<MiraiMessage> originList = new ArrayList<>();
                for (Object ol : origin) {
                    originList.add(getMessage(new JSONObject(ol)));
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

    public MiraiMessage getMessage(JSONObject json) {
        this.msgType = json.optString("type", "");

        MiraiMessage mm = new MiraiMessage();

        mm.setJson(json.toString());
        mm.setType(msgType);

        //消息的数据Source类型永远为chain的第一个元素
        switch (msgType) {
            case "Source": {
                mm.setTime(json.getInt("time"));
                mm.setId(json.getInt("id"));
                break;
            }
            //普通消息
            case "Plain": {
                mm.setText(json.getString("text"));
                break;
            }

            //图片消息
            case "Image": { //图片消息
                mm.setImageId(json.getString("imageId"));
                mm.setUrl(json.getString("url"));
                mm.setPath(json.optString("path"));
                mm.setBase64(json.optString("base64"));
                break;
            }
            //引用消息
            case "Quote": {
                mm.setId(json.getInt("id"));
                mm.setGroupId(json.getLong("groupId"));
                mm.setSenderId(json.getLong("senderId"));
                mm.setTargetId(json.getLong("targetId"));
                break;
            }
            //艾特消息
            case "At": {
                mm.setTarget(json.getLong("target"));
                mm.setDisplay(json.getString("dispaly"));
                break;
            }
        }


        return mm;
    }
}
