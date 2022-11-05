package cn.evolvefield.mods.botapi;


import cn.evolvefield.mods.botapi.common.cmds.CommandTree;
import cn.evolvefield.mods.botapi.init.config.ModConfig;
import cn.evolvefield.mods.botapi.init.handler.BotEventHandler;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.mods.botapi.init.handler.CustomCmdHandler;
import cn.evolvefield.onebot.sdk.connection.ConnectFactory;
import cn.evolvefield.onebot.sdk.connection.ModWebSocketClient;
import cn.evolvefield.onebot.sdk.core.Bot;
import cn.evolvefield.onebot.sdk.model.event.EventDispatchers;
import cn.evolvefield.onebot.sdk.util.FileUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.LinkedBlockingQueue;

@Mod(modid = Static.MODID, serverSideOnly = true, acceptableRemoteVersions = "*")
public class BotApi {
    public static final MinecraftServer SERVER = FMLCommonHandler.instance().getMinecraftServerInstance();
    public static Path CONFIGS;
    public static Path CONFIG_FOLDER;
    public static LinkedBlockingQueue<String> blockingQueue;
    public static ModWebSocketClient service;
    public static EventDispatchers dispatchers;
    public static Bot bot;
    public static ModConfig config;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) throws IOException {
        CONFIGS = event.getModConfigurationDirectory().toPath();
        CONFIG_FOLDER = event.getModConfigurationDirectory().toPath().resolve("botapi");
        FileUtils.checkFolder(CONFIG_FOLDER);
    }

    @Mod.EventHandler
    public static void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandTree());
    }

    @Mod.EventHandler
    public static void onServerStarted(FMLServerStartedEvent event) {
        config = ConfigHandler.load();//读取配置
        blockingQueue = new LinkedBlockingQueue<>();//使用队列传输数据
        if (config.getCommon().isAutoOpen()) {
            try {
                service = ConnectFactory.createWebsocketClient(config.getBotConfig(), blockingQueue);
                service.create();//创建websocket连接
                bot = service.createBot();//创建机器人实例
            } catch (Exception e) {
                Static.LOGGER.error("§c机器人服务端未配置或未打开");
            }
        }
        dispatchers = new EventDispatchers(blockingQueue);//创建事件分发器
        CustomCmdHandler.getInstance().load();//自定义命令加载
        BotEventHandler.init(dispatchers);//事件监听
    }

    @Mod.EventHandler
    public static void onServerStopped(FMLServerStoppedEvent event) {
        CustomCmdHandler.getInstance().clear();
        if (dispatchers != null) {
            dispatchers.stop();
        }
        if (service != null) {
            config.getBotConfig().setReconnect(false);
            service.close();
        }
        ConfigHandler.save(config);
        Static.LOGGER.info("▌ §c正在关闭群服互联 §a┈━═☆");
    }





}
