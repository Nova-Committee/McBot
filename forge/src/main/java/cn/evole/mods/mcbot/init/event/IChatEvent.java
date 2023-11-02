package cn.evole.mods.mcbot.init.event;

import cn.evole.mods.mcbot.IMcBot;
import lombok.val;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 10:37
 * Version: 1.0
 */
public class IChatEvent {
    public static void register(Level level, Player player, String message) {
            val split = message.split(" ");
            if (IMcBot.config != null
                    && IMcBot.config.getStatus().isSChatEnable()
                    && IMcBot.config.getStatus().isSEnable()
                    && !message.contains("CICode")
                    && !level.isClientSide
            ) {
                if (IMcBot.config.getCommon().isGuildOn() && !IMcBot.config.getCommon().getChannelIdList().isEmpty()) {
                    for (String id : IMcBot.config.getCommon().getChannelIdList())
                        IMcBot.bot.sendGuildMsg(IMcBot.config.getCommon().getGuildId(),
                                id,
                                String.format(IMcBot.config.getCmd().isMcPrefixOn()
                                                ? "[" + IMcBot.config.getCmd().getMcPrefix() + "]<%s> %s"
                                                : "<%s> %s",
                                        player.getDisplayName().getString(),
                                        IMcBot.config.getCmd().isMcChatPrefixOn()
                                                && IMcBot.config.getCmd().getMcChatPrefix().equals(split[0]) ? split[1] : message));
                }
                if (IMcBot.config.getCommon().isGroupOn()&& !IMcBot.config.getCommon().getGroupIdList().isEmpty()){
                    for (long id : IMcBot.config.getCommon().getGroupIdList())
                        IMcBot.bot.sendGroupMsg(
                                id,
                                String.format(IMcBot.config.getCmd().isMcPrefixOn()
                                                ? "[" + IMcBot.config.getCmd().getMcPrefix() + "]<%s> %s"
                                                : "<%s> %s",
                                        player.getDisplayName().getString(),
                                        IMcBot.config.getCmd().isMcChatPrefixOn()
                                                && IMcBot.config.getCmd().getMcChatPrefix().equals(split[0]) ? split[1] : message),
                                false);
                }


            }
        }

}
