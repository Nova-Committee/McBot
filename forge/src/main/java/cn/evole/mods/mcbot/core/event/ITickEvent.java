package cn.evole.mods.mcbot.core.event;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import java.util.LinkedList;
import java.util.Queue;
import cn.evole.mods.mcbot.config.ModConfig;
//#if MC <= 11802
import net.minecraft.network.chat.ChatType;
import net.minecraft.Util;
//#endif


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 10:47
 * Version: 1.0
 */
public class ITickEvent {
    private static final Queue<MutableComponent> SEND_QUEUE = new LinkedList<>();

    public static Queue<MutableComponent> getSendQueue() {
        return SEND_QUEUE;
    }


    public static void register(MinecraftServer server) {
        MutableComponent toSend = SEND_QUEUE.poll();
        if (ModConfig.INSTANCE != null
                && server != null
                && server.isDedicatedServer()
                && toSend != null
        ) {
            //#if MC >= 11900
            //$$  server.getPlayerList().broadcastSystemMessage(toSend, false);
            //#else
            server.getPlayerList().broadcastMessage(toSend, ChatType.SYSTEM, Util.NIL_UUID);
            //#endif
        }
    }
}
