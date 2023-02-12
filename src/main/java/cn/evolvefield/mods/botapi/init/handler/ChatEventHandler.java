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
            if (ConfigHandler.cached() != null
                    && ConfigHandler.cached().getStatus().isS_CHAT_ENABLE()
                    && ConfigHandler.cached().getStatus().isSEND_ENABLED()
                    && !message.contains("CICode")
            ) {
                if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                    for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                        BotApi.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(),
                                id,
                                String.format("[" + ConfigHandler.cached().getCmd().getMcPrefix() + "]<%s> %s",
                                        player.getDisplayName().getString(),
                                        ConfigHandler.cached().getCmd().isMcChatPrefixEnable()
                                                && ConfigHandler.cached().getCmd().getMcChatPrefix().equals(split[0]) ? split[1] : message));
                } else {
                    for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                        BotApi.bot.sendGroupMsg(
                                id,
                                String.format("[" + ConfigHandler.cached().getCmd().getMcPrefix() + "]<%s> %s",
                                        player.getDisplayName().getString(),
                                        ConfigHandler.cached().getCmd().isMcChatPrefixEnable()
                                                && ConfigHandler.cached().getCmd().getMcChatPrefix().equals(split[0]) ? split[1] : message),
                                true);
                }


            }
        });
    }
}
