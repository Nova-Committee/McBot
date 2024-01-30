package cn.evole.mods.mcbot;

import cn.evole.mods.mcbot.init.config.ModConfig;
import cn.evole.onebot.sdk.util.BotUtils;
import com.google.gson.JsonArray;
import net.fabricmc.loader.api.FabricLoader;
import java.nio.file.Path;

//#if MC >= 11700
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//#else
//$$ import org.apache.logging.log4j.Logger;
//$$ import org.apache.logging.log4j.LogManager;
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
    public static final Logger LOGGER = LoggerFactory.getLogger("McBot");
    //#else
    //$$ public static final Logger LOGGER = LogManager.getLogger("McBot");
    //#endif
    public static boolean isShutdown = false;
    public static Path configDir = FabricLoader.getInstance().getConfigDir();

    public static boolean isLoad(String modId){
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static void sendGroupMsg(String message){
        for (long id : ModConfig.INSTANCE.getCommon().getGroupIdList()){
            groupMsg(id, message);
        }
    }

    public static void groupMsg(long id, String message){
        if (ModConfig.INSTANCE.getBotConfig().getMsgType().equalsIgnoreCase("string")){
            McBot.bot.sendGroupMsg(id, message, false);
        }
        else {
            McBot.bot.sendGroupMsg(id, BotUtils.rawToJson(message), false);
        }
    }

    public static void sendGuildMsg(String message){
        for (String id : ModConfig.INSTANCE.getCommon().getChannelIdList()){
            guildMsg(ModConfig.INSTANCE.getCommon().getGuildId(), id, message);
        }
    }

    public static void guildMsg(String guildId, String channelId, String message){
        // 发送消息时实际上所调用的函数。
        MessageThread thread;
        if (ModConfig.INSTANCE.getBotConfig().getMsgType().equalsIgnoreCase("string")){
            thread = new MessageThread(guildId, channelId, message);
        }
        else {
            thread = new MessageThread(guildId, channelId, BotUtils.rawToJson(message));
        }
        thread.start();
    }
}

class MessageThread extends Thread {
    private final String guildID;
    private final String channelID;
    private String messageString = null;
    private JsonArray messageJson = null;

    MessageThread(String guildID, String channelID, String message) {
        this.guildID = guildID;
        this.channelID = channelID;
        this.messageString = message;
    }

    MessageThread(String guildID, String channelID, JsonArray message) {
        this.guildID = guildID;
        this.channelID = channelID;
        this.messageJson = message;
    }

    public void run() {
        if (this.messageString != null) {
            McBot.bot.sendGuildMsg(this.guildID, this.channelID, this.messageString);
        } else {
            McBot.bot.sendGuildMsg(this.guildID, this.channelID, this.messageJson);
        }
    }

    public void start() {
        Const.LOGGER.info(String.format("转发游戏消息: %s", messageString!= null ? messageString : messageJson));
        Thread thread = new Thread(this, "MessageThread");
    }
}
