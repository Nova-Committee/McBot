package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.BotApi;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@Mod.EventBusSubscriber()
public class ChatEventHandler {
    @SubscribeEvent
    public static void onChatEvent(ServerChatEvent event) {
        String[] split = event.getMessage().split(" ");
        if (BotApi.config != null
                && BotApi.config.getStatus().isS_CHAT_ENABLE()
                && BotApi.config.getStatus().isSEND_ENABLED()
                && BotApi.config.getCmd().getMcChatPrefix().equals(split[0])) {
            if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                for (String id : BotApi.config.getCommon().getChannelIdList())
                    BotApi.bot.sendGuildMsg(BotApi.config.getCommon().getGuildId(), id, String.format("[" + BotApi.config.getCmd().getQqPrefix() + "]<%s> %s", event.getPlayer().getDisplayName(), event.getMessage()));
            } else {
                for (long id : BotApi.config.getCommon().getGroupIdList())
                    BotApi.bot.sendGroupMsg(id, String.format("[" + BotApi.config.getCmd().getMcPrefix() + "]<%s> %s", event.getPlayer().getDisplayName(), event.getMessage()), true);
            }
        }
    }
}
