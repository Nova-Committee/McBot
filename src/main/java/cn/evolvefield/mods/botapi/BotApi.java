package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.init.config.ModConfig;
import cn.evolvefield.mods.botapi.init.handler.*;
import cn.evolvefield.onebot.sdk.connection.ConnectFactory;
import cn.evolvefield.onebot.sdk.connection.ModWebSocketClient;
import cn.evolvefield.onebot.sdk.core.Bot;
import cn.evolvefield.onebot.sdk.model.event.EventDispatchers;
import cn.evolvefield.onebot.sdk.util.FileUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;

import java.nio.file.Path;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/12 8:36
 * Version: 1.0
 */
public class BotApi implements ModInitializer {

    public static MinecraftServer SERVER = null;
    public static Path CONFIG_FOLDER;
    public static LinkedBlockingQueue<String> blockingQueue;
    public static ModWebSocketClient service;
    public static EventDispatchers dispatchers;
    public static Bot bot;
    public static ModConfig config;

    public BotApi() {

    }

    @Override
    public void onInitialize() {
        CONFIG_FOLDER = FabricLoader.getInstance().getConfigDir().resolve("botapi");
        FileUtils.checkFolder(CONFIG_FOLDER);

        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);

        ServerLifecycleEvents.SERVER_STOPPING.register(this::onServerStopping);
        CmdEventHandler.init();
        PlayerEventHandler.init();
        ChatEventHandler.init();
        TickEventHandler.init();
    }

    public MinecraftServer getServer() {
        return SERVER;
    }

    private void onServerStarted(MinecraftServer server) {
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

    private void onServerStopping(MinecraftServer server) {
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
