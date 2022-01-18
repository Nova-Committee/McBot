package cn.evolvefield.mods.botapi;


import cn.evolvefield.mods.botapi.common.command.CommandTree;
import cn.evolvefield.mods.botapi.common.config.BotConfig;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.service.ClientThreadService;
import com.google.gson.Gson;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;

@Mod(modid = BotApi.MODID,serverSideOnly = true, acceptableRemoteVersions = "*")
public class BotApi {

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
        event.registerServerCommand(new CommandTree());
    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent event)
    {
        //加载配置
        config = ConfigManger.initBotConfig();
        if (config.getCommon().isEnable()) {
            ClientThreadService.runWebSocketClient();
        }

    }

    @Mod.EventHandler
    public static void onExitEvent(FMLServerStoppingEvent event) {
        ConfigManger.saveBotConfig(config);
        ClientThreadService.stopWebSocketClient();
    }







}
