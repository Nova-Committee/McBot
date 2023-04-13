package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.init.handler.*;
import cn.evolvefield.onebot.client.connection.ConnectFactory;
import cn.evolvefield.onebot.client.core.Bot;
import cn.evolvefield.onebot.client.handler.EventBus;
import cn.evolvefield.onebot.sdk.util.FileUtils;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/12 8:36
 * Version: 1.0
 */
public class BotApi implements ModInitializer {
    public static ScheduledExecutorService configWatcherExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("BotApi Config Watcher %d").setDaemon(true).build());
    public static MinecraftServer SERVER = null;
    public static Path CONFIG_FOLDER;
    public static File CONFIG_FILE;
    public static LinkedBlockingQueue<String> blockingQueue;
    public static ConnectFactory service;
    public static EventBus dispatchers;
    public static Bot bot;
    public static ExecutorService app = Executors.newFixedThreadPool(1);
    ;

    public BotApi() {

    }


    private void onServerStarting(MinecraftServer server) {
        SERVER = server;//获取服务器实例
    }

    public MinecraftServer getServer() {
        return SERVER;
    }

    @Override
    public void onInitialize() {
        CONFIG_FOLDER = FabricLoader.getInstance().getConfigDir().resolve("botapi");
        FileUtils.checkFolder(CONFIG_FOLDER);
        CONFIG_FILE = CONFIG_FOLDER.resolve(Const.MODID + ".json").toFile();
        ConfigHandler.init(CONFIG_FILE);
        ServerLifecycleEvents.SERVER_STARTING.register(this::onServerStarting);
        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);
        ServerLifecycleEvents.SERVER_STOPPING.register(this::onServerStopping);
        ServerLifecycleEvents.SERVER_STOPPED.register(this::onServerStopped);
        CmdEventHandler.init();
        PlayerEventHandler.init();
        ChatEventHandler.init();
        TickEventHandler.init();
        Runtime.getRuntime().addShutdownHook(new Thread(BotApi::killOutThreads));
    }

    private void onServerStarted(MinecraftServer server) {
        blockingQueue = new LinkedBlockingQueue<>();//使用队列传输数据
        if (ConfigHandler.cached().getCommon().isAutoOpen()) {
            try {
                app.submit(() -> {
                    service = new ConnectFactory(ConfigHandler.cached().getBotConfig(), blockingQueue);//创建websocket连接
                    bot = service.ws.createBot();//创建机器人实例
                });
            } catch (Exception e) {
                Const.LOGGER.error("§c机器人服务端未配置或未打开");
            }
        }
        dispatchers = new EventBus(blockingQueue);//创建事件分发器
        CustomCmdHandler.INSTANCE.load();//自定义命令加载
        BotEventHandler.init(dispatchers);//事件监听s
    }

    private void onServerStopping(MinecraftServer server) {
        Const.isShutdown = true;
        Const.LOGGER.info("▌ §c正在关闭群服互联 §a┈━═☆");
        dispatchers.stop();//分发器关闭
        service.stop();
        app.shutdownNow();

    }

    private void onServerStopped(MinecraftServer server) {
        killOutThreads();
    }

    private static void killOutThreads() {
        try {
            ConfigHandler.save();//保存配置
            CustomCmdHandler.INSTANCE.clear();//自定义命令持久层清空
            ConfigHandler.watcher.get().close();//配置监控关闭
            BotApi.configWatcherExecutorService.shutdownNow();//监控进程关闭
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
