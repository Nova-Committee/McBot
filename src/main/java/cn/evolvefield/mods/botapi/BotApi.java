package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.common.config.BotConfig;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.init.events.ChatEventHandler;
import cn.evolvefield.mods.botapi.init.events.CommandEventHandler;

import cn.evolvefield.mods.botapi.core.service.ClientThreadService;
import cn.evolvefield.mods.botapi.init.events.PlayerEventHandler;
import cn.evolvefield.mods.botapi.init.events.TickEventHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/12 8:36
 * Version: 1.0
 */
public class BotApi implements ModInitializer {

    public static final String MODID = "botapi";
    public static final Logger LOGGER = LogManager.getLogger();
    public static Path CONFIG_FOLDER ;
    public static BotConfig config ;

    public static MinecraftServer SERVER = null;

    public BotApi(){

    }

    @Override
    public void onInitialize() {
        CONFIG_FOLDER = FabricLoader.getInstance().getConfigDir();

        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);

        ServerLifecycleEvents.SERVER_STOPPING.register(this::onServerStopping);


        CommandEventHandler.init();
        PlayerEventHandler.init();
        ChatEventHandler.init();
        TickEventHandler.init();
    }

    public MinecraftServer getServer(){
        return SERVER;
    }

    private void onServerStarted(MinecraftServer server){
        SERVER = server;
        //加载配置
        config = ConfigManger.initBotConfig();
        if (BotApi.config.getCommon().isEnable()) {
            ClientThreadService.runWebSocketClient();
        }
    }

    private void onServerStopping(MinecraftServer server){
        ConfigManger.saveBotConfig(config);
        ClientThreadService.stopWebSocketClient();
    }
}
