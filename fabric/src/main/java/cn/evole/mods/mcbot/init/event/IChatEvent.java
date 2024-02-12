package cn.evole.mods.mcbot.init.event;

import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.init.config.ModConfig;
import lombok.val;
import net.minecraft.world.entity.player.Player;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 10:37
 * Version: 1.0
 */
public class IChatEvent {
    public static void register(Player player, String message) {

        val split = message.split(" ");
        if (ModConfig.INSTANCE != null
                && ModConfig.INSTANCE.getStatus().isSChatEnable()
                && ModConfig.INSTANCE.getStatus().isSEnable()
                && !message.contains("CICode")
                && !player.getCommandSenderWorld().isClientSide
        ) {
            if (ModConfig.INSTANCE.getCommon().isGuildOn() && !ModConfig.INSTANCE.getCommon().getChannelIdList().isEmpty()) {
                val msg = String.format(ModConfig.INSTANCE.getCmd().isMcPrefixOn()
                                ? "[" + ModConfig.INSTANCE.getCmd().getMcPrefix() + "]<%s> %s"
                                : "<%s> %s",
                        player.getDisplayName().getString(),
                        ModConfig.INSTANCE.getCmd().isMcChatPrefixOn()
                                && ModConfig.INSTANCE.getCmd().getMcChatPrefix().equals(split[0]) ? split[1] : message);

                Const.sendGuildMsg(msg);

            } else {
                val msg = String.format(ModConfig.INSTANCE.getCmd().isMcPrefixOn()
                                ? "[" + ModConfig.INSTANCE.getCmd().getMcPrefix() + "]<%s> %s"
                                : "<%s> %s",
                        player.getDisplayName().getString(),
                        ModConfig.INSTANCE.getCmd().isMcChatPrefixOn()
                                && ModConfig.INSTANCE.getCmd().getMcChatPrefix().equals(split[0]) ? split[1] : message);

                Const.sendGroupMsg(msg);

            }


        }
    }

}
