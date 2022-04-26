package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.BotApi;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.network.chat.Component;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 10:47
 * Version: 1.0
 */
public class TickEventHandler {
    private static final Queue<String> toSendQueue = new LinkedList<>();
    ;

    public static Queue<String> getToSendQueue() {
        return toSendQueue;
    }


    public static void init() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            String toSend = toSendQueue.poll();
            if (BotApi.config != null
                    && !world.isClientSide
                    && toSend != null
            ) {
                Component textComponents = Component.literal(toSend);
                world.getServer().getPlayerList().broadcastSystemMessage(textComponents, false);
            }
        });
    }
}
