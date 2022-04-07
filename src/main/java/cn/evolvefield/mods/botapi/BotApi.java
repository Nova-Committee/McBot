package cn.evolvefield.mods.botapi;


import cn.evolvefield.mods.botapi.api.data.BindData;
import cn.evolvefield.mods.botapi.common.command.CommandTree;
import cn.evolvefield.mods.botapi.common.config.BotConfig;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.bot.BotHandler;
import cn.evolvefield.mods.botapi.core.service.MySqlService;
import cn.evolvefield.mods.botapi.core.service.WebSocketService;
import cn.evolvefield.mods.botapi.util.FileUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;

@Mod(modid = BotApi.MODID,serverSideOnly = true, acceptableRemoteVersions = "*")
public class BotApi {

    public static final String MODID = "botapi";
    public static final Logger LOGGER = LogManager.getLogger();
    public static Path CONFIG_FOLDER ;
    public static BotConfig config;
    public static Connection connection;

    @Mod.EventHandler
    public static void onExitEvent(FMLServerStoppingEvent event) {
        ConfigManger.saveBotConfig(config);
        BindData.save();
        if (WebSocketService.client != null) {
            WebSocketService.client.close();
        }
        LOGGER.info("▌ §c正在关闭群服互联 §a┈━═☆");
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandTree());
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        LOGGER.debug("Hello from QQ");
        CONFIG_FOLDER = event.getModConfigurationDirectory().toPath().resolve("botapi");
        FileUtil.checkFolder(CONFIG_FOLDER);

    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent event) {
        //加载配置
        config = ConfigManger.initBotConfig();
        //绑定数据加载
        BindData.init();
        //连接框架与数据库
        if (BotApi.config.getCommon().isEnable()) {
            BotHandler.init();
            if (BotApi.config.getCommon().isSQL_ENABLED()) {
                LOGGER.info("▌ §a开始连接数据库 §6┈━═☆");
                connection = MySqlService.Join();
            }

        }

    }







}
