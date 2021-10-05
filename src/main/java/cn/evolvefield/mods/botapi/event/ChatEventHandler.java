package cn.evolvefield.mods.botapi.event;
import cn.evolvefield.mods.botapi.config.ModConfig;
import cn.evolvefield.mods.botapi.service.MessageService;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class ChatEventHandler {
    @SubscribeEvent
    public static void onChatEvent(ServerChatEvent event) {
        if (ModConfig.SEND_ENABLED.get()) {
            MessageService.sendMessage(event);
        }
    }
}