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
        MessageThread thread;
        if (ModConfig.INSTANCE.getBotConfig().getMsgType().equalsIgnoreCase("string")){
            thread = new MessageThread(id, message, false);
        }
        else {
            thread = new MessageThread(id, BotUtils.rawToJson(message), false);
        }
        thread.start();
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
    private long groupIDInt;
    private String guildIDString;
    private String messageString;
    private JsonArray messageArray;
    private boolean autoEscape;
    private String channelIDString;
    private final short mode;

    MessageThread(long groupId, String msg, boolean autoEscape) {
        this.mode = 0;
        this.groupIDInt = groupId;
        this.messageString = msg;
        this.autoEscape = autoEscape;
    }

    MessageThread(long groupId, JsonArray msg, boolean autoEscape) {
        this.mode = 1;
        this.groupIDInt = groupId;
        this.messageArray = msg;
        this.autoEscape = autoEscape;
    }
    MessageThread(String guildID, String channelID, String message) {
        this.mode = 2;
        this.guildIDString = guildID;
        this.channelIDString = channelID;
        this.messageString = message;
    }

    MessageThread(String guildID, String channelID, JsonArray message) {
        this.mode = 3;
        this.guildIDString = guildID;
        this.channelIDString = channelID;
        this.messageArray = message;
    }

    public void run() {
        switch (mode) {
            case 0 -> McBot.bot.sendGroupMsg(groupIDInt, messageString, autoEscape);
            case 1 -> McBot.bot.sendGroupMsg(groupIDInt, messageArray, autoEscape);
            case 2 -> McBot.bot.sendGuildMsg(guildIDString, channelIDString, messageString);
            case 3 -> McBot.bot.sendGuildMsg(guildIDString, channelIDString, messageArray);
        }
    }

    public void start() {
        Const.LOGGER.info(String.format("转发游戏消息: %s", messageString!= null ? messageString : messageArray));
        Thread thread = new Thread(this, "MessageThread");
        thread.start();
    }
}
