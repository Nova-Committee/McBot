package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.init.handler.*;
import cn.evolvefield.onebot.client.connection.ConnectFactory;
import cn.evolvefield.onebot.client.core.Bot;
import cn.evolvefield.onebot.client.handler.EventBus;
import cn.evolvefield.onebot.sdk.util.FileUtils;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.*;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

@Mod( modid = Const.MODID, acceptableRemoteVersions = "*")
public class BotApi {

    public static ScheduledExecutorService configWatcherExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("BotApi Config Watcher %d").setDaemon(true).build());
    public static MinecraftServer SERVER = null;
    public static Path CONFIG_FOLDER;
    public static File CONFIG_FILE;
    public static LinkedBlockingQueue<String> blockingQueue;
    public static ConnectFactory service;
    public static EventBus dispatchers;
    public static Bot bot;
    public static ExecutorService app = Executors.newFixedThreadPool(1);
    boolean init = false;
    public BotApi() {
    }
    @Mod.EventHandler
    private void pre(final FMLPreInitializationEvent event) {
        CONFIG_FOLDER = event.getModConfigurationDirectory().toPath().resolve("botapi");
        FileUtils.checkFolder(CONFIG_FOLDER);
        CONFIG_FILE = CONFIG_FOLDER.resolve(Const.MODID + ".json").toFile();
        ConfigHandler.init(CONFIG_FILE);

    }

    @Mod.EventHandler
    public void onServerAboutToStart(FMLServerStartingEvent event) {
        SERVER = event.getServer();
        event.registerServerCommand(new CmdEventHandler());
        CustomCmdHandler.INSTANCE.load(CONFIG_FOLDER);//自定义命令加载
    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent event) throws Exception{
        ChatEventHandler.INSTANCE.preInit();
        PlayerEventHandler.INSTANCE.preInit();
        TickEventHandler.INSTANCE.preInit();
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

        BotEventHandler.init(dispatchers);//事件监听s
    }
    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event){
        Const.isShutdown = true;
        Const.LOGGER.info("▌ §c正在关闭群服互联 §a┈━═☆");
        dispatchers.stop();//分发器关闭
        service.stop();
        app.shutdownNow();

    }
    @Mod.EventHandler
    public void onServerStopped(FMLServerStoppedEvent event){
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
