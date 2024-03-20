package cn.evole.mods.mcbot;

import cn.evole.mods.mcbot.config.ModConfig;
import cn.evole.mods.mcbot.core.event.IBotEvent;
import cn.evole.mods.mcbot.core.event.ITickEvent;
import cn.evole.mods.mcbot.util.onebot.MessageThread;
import cn.evole.onebot.client.OneBotClient;
import cn.evole.onebot.sdk.action.ActionPath;
import com.google.gson.JsonObject;
import lombok.val;
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

//#if MC < 11900
import net.minecraft.network.chat.TextComponent;
//#else
//$$ import net.minecraft.network.chat.Component;
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
    public static final MessageThread messageThread = new MessageThread();

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

    /**
     * 自定义请求 (不应清理)
     * @param action 请求类型
     * @param params 参数
     */
    public static void customRequest(ActionPath action, JsonObject params){
        messageThread.submit(action, params);
    }

    /**
     * 向游戏中的所有人发送消息
     */
    public static void sendAllPlayerMsg(String message){
        //#if MC >= 11900
        //$$ val toSend = Component.literal(message);
        //#else
        val toSend = new TextComponent(message);
        //#endif

        ITickEvent.getSendQueue().add(toSend);
    }

    /**
     * WS连接
     */
    public static void wsConnect(){
        McBot.onebot.close();//关闭线程
        McBot.onebot = null;//强制为null
        McBot.onebot = OneBotClient.create(ModConfig.INSTANCE.getBotConfig().build()).open().registerEvents(new IBotEvent());//重新实例化
        ModConfig.INSTANCE.getStatus().setREnable(true);
        ModConfig.INSTANCE.getCommon().setEnable(true);
        ModConfig.INSTANCE.save();
        McBot.connected = true;
    }


    public static void shutdown() {
        messageThread.stop();
    }
}
