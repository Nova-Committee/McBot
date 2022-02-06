package cn.evolvefield.mods.botapi.init.event;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.SendMessage;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.ServerChatEvent;




public class ChatEventHandler {

    public void preInit() {
        FMLCommonHandler.instance().bus().register(this);
    }


    @SubscribeEvent
    public void onChatEvent(ServerChatEvent event) {
        if (BotApi.config.getCommon().isS_CHAT_ENABLE() && BotApi.config.getCommon().isSEND_ENABLED()) {
            SendMessage.Group(BotApi.config.getCommon().getGroupId(),String.format("[MC]<%s> %s", event.username, event.message));
        }
    }
}