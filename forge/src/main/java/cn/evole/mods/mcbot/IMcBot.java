package cn.evole.mods.mcbot;

import cn.evole.mods.mcbot.init.event.IBotEvent;
import cn.evole.mods.mcbot.init.event.IChatEvent;
import cn.evole.mods.mcbot.init.event.IPlayerEvent;
import cn.evole.mods.mcbot.init.event.ITickEvent;
import cn.evole.mods.mcbot.init.handler.ConfigHandler;
import cn.evole.mods.mcbot.init.handler.CustomCmdHandler;
import cn.evole.onebot.client.connection.ConnectFactory;
import cn.evole.onebot.client.core.Bot;
import cn.evole.onebot.client.handler.EventBus;
import cn.evole.onebot.sdk.util.FileUtils;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

public class IMcBot {

    public static ScheduledExecutorService configWatcherExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("BotApi Config Watcher %d").setDaemon(true).build());
    public static MinecraftServer SERVER = null;
    public static CustomCmdHandler CMD ;
    public static Path CONFIG_FOLDER;
    public static File CONFIG_FILE;
    public static LinkedBlockingQueue<String> blockingQueue;
    public static ConnectFactory service;
    public static EventBus bus;
    public static Bot bot;
    public static Thread app;

    public MinecraftServer getServer() {
        return SERVER;
    }

    public IMcBot(Path configDir) {
        init(configDir);
    }
    public void init(Path config_dir) {
        CONFIG_FOLDER = config_dir.resolve("mcbot");
        FileUtils.checkFolder(CONFIG_FOLDER);
        CONFIG_FILE = CONFIG_FOLDER.resolve(Const.MODID + ".json").toFile();
        ConfigHandler.init(CONFIG_FILE);
        CMD = new CustomCmdHandler(CONFIG_FOLDER);
        Runtime.getRuntime().addShutdownHook(new Thread(IMcBot::killOutThreads));
    }

    public void onServerStarting(MinecraftServer server) {
        SERVER = server;//获取服务器实例
    }

    public void onServerStarted(MinecraftServer server) {
        blockingQueue = new LinkedBlockingQueue<>();//使用队列传输数据
        if (ConfigHandler.cached().getCommon().isAutoOpen()) {
            try {
                app = new Thread(() -> {
                    service = new ConnectFactory(ConfigHandler.cached().getBotConfig(), blockingQueue);//创建websocket连接
                    bot = service.ws.createBot();//创建机器人实例
                }, "BotServer");
                app.start();
            } catch (Exception e) {
                Const.LOGGER.error("§c机器人服务端未配置或未打开");
            }
        }
        bus = new EventBus(blockingQueue);//创建事件分发器
        CMD.load();//自定义命令加载
        IBotEvent.init(bus);//事件监听s
    }

    public void onServerStopping(MinecraftServer server) {
        Const.isShutdown = true;
        Const.LOGGER.info("▌ §c正在关闭群服互联 §a┈━═☆");
        bus.stop();//分发器关闭
        service.stop();
        app.interrupt();
    }

    public void onServerStopped(MinecraftServer server) {
        killOutThreads();
    }

    private static void killOutThreads() {
        try {
            ConfigHandler.save();//保存配置
            CMD.clear();//自定义命令持久层清空
            ConfigHandler.watcher.get().close();//配置监控关闭
            IMcBot.configWatcherExecutorService.shutdownNow();//监控进程关闭
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onServerTick(MinecraftServer server) {
        ITickEvent.register(server);
    }
    public void onServerChat(Player player, String msg) {
        IChatEvent.register(player, msg);
    }
    public void onPlayerLogIn(Player player) {
        IPlayerEvent.loggedIn(player);
    }

    public void onPlayerLogOut(Player player) {
        IPlayerEvent.loggedOut(player);
    }
    public void onPlayerDeath(DamageSource source, ServerPlayer player) {
        IPlayerEvent.death(source, player);
    }
    public void onPlayerAdvancement(Player player, Advancement advancement) {
        IPlayerEvent.advancement(player, advancement);
    }
}
