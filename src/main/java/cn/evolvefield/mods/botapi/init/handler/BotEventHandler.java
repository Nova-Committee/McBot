package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.message.SendMessage;
import cn.evolvefield.mods.botapi.core.bot.Invoke;
import cn.evolvefield.mods.botapi.init.callbacks.BotEvents;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/20 8:13
 * Version: 1.0
 */
public class BotEventHandler {
    public static void init() {
        BotEvents.GROUP_MSG_EVENT.register((event) -> {

            if (event.getGroupId() == BotApi.config.getCommon().getGroupId() && BotApi.config.getCommon().isRECEIVE_ENABLED()) {
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("收到群" + event.getGroupId() + "发送消息" + event.getMessage());
                }
                if (event.getMessage().startsWith(BotApi.config.getCommon().getCommandStart()) && BotApi.config.getCommon().isR_COMMAND_ENABLED()) {

                    Invoke.invokeCommand(event);

                } else if (!event.getMessage().startsWith("[CQ:") && BotApi.config.getCommon().isR_CHAT_ENABLE()) {
                    String toSend = String.format("§b[§lQQ§r§b]§a<%s>§f %s", event.getNickName(), event.getMessage());
                    TickEventHandler.getToSendQueue().add(toSend);
                }
            }
        });

        BotEvents.PRIVATE_MSG_EVENT.register((event) -> {


        });

        BotEvents.NOTICE_MSG_EVENT.register((event) -> {
            if (BotApi.config.getCommon().isS_WELCOME_ENABLE()
                    && BotApi.config.getCommon().isSEND_ENABLED()
                    && event.getGroup_id() == BotApi.config.getCommon().getGroupId()) {
                if (event.getNoticeType().equals("group_increase")) {
                    SendMessage.Group(BotApi.config.getCommon().getGroupId(), BotApi.config.getCommon().getWelcomeNotice());
                } else if (event.getNoticeType().equals("group_decrease")) {
                    SendMessage.Group(BotApi.config.getCommon().getGroupId(), BotApi.config.getCommon().getLeaveNotice());
                }
            }

        });


        BotEvents.REQUEST_MSG_EVENT.register((event) -> {


        });
    }

}
