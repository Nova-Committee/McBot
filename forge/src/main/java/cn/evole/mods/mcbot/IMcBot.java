package cn.evole.mods.mcbot;

import cn.evole.mods.mcbot.config.ModConfig;
import cn.evole.mods.mcbot.core.data.ChatRecordApi;
import cn.evole.mods.mcbot.core.data.UserBindApi;
import cn.evole.mods.mcbot.core.event.*;
import cn.evole.mods.mcbot.init.handler.CustomCmdHandler;
import cn.evole.mods.mcbot.util.FileUtil;
import cn.evole.mods.mcbot.util.lib.LibUtils;
import cn.evole.mods.mcbot.util.locale.I18n;
import cn.evole.onebot.client.OneBotClient;
import net.minecraft.advancements.Advancement;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IMcBot {
    public static MinecraftServer SERVER = null;
    public static Path CONFIG_FOLDER;
    public static Path CONFIG_FILE;
    public static Path LIB_FOLDER;

    public static McBot INSTANCE = new McBot();

    public static OneBotClient onebot;
    public static ExecutorService CQUtilsExecutor;

    public MinecraftServer getServer() {
        return SERVER;
    }

    public IMcBot() {
        init();
    }

    public void init() {
        CONFIG_FOLDER = Const.gameDir.resolve("mcbot");
        FileUtil.checkFolder(CONFIG_FOLDER);
        LIB_FOLDER = CONFIG_FOLDER.resolve("libs");
        FileUtil.checkFolder(LIB_FOLDER);
        LibUtils.create(LIB_FOLDER, "libs.txt").download();
        CONFIG_FILE = CONFIG_FOLDER.resolve("config.toml");
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
        CQUtilsExecutor.shutdownNow();
        if (onebot != null) onebot.close();
    }

    public void onServerTick(MinecraftServer server) {
        ITickEvent.register(server);
    }
    public void onServerChat(Level level, ServerPlayer player, String msg) {
        IChatEvent.register(player, msg);
    }
    public void onPlayerLogIn(Level level, ServerPlayer player) {
        IPlayerEvent.loggedIn(level, player);
    }
    public void onPlayerLogOut(Level level, ServerPlayer player) {
        IPlayerEvent.loggedOut(level, player);
    }
    public void onPlayerDeath(Level level, DamageSource source, ServerPlayer player) {
        IPlayerEvent.death(source, player);
    }
    public void onPlayerAdvancement(Level level, Player player, Advancement advancement) {
        IPlayerEvent.advancement(player, advancement);
    }
}
