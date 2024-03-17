package cn.evole.mods.mcbot;

import cn.evole.mods.mcbot.config.ModConfig;
import cn.evole.mods.mcbot.util.onebot.MessageThread;
import net.fabricmc.loader.api.FabricLoader;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import net.minecraft.server.level.ServerPlayer;
//#if MC >= 11700
//$$ import org.slf4j.Logger;
//$$ import org.slf4j.LoggerFactory;
//#else
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
//#endif

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/1 16:58
 * Version: 1.0
 */
public class Const {
    public static final String MODID = "mcbot";
    //#if MC >= 11700
    //$$ public static final Logger LOGGER = LoggerFactory.getLogger("McBot");
    //#else
    public static final Logger LOGGER = LogManager.getLogger("McBot");
    //#endif
    public static boolean isShutdown = false;
    public static Path configDir = FabricLoader.getInstance().getConfigDir();
    public static Path gameDir = FabricLoader.getInstance().getGameDir();
    private static final MessageThread messageThread = new MessageThread();

    public static boolean isLoad(String modId){
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static void sendAllGroupMsg(String message){
        for (long id : ModConfig.INSTANCE.getCommon().getGroupIdList()){
            sendGroupMsg(id, message);
        }
    }

    public static void sendAllGroupMsg(Callable<String> message){
        for (long id : ModConfig.INSTANCE.getCommon().getGroupIdList()){
            sendGroupMsg(id, message);
        }
    }

    /**
     * 玩家在游戏里发送消息
     * @param message 消息
     * @param player 玩家
     */
    public static void sendAllGroupMsg(Callable<String> message, ServerPlayer player){
        for (long id : ModConfig.INSTANCE.getCommon().getGroupIdList()){
            messageThread.submit(id, message, false, player);
        }
    }

    public static void sendGroupMsg(long id, String message){
        messageThread.submit(id, message, false);
    }

    public static void sendGroupMsg(long id, Callable<String> message){
        messageThread.submit(id, message, false);
    }

    public static void shutdown() {
        messageThread.stop();
    }
}
