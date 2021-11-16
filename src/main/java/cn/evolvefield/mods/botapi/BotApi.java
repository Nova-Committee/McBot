package cn.evolvefield.mods.botapi;


import cn.evolvefield.mods.botapi.command.CommandTree;
import cn.evolvefield.mods.botapi.config.BotConfig;
import cn.evolvefield.mods.botapi.config.ConfigManger;
import cn.evolvefield.mods.botapi.service.ClientThreadService;
import com.google.gson.Gson;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Mod(modid = BotApi.MODID,serverSideOnly = true, acceptableRemoteVersions = "*")
public class BotApi {

    public static final String MODID = "botapi";
    public static final Logger LOGGER = LogManager.getLogger();
    public static Path CONFIG_FOLDER ;
    private static Gson GSON = new Gson();
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
        if (config.getCommon().isENABLED()) {
            ClientThreadService.runWebSocketClient();
        }
        //加载配置
        ConfigManger.initBotConfig();
    }

    @Mod.EventHandler
    public static void onExitEvent(FMLServerStoppingEvent event) {
        ClientThreadService.stopWebSocketClient();
    }







}
