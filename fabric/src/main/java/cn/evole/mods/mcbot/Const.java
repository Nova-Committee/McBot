package cn.evole.mods.mcbot;

import cn.evole.mods.mcbot.init.config.ModConfig;
import cn.evole.onebot.sdk.util.BotUtils;
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
        if (ModConfig.INSTANCE.getBotConfig().getMsgType().equalsIgnoreCase("string")){
            McBot.bot.sendGuildMsg(guildId, channelId, message);
        }
        else {
            McBot.bot.sendGuildMsg(guildId, channelId, BotUtils.rawToJson(message));
        }
    }

}
