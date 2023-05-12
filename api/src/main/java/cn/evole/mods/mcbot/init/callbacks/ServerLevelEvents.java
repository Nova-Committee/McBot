package cn.evole.mods.mcbot.init.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerPlayer;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 10:27
 * Version: 1.0
 */
public final class ServerLevelEvents {
    public ServerLevelEvents() {
    }

    public static final Event<ServerLevelEvents.Server_Chat> Server_Chat = EventFactory.createArrayBacked(ServerLevelEvents.Server_Chat.class, callbacks -> (player, message) -> {
        for (ServerLevelEvents.Server_Chat callback : callbacks) {
            callback.onChat(player, message);
        }
    });


    @FunctionalInterface
    public interface Server_Chat {
        void onChat(ServerPlayer player, String message);
    }


}
