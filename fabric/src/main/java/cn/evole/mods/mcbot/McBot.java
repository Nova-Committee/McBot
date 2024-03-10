package cn.evole.mods.mcbot;

import cn.evole.mods.mcbot.core.event.*;
import cn.evole.mods.mcbot.core.data.UserBindApi;
import cn.evole.mods.mcbot.core.data.ChatRecordApi;
import cn.evole.mods.mcbot.init.callbacks.IEvents;
import cn.evole.mods.mcbot.config.ModConfig;
import cn.evole.mods.mcbot.init.handler.CustomCmdHandler;
import cn.evole.mods.mcbot.util.lib.LibUtils;
import cn.evole.mods.mcbot.util.locale.I18n;
import cn.evole.onebot.client.OneBotClient;
import cn.evole.onebot.sdk.util.FileUtils;
import cn.evole.onebot.sdk.util.java.Assert;
import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//#if MC >= 11900
//$$ import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
//#else
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
//#endif

//兼容vanish
import cn.evole.mods.mcbot.init.compat.vanish.VanishCompat;



public class McBot implements ModInitializer {

    @Getter
    public static MinecraftServer SERVER = null;
    public static Path CONFIG_FOLDER;
    public static Path CONFIG_FILE;
    public static Path LIB_FOLDER;

    public static McBot INSTANCE = new McBot();

    public static OneBotClient onebot;
    public static ExecutorService CQUtilsExecutor;

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

        VanishCompat.init();
    }


    public void init() {
        CONFIG_FOLDER = Const.gameDir.resolve("mcbot");
        FileUtils.checkFolder(CONFIG_FOLDER);
        LIB_FOLDER = CONFIG_FOLDER.resolve("libs");
        FileUtils.checkFolder(LIB_FOLDER);
        CONFIG_FILE = CONFIG_FOLDER.resolve("config.toml");
        LibUtils.create(LIB_FOLDER, "libs.txt").download();
        I18n.init();
        UserBindApi.load(CONFIG_FOLDER);
        ChatRecordApi.load(CONFIG_FOLDER);
    }

    public void onServerStarting(MinecraftServer server) {
        SERVER = server;//获取服务器实例
    }

    public void onServerStarted(MinecraftServer server) {
        if (ModConfig.INSTANCE.getCommon().isAutoOpen()) {
            onebot = OneBotClient.create(ModConfig.INSTANCE.getBotConfig().build()).open().registerEvents(new IBotEvent());
        }
        if (onebot != null) onebot.getEventsBus().register(new IBotEvent());
        CustomCmdHandler.INSTANCE.load();//自定义命令加载
        CQUtilsExecutor = Executors.newSingleThreadExecutor();  // 创建CQ码处理线程池
    }

    public void onServerStopping(MinecraftServer server) {
        Const.isShutdown = true;
        Const.LOGGER.info("▌ §c正在关闭群服互联");
        UserBindApi.save(CONFIG_FOLDER);
        ChatRecordApi.save(CONFIG_FOLDER);
        CustomCmdHandler.INSTANCE.clear();//自定义命令持久层清空
    }

    public void onServerStopped(MinecraftServer server) {
        if (onebot != null) onebot.close();
        CQUtilsExecutor.shutdownNow();
    }

}
