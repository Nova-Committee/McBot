package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.message.SendMessage;
import cn.evolvefield.mods.botapi.init.callbacks.PlayerEvents;
import cn.evolvefield.mods.botapi.util.I18a;
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
            if (BotApi.config.getStatus().isS_JOIN_ENABLE() && BotApi.config.getStatus().isSEND_ENABLED()) {
                if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                    for (String id : BotApi.config.getCommon().getChannelIdList())
                        SendMessage.ChannelGroup(BotApi.config.getCommon().getGuildId(), id, player.getDisplayName().getString() + " 加入了服务器");
                } else {
                    SendMessage.Group(BotApi.config.getCommon().getGroupId(), player.getDisplayName().getString() + " 加入了服务器");
                }
            }
        });

        PlayerEvents.PLAYER_LOGGED_OUT.register((world, player) -> {
            if (BotApi.config.getStatus().isS_LEAVE_ENABLE() && BotApi.config.getStatus().isSEND_ENABLED()) {
                if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                    for (String id : BotApi.config.getCommon().getChannelIdList())
                        SendMessage.ChannelGroup(BotApi.config.getCommon().getGuildId(), id, player.getDisplayName().getString() + " 离开了服务器");
                } else {
                    SendMessage.Group(BotApi.config.getCommon().getGroupId(), player.getDisplayName().getString() + " 离开了服务器");

                }
            }
        });

        PlayerEvents.PLAYER_DEATH.register((source, player) -> {
            if (player != null && BotApi.config.getStatus().isS_DEATH_ENABLE() && BotApi.config.getStatus().isSEND_ENABLED()) {
                LivingEntity livingEntity2 = player.getKillCredit();
                String string = "botapi.death.attack." + source.msgId;
                String string2 = string + ".player";
                String msg = livingEntity2 != null ? I18a.get(string2, player.getDisplayName().getString(), livingEntity2.getDisplayName().getString()) : I18a.get(string, player.getDisplayName().getString());

                if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                    for (String id : BotApi.config.getCommon().getChannelIdList())
                        SendMessage.ChannelGroup(BotApi.config.getCommon().getGuildId(), id, String.format(msg, player.getDisplayName().getString()));
                } else {
                    SendMessage.Group(BotApi.config.getCommon().getGroupId(), String.format(msg, player.getDisplayName().getString()));
                }
            }
        });

        PlayerEvents.PLAYER_ADVANCEMENT.register((player, advancement) -> {
            if (BotApi.config.getStatus().isS_ADVANCE_ENABLE() && advancement.getDisplay() != null && BotApi.config.getStatus().isSEND_ENABLED()) {
                String msg = I18a.get("botapi.chat.type.advancement." + advancement.getDisplay().getFrame().getName(), player.getDisplayName().getString(), I18a.get(advancement.getDisplay().getTitle().getString()));

                if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                    for (String id : BotApi.config.getCommon().getChannelIdList())
                        SendMessage.ChannelGroup(BotApi.config.getCommon().getGuildId(), id, msg);
                } else {
                    SendMessage.Group(BotApi.config.getCommon().getGroupId(), msg);
                }
            }
        });
    }
}
