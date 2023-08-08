package cn.evole.mods.mcbot;

import cn.evole.mods.mcbot.connect.WSServer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.java_websocket.server.WebSocketServer;

/**
 * Author cnlimiter
 * CreateTime 2023/7/26 23:50
 * Name McBot_koishi
 * Description
 */

public class McBotKoishi implements ModInitializer {
    public static MinecraftServer server;
    public static WebSocketServer ws;
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register((l) -> {
            McBotKoishi.server = l;
        });
        ServerLifecycleEvents.SERVER_STARTED.register((l) -> {
            ws = new WSServer();
            ws.start();
        });
    }
}
