package cn.evolvefield.mods.botapi.init.handler;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.LinkedList;
import java.util.Queue;

@Mod.EventBusSubscriber()
public class TickEventHandler {


    private static final Queue<String> toSendQueue = new LinkedList<>();;
    public static Queue<String> getToSendQueue() {
        return toSendQueue;
    }

    @SubscribeEvent
    public static void onTickEvent(TickEvent.WorldTickEvent event) {
        String toSend = toSendQueue.poll();
        if (!event.world.isRemote && toSend != null ) {
            ITextComponent textComponents = new TextComponentString(toSend);
            event.world.getMinecraftServer().getPlayerList().sendMessage(textComponents, false);
        }
    }
}
