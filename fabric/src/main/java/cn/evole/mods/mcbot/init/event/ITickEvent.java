package cn.evole.mods.mcbot.init.event;

import cn.evole.mods.mcbot.init.handler.ConfigHandler;
//#if MC >= 11900
import net.minecraft.network.chat.Component;
//#else
//$$ import net.minecraft.network.chat.TextComponent;
//#endif
import net.minecraft.server.MinecraftServer;

import java.util.LinkedList;
import java.util.Queue;

//#if MC <= 11802
//$$ import java.util.UUID;
//$$ import net.minecraft.network.chat.ChatType;
//$$ import net.minecraft.Util;
//#endif


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 10:47
 * Version: 1.0
 */
public class ITickEvent {
    private static final Queue<String> toSendQueue = new LinkedList<>();
    ;

    public static Queue<String> getToSendQueue() {
        return toSendQueue;
    }


    public static void init(MinecraftServer server) {
            String toSend = toSendQueue.poll();
            if (ConfigHandler.cached() != null
                    && server.isDedicatedServer()
                    && toSend != null
            ) {
                //#if MC >= 11900
                server.getPlayerList().broadcastSystemMessage(Component.literal(toSend), false);
                //#elseif MC <= 11502
                //$$ server.getPlayerList().broadcastMessage(new TextComponent(toSend), false);
                //#else
                //$$ server.getPlayerList().broadcastMessage(new TextComponent(toSend), ChatType.SYSTEM, Util.NIL_UUID);
                //#endif
            }
    }
}
