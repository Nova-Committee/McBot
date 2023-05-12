package cn.evole.mods.mcbot.init.handler;

import cn.evole.mods.mcbot.McBot;
import cn.evole.mods.mcbot.init.callbacks.PlayerEvents;
import cn.evole.mods.mcbot.util.locale.I18n;
import net.minecraft.world.entity.LivingEntity;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 9:48
 * Version: 1.0
 */
public class PlayerEventHandler {
    public static void init() {
        PlayerEvents.PLAYER_LOGGED_IN.register((world, player) -> {
            if (ConfigHandler.cached().getStatus().isS_JOIN_ENABLE() && ConfigHandler.cached().getStatus().isSEND_ENABLED()) {
                if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                    for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                        McBot.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(), id, player.getDisplayName().getString() + " 加入了服务器");
                } else {
                    for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                        McBot.bot.sendGroupMsg(id, player.getDisplayName().getString() + " 加入了服务器", true);
                }
            }
        });

        PlayerEvents.PLAYER_LOGGED_OUT.register((world, player) -> {
            if (ConfigHandler.cached().getStatus().isS_LEAVE_ENABLE() && ConfigHandler.cached().getStatus().isSEND_ENABLED()) {
                if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                    for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                        McBot.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(), id, player.getDisplayName().getString() + " 离开了服务器");
                } else {
                    for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                        McBot.bot.sendGroupMsg(id, player.getDisplayName().getString() + " 离开了服务器", true);

                }
            }
        });

        PlayerEvents.PLAYER_DEATH.register((source, player) -> {
            if (player != null && ConfigHandler.cached().getStatus().isS_DEATH_ENABLE() && ConfigHandler.cached().getStatus().isSEND_ENABLED()) {
                LivingEntity livingEntity2 = player.getKillCredit();
                String string = "McBot.death.attack." + source.type().msgId();
                String msg = livingEntity2 != null ? I18n.get(string, player.getDisplayName().getString(), livingEntity2.getDisplayName().getString()) : I18n.get(string, player.getDisplayName().getString());

                if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                    for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                        McBot.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(), id, String.format(msg, player.getDisplayName().getString()));
                } else {
                    for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                        McBot.bot.sendGroupMsg(id, String.format(msg, player.getDisplayName().getString()), true);
                }
            }
        });

        PlayerEvents.PLAYER_ADVANCEMENT.register((player, advancement) -> {
            if (ConfigHandler.cached().getStatus().isS_ADVANCE_ENABLE() && advancement.getDisplay() != null && ConfigHandler.cached().getStatus().isSEND_ENABLED()) {
                String msg = I18n.get("McBot.chat.type.advancement." + advancement.getDisplay().getFrame().getName(), player.getDisplayName().getString(), I18n.get(advancement.getDisplay().getTitle().getString()));

                if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                    for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                        McBot.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(), id, msg);
                } else {
                    for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                        McBot.bot.sendGroupMsg(id, msg, true);
                }
            }
        });
    }
}
