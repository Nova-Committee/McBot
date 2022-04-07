package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.message.SendMessage;
import cn.evolvefield.mods.botapi.init.callbacks.ServerLevelEvents;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 10:37
 * Version: 1.0
 */
public class ChatEventHandler {
    public static void init() {
        ServerLevelEvents.Server_Chat.register((player, message, component) -> {
            if (BotApi.config.getStatus().isS_CHAT_ENABLE() && BotApi.config.getStatus().isSEND_ENABLED()) {
                SendMessage.Group(BotApi.config.getCommon().getGroupId(), String.format("[MC]<%s> %s", player.getDisplayName().getString(), message));
            }
        });
    }
}
