package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.api.data.BindData;
import cn.evolvefield.mods.botapi.common.config.BotConfig;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.bot.BotHandler;
import cn.evolvefield.mods.botapi.core.service.MySqlService;
import cn.evolvefield.mods.botapi.core.service.WebSocketService;
import cn.evolvefield.mods.botapi.core.tick.TickTimeService;
import cn.evolvefield.mods.botapi.init.handler.*;
import cn.evolvefield.mods.botapi.util.FileUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.sql.Connection;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/12 8:36
 * Version: 1.0
 */
public class BotApi implements ModInitializer {

    public static final String MODID = "botapi";
    public static final Logger LOGGER = LogManager.getLogger();
    public static Path CONFIG_FOLDER;
    public static BotConfig config;
    public static MinecraftServer SERVER = null;
    public static TickTimeService service;
    public static Connection connection;

    public BotApi() {

    }

    @Override
    public void onInitialize() {
        CONFIG_FOLDER = FabricLoader.getInstance().getConfigDir().resolve("botapi");
        FileUtil.checkFolder(CONFIG_FOLDER);

        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);

        ServerLifecycleEvents.SERVER_STOPPING.register(this::onServerStopping);
        CommandEventHandler.init();
        PlayerEventHandler.init();
        BotEventHandler.init();
        ChatEventHandler.init();
        TickEventHandler.init();
    }

    public MinecraftServer getServer() {
        return SERVER;
    }

    private void onServerStarted(MinecraftServer server) {
        SERVER = server;
        service = (TickTimeService) server;
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

    private void onServerStopping(MinecraftServer server) {
        ConfigManger.saveBotConfig(config);
        BindData.save();
        if (WebSocketService.client != null) {
            WebSocketService.client.close();
        }
        LOGGER.info("▌ §c正在关闭群服互联 §a┈━═☆");

    }
}
