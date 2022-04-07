package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.events.GroupMessageEvent;
import cn.evolvefield.mods.botapi.api.events.NoticeEvent;
import cn.evolvefield.mods.botapi.api.events.PrivateMessageEvent;
import cn.evolvefield.mods.botapi.api.events.RequestEvent;
import cn.evolvefield.mods.botapi.api.message.MiraiMessage;
import cn.evolvefield.mods.botapi.api.message.SendMessage;
import cn.evolvefield.mods.botapi.core.bot.BotData;
import cn.evolvefield.mods.botapi.core.bot.Invoke;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/18 19:03
 * Version: 1.0
 */
@Mod.EventBusSubscriber
public class BotEventHandler {
    @SubscribeEvent
    public static void GroupEventHandler(GroupMessageEvent event) {

        if (event.getGroupId() == BotApi.config.getCommon().getGroupId() && BotApi.config.getStatus().isRECEIVE_ENABLED()) {

            if (BotData.getBotFrame().equalsIgnoreCase("cqhttp")) {
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("收到群" + event.getGroupId() + "发送消息" + event.getMessage());
                }
                if (event.getMessage().startsWith(BotApi.config.getCmd().getCommandStart()) && BotApi.config.getStatus().isR_COMMAND_ENABLED()) {

                    Invoke.invokeCommand(event);

                } else if (!event.getMessage().startsWith("[CQ:") && BotApi.config.getStatus().isR_CHAT_ENABLE()) {
                    String toSend = String.format("§b[§lQQ§r§b]§a<%s>§f %s", event.getNickName(), event.getMessage());
                    TickEventHandler.getToSendQueue().add(toSend);
                }
            } else if (BotData.getBotFrame().equalsIgnoreCase("mirai")) {
                if (BotApi.config.getCommon().isDebuggable()) {
                    for (MiraiMessage msg : event.getMiraiMessage()) {
                        msg.deBug();
                    }
                    System.out.println(event.getMiraiMessage().get(1).getText());
                }
                if (event.getMiraiMessage().get(1).getText().startsWith(BotApi.config.getCmd().getCommandStart()) && BotApi.config.getStatus().isR_COMMAND_ENABLED()) {

                    Invoke.invokeCommand(event);

                } else if (!event.getMiraiMessage().get(1).getText().startsWith(BotApi.config.getCmd().getCommandStart()) && BotApi.config.getStatus().isR_CHAT_ENABLE()) {
                    String toSend = String.format("§b[§lQQ§r§b]§a<%s>§f %s", event.getNickName(), event.getMiraiMessage().get(1).getText());
                    TickEventHandler.getToSendQueue().add(toSend);
                }
            } else {
                System.out.println("错误");
            }

        }
    }

    @SubscribeEvent
    public static void PrivateEventHandler(PrivateMessageEvent event) {

    }

    @SubscribeEvent
    public static void NoticeEventHandler(NoticeEvent event) {
        if (BotApi.config.getStatus().isS_WELCOME_ENABLE()
                && BotApi.config.getStatus().isSEND_ENABLED()
                && event.getGroup_id() == BotApi.config.getCommon().getGroupId()) {
            if (event.getNoticeType().equals("group_increase")) {
                SendMessage.Group(BotApi.config.getCommon().getGroupId(), BotApi.config.getCmd().getWelcomeNotice());
            } else if (event.getNoticeType().equals("group_decrease")) {
                SendMessage.Group(BotApi.config.getCommon().getGroupId(), BotApi.config.getCmd().getLeaveNotice());
            }
        }
    }

    @SubscribeEvent
    public static void RequestsEventHandler(RequestEvent event) {

    }
}
