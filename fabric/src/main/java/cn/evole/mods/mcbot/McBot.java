package cn.evole.mods.mcbot;

import cn.evole.mods.mcbot.init.callbacks.IEvents;
import cn.evole.mods.mcbot.init.event.*;
import cn.evole.mods.mcbot.init.config.ModConfig;
import cn.evole.mods.mcbot.init.handler.CustomCmdHandler;
import cn.evole.mods.mcbot.util.locale.I18n;
import cn.evole.onebot.client.connection.ConnectFactory;
import cn.evole.onebot.client.core.Bot;
import cn.evole.onebot.client.handler.EventBus;
import cn.evole.onebot.sdk.util.FileUtils;
import net.fabricmc.api.ModInitializer;
//#if MC >= 11900
//$$ import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
//#else
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
//#endif
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

import java.nio.file.Path;
import java.util.concurrent.LinkedBlockingQueue;

public class McBot implements ModInitializer {

    public static MinecraftServer SERVER = null;
    public static Path CONFIG_FOLDER;
    public static Path CONFIG_FILE;

    public static LinkedBlockingQueue<String> blockingQueue;
    public static ConnectFactory service;
    public static EventBus bus;
    public static Bot bot;
    public static Thread app;

    public static McBot INSTANCE = new McBot();


    public MinecraftServer getServer() {
        return SERVER;
    }

    @Override
    public void onInitialize() {
        init();
        //#if MC >= 11900
        //$$ CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> ICmdEvent.register(dispatcher));
        //#else
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> ICmdEvent.register(dispatcher));
        //#endif

        ServerLifecycleEvents.SERVER_STARTING.register(this::onServerStarting);
        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);
        ServerLifecycleEvents.SERVER_STOPPING.register(this::onServerStopping);
        ServerLifecycleEvents.SERVER_STOPPED.register(this::onServerStopped);

        ServerTickEvents.END_SERVER_TICK.register(ITickEvent::register);

        IEvents.PLAYER_LOGGED_IN.register(IPlayerEvent::loggedIn);
        IEvents.PLAYER_LOGGED_OUT.register(IPlayerEvent::loggedOut);
        IEvents.PLAYER_ADVANCEMENT.register(IPlayerEvent::advancement);
        IEvents.PLAYER_DEATH.register(IPlayerEvent::death);

        IEvents.SERVER_CHAT.register(IChatEvent::register);
    }


    public void init() {
        CONFIG_FOLDER = Const.configDir.resolve("mcbot");
        FileUtils.checkFolder(CONFIG_FOLDER);
        CONFIG_FILE = CONFIG_FOLDER.resolve("config.toml");
        I18n.init();
        Runtime.getRuntime().addShutdownHook(new Thread(McBot::killOutThreads));
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
                Const.LOGGER.error("▌ §c机器人服务端未配置或未打开");
            }
        }
        bus = new EventBus(blockingQueue);//创建事件分发器
        CustomCmdHandler.INSTANCE.load();//自定义命令加载
        IBotEvent.init(bus);//事件监听
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
}
