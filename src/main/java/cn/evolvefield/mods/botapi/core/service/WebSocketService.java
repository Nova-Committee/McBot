package cn.evolvefield.mods.botapi.core.service;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.core.bot.BotData;
import cn.evolvefield.mods.botapi.core.bot.CQHttpBot;
import cn.evolvefield.mods.botapi.core.bot.MiraiBot;
import cn.evolvefield.mods.botapi.util.json.JSONObject;
import cn.evolvefield.mods.botapi.util.websocket.client.WebSocketClient;
import cn.evolvefield.mods.botapi.util.websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/6 18:59
 * Version: 1.0
 */
public class WebSocketService {

    public static WebSocketClient client = null;

    public static void main(String url) {
        if (WebSocketService.client != null) {
            WebSocketService.client.close();
        }
        try {
            client = new WebSocketClient(new URI(url)) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    BotApi.LOGGER.info("§7[§a§l*§7] §a启用框架: §e" + BotData.getBotFrame() + "成功");
                }

                @Override
                public void onMessage(String message) {//执行接收到消息体后的相应操作
                    JSONObject json = new JSONObject(message);

                    if (BotApi.config.getCommon().isDebuggable()) {
                        BotApi.LOGGER.info(json);
                    }

                    if (BotData.getBotFrame().equalsIgnoreCase("cqhttp")) {
                        new CQHttpBot(message, json);
                    } else if (BotData.getBotFrame().equalsIgnoreCase("mirai")) {
                        new MiraiBot(message, json);
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    BotApi.LOGGER.info("§b[群服互联] §c退出连接");
                }

                @Override
                public void onError(Exception ex) {
                    BotApi.LOGGER.info("§7[§a§l*§7] §a启用框架: §e" + BotData.getBotFrame() + "失败");
                    BotApi.LOGGER.warn("§b[群服互联] §c出现错误!");
                    ex.printStackTrace();
                }
            };
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
