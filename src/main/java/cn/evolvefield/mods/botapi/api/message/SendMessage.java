package cn.evolvefield.mods.botapi.api.message;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.core.bot.BotData;
import cn.evolvefield.mods.botapi.core.service.WebSocketService;
import cn.evolvefield.mods.botapi.util.MsgUtil;
import cn.evolvefield.mods.botapi.util.json.JSONArray;
import cn.evolvefield.mods.botapi.util.json.JSONObject;

import java.util.List;


public class SendMessage {

    private static final JSONObject errorObject = new JSONObject("{\"retcode\": 1}");

    public static void ChannelGroup(String guild_id, String channel_id, String message) {
        if (BotApi.config.getCommon().isEnable()) {
            JSONObject data = new JSONObject();
            JSONObject params = new JSONObject();
            if (BotApi.config.getCommon().getFrame().equalsIgnoreCase("cqhttp") && BotApi.config.getCommon().isGuildOn()) {
                data.put("action", "send_guild_channel_msg");
                params.put("guild_id", guild_id);
                params.put("channel_id", channel_id);
                params.put("message", message);
                data.put("params", params);
                WebSocketService.client.send(data.toString());
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("向频道：" + guild_id + "的子频道：" + channel_id + "发送消息" + message);
                }

            }
        }
    }

    public static void ChannelGroup(String guild_id, String channel_id, List<String> message) {
        if (BotApi.config.getCommon().isEnable()) {
            JSONObject data = new JSONObject();
            JSONObject params = new JSONObject();
            if (BotApi.config.getCommon().getFrame().equalsIgnoreCase("cqhttp") && BotApi.config.getCommon().isGuildOn()) {
                data.put("action", "send_guild_channel_msg");
                params.put("guild_id", guild_id);
                params.put("channel_id", channel_id);
                params.put("message", MsgUtil.setListMessage(message));
                data.put("params", params);
                WebSocketService.client.send(data.toString());
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("向频道：" + guild_id + "的子频道：" + channel_id + "发送消息" + message);
                }

            }
        }
    }

    public static void Temp(long user_id, long group_id, String message) {
        if (BotApi.config.getCommon().isEnable()) {
            JSONObject data = new JSONObject();
            JSONObject params = new JSONObject();
            if (BotApi.config.getCommon().getFrame().equalsIgnoreCase("cqhttp")) {
                data.put("action", "send_private_msg");
                params.put("group_id", group_id);
                params.put("user_id", user_id);
                params.put("message", message);
                data.put("params", params);
                WebSocketService.client.send(data.toString());
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("向群：" + group_id + "的用户：" + user_id + "发送临时消息" + message);
                }

            } else if (BotApi.config.getCommon().getFrame().equalsIgnoreCase("mirai")) {

                data.put("sessionKey", BotData.getSessionKey());
                data.put("qq", user_id);
                data.put("group", group_id);
                data.put("messageChain", MsgUtil.getMessage(message));

                JSONObject main = new JSONObject();
                main.put("syncId", "");
                main.put("command", "sendTempMessage");
                main.put("content", data);

                WebSocketService.client.send(main.toString());
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("向群：" + group_id + "的用户：" + user_id + "发送临时消息" + main);
                }
            } else {
                BotApi.LOGGER.warn("§c未找到机器人框架.");
            }
        }

    }

    public static void Group(long group_id, String message) {
        if (BotApi.config.getCommon().isEnable()) {
            JSONObject data = new JSONObject();
            JSONObject params = new JSONObject();
            if (BotApi.config.getCommon().getFrame().equalsIgnoreCase("cqhttp")) {
                data.put("action", "send_group_msg");
                params.put("group_id", group_id);
                params.put("message", message);
                data.put("params", params);
                WebSocketService.client.send(data.toString());
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("向群" + group_id + "发送消息" + message);
                }

            } else if (BotApi.config.getCommon().getFrame().equalsIgnoreCase("mirai")) {

                data.put("sessionKey", BotData.getSessionKey());
                data.put("target", group_id);
                data.put("messageChain", MsgUtil.getMessage(message));

                JSONObject main = new JSONObject();
                main.put("syncId", "");
                main.put("command", "sendGroupMessage");
                main.put("content", data);

                WebSocketService.client.send(main.toString());
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("向群" + group_id + "发送消息" + main);
                }
            } else {
                BotApi.LOGGER.warn("§c未找到机器人框架.");
            }

        }
    }

    public static void Group(long group_id, List<String> message) {
        if (BotApi.config.getCommon().isEnable()) {
            JSONObject data = new JSONObject();
            JSONObject params = new JSONObject();
            JSONArray array = new JSONArray();
            if (BotApi.config.getCommon().getFrame().equalsIgnoreCase("cqhttp")) {
                data.put("action", "send_group_msg");
                params.put("group_id", group_id);
                params.put("message", MsgUtil.setListMessage(message));
                data.put("params", params);
                WebSocketService.client.send(data.toString());
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("向群" + group_id + "发送消息" + data);
                }

            } else if (BotApi.config.getCommon().getFrame().equalsIgnoreCase("mirai")) {

                data.put("sessionKey", BotData.getSessionKey());
                data.put("target", group_id);
                data.put("messageChain", MsgUtil.getMessage(message));

                JSONObject main = new JSONObject();
                main.put("syncId", "");
                main.put("command", "sendGroupMessage");
                main.put("content", data);

                WebSocketService.client.send(main.toString());
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("向群" + group_id + "发送消息" + main);
                }
            } else {
                BotApi.LOGGER.warn("§c未找到机器人框架.");
            }
        }

    }

    //获取用户名信息
    public static String getUsernameFromInfo(JSONObject userInfo) {
        if (userInfo == null) {
            return "";
        }

        if (userInfo.getNumber("retcode").intValue() != 0) {
            return "";
        }

        String username = userInfo.getJSONObject("data").getString("card");
        if (username.equals("")) {
            username = userInfo.getJSONObject("data").getString("nickname");
        }

        return username;
    }

}
