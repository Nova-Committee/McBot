package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.cmd.CmdApi;
import cn.evolvefield.onebot.sdk.listener.SimpleListener;
import cn.evolvefield.onebot.sdk.model.event.EventDispatchers;
import cn.evolvefield.onebot.sdk.model.event.message.GroupMessageEvent;
import cn.evolvefield.onebot.sdk.model.event.message.GuildMessageEvent;
import cn.evolvefield.onebot.sdk.model.event.notice.GroupDecreaseNoticeEvent;
import cn.evolvefield.onebot.sdk.model.event.notice.GroupIncreaseNoticeEvent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/20 8:13
 * Version: 1.0
 */
public class BotEventHandler {
    public static void init(EventDispatchers dispatchers) {

        GroupChatHandler(dispatchers);
        GroupCmdsHandler(dispatchers);
        GroupMemberNotice(dispatchers);
        GuildChatHandler(dispatchers);
        GuildCmdsHandler(dispatchers);
        dispatchers.start(10);//线程组处理任务
    }

    private static void GroupChatHandler(EventDispatchers dispatchers) {
        dispatchers.addListener(new SimpleListener<GroupMessageEvent>() {
            @Override
            public void onMessage(GroupMessageEvent event) {
                if (BotApi.config.getCommon().getGroupIdList().contains(event.getGroupId())
                        && !event.getMessage().startsWith(BotApi.config.getCmd().getCommandStart())
                        && BotApi.config.getStatus().isRECEIVE_ENABLED()
                        && !event.getMessage().contains("[CQ:")
                        && BotApi.config.getStatus().isR_CHAT_ENABLE()
                        && event.getUserId() != BotApi.config.getCommon().getBotId()) {
                    String toSend = String.format("§b[§lQQ§r§b]§a<%s>§f %s", event.getSender().getNickname(), event.getMessage());
                    TickEventHandler.getToSendQueue().add(toSend);
                }
            }
        });
    }

    private static void GroupCmdsHandler(EventDispatchers dispatchers) {
        dispatchers.addListener(new SimpleListener<GroupMessageEvent>() {
            @Override
            public void onMessage(GroupMessageEvent event) {
                if (BotApi.config.getCommon().getGroupIdList().contains(event.getGroupId())
                        && BotApi.config.getStatus().isRECEIVE_ENABLED()
                        && event.getMessage().startsWith(BotApi.config.getCmd().getCommandStart())
                        && BotApi.config.getStatus().isR_COMMAND_ENABLED()) {
                    CmdApi.invokeCommandGroup(event);
                }
            }
        });
    }

    private static void GroupMemberNotice(EventDispatchers dispatchers) {
        dispatchers.addListener(new SimpleListener<GroupDecreaseNoticeEvent>() {
            @Override
            public void onMessage(GroupDecreaseNoticeEvent event) {
                if (BotApi.config.getCommon().getGroupIdList().contains(event.getGroupId())
                        && BotApi.config.getStatus().isRECEIVE_ENABLED()
                        && BotApi.config.getStatus().isS_WELCOME_ENABLE()) {
                    BotApi.bot.sendGroupMsg(event.getGroupId(), BotApi.config.getCmd().getWelcomeNotice(), true);
                }
            }
        });

        dispatchers.addListener(new SimpleListener<GroupIncreaseNoticeEvent>() {
            @Override
            public void onMessage(GroupIncreaseNoticeEvent event) {
                if (BotApi.config.getCommon().getGroupIdList().contains(event.getGroupId())
                        && BotApi.config.getStatus().isRECEIVE_ENABLED()
                        && BotApi.config.getStatus().isS_WELCOME_ENABLE()) {
                    BotApi.bot.sendGroupMsg(event.getGroupId(), BotApi.config.getCmd().getLeaveNotice(), true);
                }
            }
        });

    }

    private static void GuildChatHandler(EventDispatchers dispatchers) {
        dispatchers.addListener(new SimpleListener<GuildMessageEvent>() {
            @Override
            public void onMessage(GuildMessageEvent event) {
                if (event.getGuildId().equals(BotApi.config.getCommon().getGuildId())
                        && BotApi.config.getCommon().getChannelIdList().contains(event.getChannelId())
                        && !event.getMessage().startsWith(BotApi.config.getCmd().getCommandStart())
                ) {
                    if (!event.getMessage().contains("[CQ:") && BotApi.config.getStatus().isR_CHAT_ENABLE()
                    ) {
                        String toSend = String.format("§b[§lQQ§r§b]§a<%s>§f %s", event.getSender().getNickname(), event.getMessage());
                        TickEventHandler.getToSendQueue().add(toSend);
                    }
                }
            }
        });
    }

    private static void GuildCmdsHandler(EventDispatchers dispatchers) {
        dispatchers.addListener(new SimpleListener<GuildMessageEvent>() {
            @Override
            public void onMessage(GuildMessageEvent event) {
                if (BotApi.config.getCommon().getChannelIdList().contains(event.getChannelId())
                        && BotApi.config.getStatus().isRECEIVE_ENABLED()
                        && event.getMessage().startsWith(BotApi.config.getCmd().getCommandStart())
                        && BotApi.config.getStatus().isR_COMMAND_ENABLED()) {
                    CmdApi.invokeCommandGuild(event);
                }
            }
        });
    }


}
