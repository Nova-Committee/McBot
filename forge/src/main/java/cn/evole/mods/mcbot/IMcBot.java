package cn.evole.mods.mcbot;

import cn.evole.mods.mcbot.init.config.ModConfig;
import cn.evole.mods.mcbot.init.event.IBotEvent;
import cn.evole.mods.mcbot.init.event.IChatEvent;
import cn.evole.mods.mcbot.init.event.IPlayerEvent;
import cn.evole.mods.mcbot.init.event.ITickEvent;
import cn.evole.mods.mcbot.init.handler.CustomCmdHandler;
import cn.evole.mods.mcbot.util.locale.I18n;
import cn.evole.onebot.client.connection.ConnectFactory;
import cn.evole.onebot.client.core.Bot;
import cn.evole.onebot.client.handler.EventBus;
import cn.evole.onebot.sdk.util.FileUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.nio.file.Path;
import java.util.concurrent.LinkedBlockingQueue;

public class IMcBot {

    public static MinecraftServer SERVER = null;
    public static Path CONFIG_FOLDER;
    public static Path CONFIG_FILE;
    
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
        CONFIG_FOLDER = Const.configDir.resolve("mcbot");
        FileUtils.checkFolder(CONFIG_FOLDER);
        CONFIG_FILE = CONFIG_FOLDER.resolve("config.toml");
        I18n.init(config_dir);
        Runtime.getRuntime().addShutdownHook(new Thread(IMcBot::killOutThreads));
    }

    public void onServerStarting(MinecraftServer server) {
        SERVER = server;//获取服务器实例
    }

    public void onServerStarted(MinecraftServer server) {
        blockingQueue = new LinkedBlockingQueue<>();//使用队列传输数据
        if (ModConfig.INSTANCE.getCommon().isAutoOpen()) {
            try {
                app = new Thread(() -> {
                    service = new ConnectFactory(ModConfig.INSTANCE.getBotConfig().toBot(), blockingQueue);//创建websocket连接
                    bot = service.ws.createBot();//创建机器人实例
                }, "BotServer");
                app.start();
            } catch (Exception e) {
                Const.LOGGER.error("§c机器人服务端未配置或未打开");
            }
        }
        bus = new EventBus(blockingQueue);//创建事件分发器
        CustomCmdHandler.INSTANCE.load();//自定义命令加载
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
            CustomCmdHandler.INSTANCE.clear();//自定义命令持久层清空
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onServerTick(MinecraftServer server) {
        ITickEvent.register(server);
    }
    public void onServerChat(Level level, Player player, String msg) {
        IChatEvent.register(player, msg);
    }
    public void onPlayerLogIn(Level level, Player player) {
        IPlayerEvent.loggedIn(level, player);
    }
    public void onPlayerLogOut(Level level, Player player) {
        IPlayerEvent.loggedOut(level, player);
    }
    public void onPlayerDeath(Level level, DamageSource source, ServerPlayer player) {
        IPlayerEvent.death(source, player);
    }
    public void onPlayerAdvancement(Level level, Player player, Advancement advancement) {
        IPlayerEvent.advancement(player, advancement);
    }
}
