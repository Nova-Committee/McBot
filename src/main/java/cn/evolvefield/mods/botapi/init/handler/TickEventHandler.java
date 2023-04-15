package cn.evolvefield.mods.botapi.init.handler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import lombok.val;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TickEventHandler {
    public static TickEventHandler INSTANCE = new TickEventHandler();

    public void preInit() {
        FMLCommonHandler.instance().bus().register(this);
    }
    private static final Queue<String> toSendQueue = new LinkedList<>();;
    public static Queue<String> getToSendQueue() {
        return toSendQueue;
    }

    @SubscribeEvent
    public void onTickEvent(TickEvent.WorldTickEvent event) {
        val world = event.world;
        String toSend = toSendQueue.poll();
        if (world != null
                && !event.world.isRemote
                && ConfigHandler.cached() != null
                && toSend != null) {
            val textComponents = new ChatComponentText(toSend);
            List<EntityPlayer> playerMPList = event.world.playerEntities;
            playerMPList.forEach(player -> player.addChatComponentMessage(textComponents));
        }
    }
}
