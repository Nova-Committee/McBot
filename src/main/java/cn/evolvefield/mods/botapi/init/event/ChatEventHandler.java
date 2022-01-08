package cn.evolvefield.mods.botapi.init.event;
import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.core.service.MessageHandlerService;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class ChatEventHandler {
    @SubscribeEvent
    public static void onChatEvent(ServerChatEvent event) {
        if (BotApi.config.getCommon().isS_CHAT_ENABLE() && BotApi.config.getCommon().isEnable()) {
            MessageHandlerService.sendMessage(event);
        }
    }
}