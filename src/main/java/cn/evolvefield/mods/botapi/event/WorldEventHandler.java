package cn.evolvefield.mods.botapi.event;

import cn.evolvefield.mods.botapi.config.ModConfig;
import cn.evolvefield.mods.botapi.service.ClientThreadService;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;


@Mod.EventBusSubscriber
public class WorldEventHandler {

    @SubscribeEvent
    public static void onEntranceEvent(FMLServerStartingEvent event) {
        if (ModConfig.IS_ENABLED.get()) {
            ClientThreadService.runWebSocketClient();
        }
    }

    @SubscribeEvent
    public static void onExitEvent(FMLServerStoppingEvent event) {
        ClientThreadService.stopWebSocketClient();
    }
}
