package cn.evolvefield.mods.botapi.event;

import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

@Mod.EventBusSubscriber
public class TickEventHandler {
    private static final Queue<String> toSendQueue = new LinkedList<>();;
    public static Queue<String> getToSendQueue() {
        return toSendQueue;
    }

    @SubscribeEvent
    public static void onTickEvent(TickEvent.WorldTickEvent event) {
        String toSend = toSendQueue.poll();
        if (!event.world.isClientSide && toSend != null) {
            ITextComponent textComponents = new StringTextComponent(toSend);
            event.world.getServer().getPlayerList().broadcastMessage(textComponents, ChatType.CHAT, UUID.randomUUID());
        }
    }
}