package cn.evole.mods.mcbot.init.handler;

import cn.evole.mods.multi.Const;
import cn.evole.mods.multi.api.mapping.MappingHelper;
import cn.evole.mods.multi.common.ComponentWrapper;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

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
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            String toSend = toSendQueue.poll();
            if (ConfigHandler.cached() != null
                    && server.isDedicatedServer()
                    && toSend != null
            ) {
                Component textComponents = ComponentWrapper.literal(toSend);


                if (Const.IS_1_19) {
                    server.getPlayerList().broadcastSystemMessage(textComponents, false);
                } else {
                    Class<?> PlayerList_class = MappingHelper.mapAndLoadClass("net.minecraft.class_3324", MappingHelper.CLASS_MAPPER_FUNCTION);
                    Method broadcastMsg = MappingHelper.mapAndGetMethod(PlayerList_class, "broadcastMessage", Component.class, ChatType.class, UUID.class);
                    Field ChatType_SYS = MappingHelper.mapAndGetField(ChatType.class, "field_11735", ChatType.class);

                    try {
                        broadcastMsg.invoke(server.getPlayerList(), textComponents, ChatType_SYS, Util.NIL_UUID);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        throw new UnsupportedOperationException("Failed to invoke method \"PlayerList::broadcastMessage\" with reflection", e);
                    }
                }
            }
        });
    }
}
