package cn.evolvefield.mods.botapi.event;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.service.MessageHandlerService;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@Mod.EventBusSubscriber
public class ChatEventHandler {
    @SubscribeEvent
    public static void onChatEvent(ServerChatEvent event) {
        if (BotApi.config.getCommon().isSEND_ENABLED()) {
            MessageHandlerService.sendMessage(event);
        }
    }
}