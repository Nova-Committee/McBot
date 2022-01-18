package cn.evolvefield.mods.botapi.init.events;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.message.SendMessage;
import cn.evolvefield.mods.botapi.init.callbacks.PlayerEvents;
import net.minecraft.network.chat.TranslatableComponent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 9:48
 * Version: 1.0
 */
public class PlayerEventHandler {
    public static void init(){
        PlayerEvents.PLAYER_LOGGED_IN.register((world, player) -> {
            if (BotApi.config.getCommon().isS_JOIN_ENABLE() && BotApi.config.getCommon().isEnable()){
                SendMessage.Group(BotApi.config.getCommon().getGroupId(), player.getDisplayName().getString() + " 加入了服务器");
            }
        });

        PlayerEvents.PLAYER_LOGGED_OUT.register((world, player) -> {
            if (BotApi.config.getCommon().isS_LEAVE_ENABLE() && BotApi.config.getCommon().isEnable()){
                SendMessage.Group(BotApi.config.getCommon().getGroupId(),player.getDisplayName().getString() + " 离开了服务器");
            }
        });

        PlayerEvents.PLAYER_DEATH.register((source, player) -> {
            if (player != null && BotApi.config.getCommon().isS_DEATH_ENABLE() && BotApi.config.getCommon().isEnable()) {
                String message = source.getLocalizedDeathMessage(player).getString();
                SendMessage.Group(BotApi.config.getCommon().getGroupId(),String.format(message, player.getDisplayName().getString()));
            }
        });

        PlayerEvents.PLAYER_ADVANCEMENT.register((player, advancement) -> {
            if ( BotApi.config.getCommon().isS_ADVANCE_ENABLE() && advancement.getDisplay() != null && BotApi.config.getCommon().isEnable()) {
                String message = new TranslatableComponent("chat.botapi.type.advancement." + advancement.getDisplay().getFrame().getName(), player.getDisplayName(), advancement.getChatComponent()).getString();
                SendMessage.Group(BotApi.config.getCommon().getGroupId(),message);
            }
        });
    }
}
