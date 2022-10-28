package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.BotApi;
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
            var split = message.split(" ");
            if (BotApi.config != null
                    && BotApi.config.getStatus().isS_CHAT_ENABLE()
                    && BotApi.config.getStatus().isSEND_ENABLED()
                    && BotApi.config.getCmd().getMcChatPrefix().equals(split[0])) {
                if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                    for (String id : BotApi.config.getCommon().getChannelIdList())
                        BotApi.bot.sendGuildMsg(BotApi.config.getCommon().getGuildId(), id, String.format("[" + BotApi.config.getCmd().getQqPrefix() + "]<%s> %s", player.getDisplayName(), message));
                } else {
                    for (long id : BotApi.config.getCommon().getGroupIdList())
                        BotApi.bot.sendGroupMsg(id, String.format("[" + BotApi.config.getCmd().getMcPrefix() + "]<%s> %s", player.getDisplayName(), message), true);
                }
            }
        });
    }
}
