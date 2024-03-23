package cn.evole.mods.mcbot.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class McBotEvents {
    /**
     * 当玩家发送一条消息（并转发到QQ）后触发。
     * 包含发送消息的玩家、message_id和消息内容
     */
    public static final Event<PlayerChat> ON_CHAT = EventFactory.createArrayBacked(PlayerChat.class, callbacks -> (player, message_id, message) -> {
        for (PlayerChat callback : callbacks) {
            callback.onChat(player, message_id, message);
        }
    });

    /**
     * 当玩家发送一条消息后（并在任何处理之前）触发。
     * 如果取消它，消息将不会发送到游戏和QQ。
     * 包含发送消息的玩家、消息内容和CallBackInfo。
     */
    public static final Event<EarlyPlayerChat> BEFORE_CHAT = EventFactory.createArrayBacked(EarlyPlayerChat.class, callbacks -> (player, message, ci) -> {
        for (EarlyPlayerChat callback : callbacks) {
            callback.onChat(player, message, ci);
        }
    });

    @FunctionalInterface
    public interface PlayerChat {
        void onChat(ServerPlayer player, int message_id, String message);
    }

    @FunctionalInterface
    public interface EarlyPlayerChat {
        void onChat(ServerPlayer player, String message, CallbackInfo ci);
    }
}
