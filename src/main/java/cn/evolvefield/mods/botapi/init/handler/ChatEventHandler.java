package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.message.SendMessage;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@Mod.EventBusSubscriber
public class ChatEventHandler {
    @SubscribeEvent
    public static void onChatEvent(ServerChatEvent event) {
        if (BotApi.config.getStatus().isS_CHAT_ENABLE() && BotApi.config.getStatus().isSEND_ENABLED()) {
            if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                for (String id : BotApi.config.getCommon().getChannelIdList())
                    SendMessage.ChannelGroup(BotApi.config.getCommon().getGuildId(), id, String.format("[" + BotApi.config.getCmd().getQqPrefix() + "]<%s> %s", event.getUsername(), event.getMessage()));
            } else {
                SendMessage.Group(BotApi.config.getCommon().getGroupId(), String.format("[" + BotApi.config.getCmd().getQqPrefix() + "]<%s> %s", event.getUsername(), event.getMessage()));
            }
        }
    }
}
