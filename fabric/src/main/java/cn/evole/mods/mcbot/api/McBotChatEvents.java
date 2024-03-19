package cn.evole.mods.mcbot.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerPlayer;

public class McBotChatEvents {
    /**
     * 当玩家发送一条消息（并转发到QQ）后触发。
     * 包含发送消息的玩家和message_id
     */
    public static final Event<PlayerChat> ON_CHAT = EventFactory.createArrayBacked(PlayerChat.class, callbacks -> (player, message_id, message) -> {
        for (PlayerChat callback : callbacks) {
            callback.onChat(player, message_id, message);
        }
    });

    @FunctionalInterface
    public interface PlayerChat {
        void onChat(ServerPlayer player, int message_id, String message);
    }
}
