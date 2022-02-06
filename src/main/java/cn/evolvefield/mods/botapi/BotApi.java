package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.common.command.CommandTree;
import cn.evolvefield.mods.botapi.common.config.BotConfig;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.service.ClientThreadService;
import cn.evolvefield.mods.botapi.init.event.ChatEventHandler;
import cn.evolvefield.mods.botapi.init.event.PlayerEventHandler;
import cn.evolvefield.mods.botapi.init.event.TickEventHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;

@Mod(modid = BotApi.MODID, acceptableRemoteVersions = "*" )
public class BotApi
{
    public static final String MODID = "botapi";
    public static final Logger LOGGER = LogManager.getLogger();
    public static Path CONFIG_FOLDER ;
    public static BotConfig config ;



    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        LOGGER.debug("Hello from QQ");
        CONFIG_FOLDER = event.getModConfigurationDirectory().toPath();
        config = new BotConfig();



    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event)
    {
        event.registerServerCommand(CommandTree.instance);
    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent event)
    {
        //加载配置
        config = ConfigManger.initBotConfig();
        if (config.getCommon().isEnable()) {
            ClientThreadService.runWebSocketClient();
        }

        new PlayerEventHandler().preInit();
        new ChatEventHandler().preInit();
        new TickEventHandler().preInit();

    }

    @Mod.EventHandler
    public static void onExitEvent(FMLServerStoppingEvent event) {
        ConfigManger.saveBotConfig(config);
        ClientThreadService.stopWebSocketClient();
    }
}
