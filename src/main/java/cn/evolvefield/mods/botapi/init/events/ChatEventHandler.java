package cn.evolvefield.mods.botapi.init.events;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.core.service.MessageHandlerService;
import cn.evolvefield.mods.botapi.init.callbacks.ServerLevelEvents;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 10:37
 * Version: 1.0
 */
public class ChatEventHandler {
    public static void init(){
        ServerLevelEvents.Server_Chat.register((player, message, component) -> {
            if (BotApi.config.getCommon().isS_CHAT_ENABLE() && BotApi.config.getCommon().isEnable()) {
                MessageHandlerService.sendMessage(player, message);
            }
        });
    }
}
