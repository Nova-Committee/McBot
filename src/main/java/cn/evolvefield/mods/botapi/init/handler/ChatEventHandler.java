package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.BotApi;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lombok.val;
import net.minecraftforge.event.ServerChatEvent;



public class ChatEventHandler {
    public static ChatEventHandler INSTANCE = new ChatEventHandler();
    public void preInit() {
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onChatEvent(ServerChatEvent event) {
        val message = event.message;
        val player = event.player;
        val split = message.split(" ");
        if (ConfigHandler.cached() != null
                && ConfigHandler.cached().getStatus().isS_CHAT_ENABLE()
                && ConfigHandler.cached().getStatus().isSEND_ENABLED()
                && !message.contains("CICode")
        ) {
            if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                    BotApi.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(),
                            id,
                            String.format("[" + ConfigHandler.cached().getCmd().getMcPrefix() + "]<%s> %s",
                                    player.getDisplayName(),
                                    ConfigHandler.cached().getCmd().isMcChatPrefixEnable()
                                            && ConfigHandler.cached().getCmd().getMcChatPrefix().equals(split[0]) ? split[1] : message));
            } else {
                for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                    BotApi.bot.sendGroupMsg(
                            id,
                            String.format("[" + ConfigHandler.cached().getCmd().getMcPrefix() + "]<%s> %s",
                                    player.getDisplayName(),
                                    ConfigHandler.cached().getCmd().isMcChatPrefixEnable()
                                            && ConfigHandler.cached().getCmd().getMcChatPrefix().equals(split[0]) ? split[1] : message),
                            true);
            }


        }
    }
}
