package cn.evole.mods.mcbot.core.event;

import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.config.ModConfig;
import cn.evole.onebot.sdk.util.MsgUtils;
import lombok.val;
import net.minecraft.server.level.ServerPlayer;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 10:37
 * Version: 1.0
 */
public class IChatEvent {
    public static void register(ServerPlayer player, String message) {
        val split = message.split(" ");
        if (ModConfig.INSTANCE != null
                && ModConfig.INSTANCE.getStatus().isSChatEnable()
                && ModConfig.INSTANCE.getStatus().isSEnable()
                && !message.contains("CICode")
                && !player.getCommandSenderWorld().isClientSide
        ) {
            String msg = String.format(ModConfig.INSTANCE.getCmd().isMcPrefixOn()
                            ? "[" + ModConfig.INSTANCE.getCmd().getMcPrefix() + "]<%s> %s"
                            : "<%s> %s",
                    player.getDisplayName().getString(),
                    ModConfig.INSTANCE.getCmd().isMcChatPrefixOn()
                            && ModConfig.INSTANCE.getCmd().getMcChatPrefix().equals(split[0]) ? split[1] : message);

            Const.sendAllGroupMsg(MsgUtils.builder().text(msg).build());

        }
    }

}
