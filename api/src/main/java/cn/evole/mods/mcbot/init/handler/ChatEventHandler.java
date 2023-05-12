package cn.evole.mods.mcbot.init.handler;

import cn.evole.mods.mcbot.McBot;
import cn.evole.mods.mcbot.init.callbacks.ServerLevelEvents;
import lombok.val;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 10:37
 * Version: 1.0
 */
public class ChatEventHandler {
    public static void init() {
        ServerLevelEvents.Server_Chat.register((player, message) -> {
            val split = message.split(" ");
            if (ConfigHandler.cached() != null
                    && ConfigHandler.cached().getStatus().isS_CHAT_ENABLE()
                    && ConfigHandler.cached().getStatus().isSEND_ENABLED()
                    && !message.contains("CICode")
            ) {
                if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                    for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                        McBot.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(),
                                id,
                                String.format(ConfigHandler.cached().getCmd().isMcPrefixOn()
                                                ? "[" + ConfigHandler.cached().getCmd().getMcPrefix() + "]<%s> %s"
                                                : "<%s> %s",
                                        player.getDisplayName().getString(),
                                        ConfigHandler.cached().getCmd().isMcChatPrefixOn()
                                                && ConfigHandler.cached().getCmd().getMcChatPrefix().equals(split[0]) ? split[1] : message));
                } else {
                    for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                        McBot.bot.sendGroupMsg(
                                id,
                                String.format(ConfigHandler.cached().getCmd().isMcPrefixOn()
                                                ? "[" + ConfigHandler.cached().getCmd().getMcPrefix() + "]<%s> %s"
                                                : "<%s> %s",
                                        player.getDisplayName().getString(),
                                        ConfigHandler.cached().getCmd().isMcChatPrefixOn()
                                                && ConfigHandler.cached().getCmd().getMcChatPrefix().equals(split[0]) ? split[1] : message),
                                true);
                }


            }
        });
    }
}
