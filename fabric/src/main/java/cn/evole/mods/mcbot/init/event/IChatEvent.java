package cn.evole.mods.mcbot.init.event;

import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.init.config.ModConfig;
import cn.evole.mods.mcbot.util.onebot.CQUtils;
import lombok.val;
import net.minecraft.world.entity.player.Player;

//兼容1.20.1版本vanish
//#if MC == 12001
//$$ import cn.evole.mods.mcbot.init.compat.VanishAPI;
//#endif

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 10:37
 * Version: 1.0
 */
public class IChatEvent {
    public static void register(Player player, String message) {
        //#if MC == 12001
        //$$ if (VanishAPI.isVanished(player)) return;
        //#endif

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

            msg = CQUtils.replace(msg);
            if (ModConfig.INSTANCE.getCommon().isGuildOn() && !ModConfig.INSTANCE.getCommon().getChannelIdList().isEmpty()) {
                Const.sendGuildMsg(msg);
            } else {
                Const.sendGroupMsg(msg);
            }


        }
    }

}
