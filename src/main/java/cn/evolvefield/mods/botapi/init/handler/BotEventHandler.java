package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.cmd.CmdApi;
import cn.evolvefield.mods.botapi.util.onebot.CQUtils;
import cn.evolvefield.onebot.client.handler.EventBus;
import cn.evolvefield.onebot.client.listener.SimpleEventListener;
import cn.evolvefield.onebot.sdk.event.message.GroupMessageEvent;
import cn.evolvefield.onebot.sdk.event.message.GuildMessageEvent;
import cn.evolvefield.onebot.sdk.event.meta.LifecycleMetaEvent;
import cn.evolvefield.onebot.sdk.event.notice.group.GroupDecreaseNoticeEvent;
import cn.evolvefield.onebot.sdk.event.notice.group.GroupIncreaseNoticeEvent;
import lombok.val;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/20 8:13
 * Version: 1.0
 */
public class BotEventHandler {
    public static void init(EventBus dispatchers) {

        GroupChatHandler(dispatchers);
        GroupCmdsHandler(dispatchers);
        GroupNoticeHandler(dispatchers);
        GuildChatHandler(dispatchers);
        GuildCmdsHandler(dispatchers);
        LifeCycleHandler(dispatchers);
    }

    private static void GroupChatHandler(EventBus dispatchers) {
        dispatchers.addListener(new SimpleEventListener<GroupMessageEvent>() {
            @Override
            public void onMessage(GroupMessageEvent event) {
                if (ConfigHandler.cached().getCommon().getGroupIdList().contains(event.getGroupId())//判断是否是配置中的群
                        && !event.getMessage().startsWith(ConfigHandler.cached().getCmd().getCommandStart())//过滤命令前缀
                        && ConfigHandler.cached().getStatus().isRECEIVE_ENABLED()//总接受开关
                        && ConfigHandler.cached().getStatus().isR_CHAT_ENABLE()//接受聊天开关
                        && event.getUserId() != ConfigHandler.cached().getCommon().getBotId()//过滤机器人
                ) {

                    String send = CQUtils.replace(event.getMessage());//暂时匹配仅符合字符串聊天内容与图片

                    if (ConfigHandler.cached().getCmd().isQqChatPrefixEnable()) {
                        var split = event.getMessage().split(" ");
                        if (ConfigHandler.cached().getCmd().getQqChatPrefix().equals(split[0])) //指定前缀发送
                            send = split[1];
                        else return;
                    }
                    String toSend = String.format("§b[§l%s§r(§5%s§b)]§a<%s>§f %s", ConfigHandler.cached().getCmd().getQqPrefix(), event.getGroupId(), event.getSender().getNickname(), send);
                    TickEventHandler.getToSendQueue().add(toSend);
                }
            }
        });
    }

    private static void GroupCmdsHandler(EventBus dispatchers) {
        dispatchers.addListener(new SimpleEventListener<GroupMessageEvent>() {
            @Override
            public void onMessage(GroupMessageEvent event) {
                if (ConfigHandler.cached().getCommon().getGroupIdList().contains(event.getGroupId())
                        && event.getMessage().startsWith(ConfigHandler.cached().getCmd().getCommandStart())//命令前缀
                        && ConfigHandler.cached().getStatus().isRECEIVE_ENABLED()//总接受开关
                        && ConfigHandler.cached().getStatus().isR_COMMAND_ENABLED()//接受命令开关
                ) {
                    CmdApi.invokeCommandGroup(event);
                }
            }
        });
    }

    private static void GroupNoticeHandler(EventBus dispatchers) {
        dispatchers.addListener(new SimpleEventListener<GroupDecreaseNoticeEvent>() {
            @Override
            public void onMessage(GroupDecreaseNoticeEvent event) {
                if (ConfigHandler.cached().getCommon().getGroupIdList().contains(event.getGroupId())
                        && ConfigHandler.cached().getStatus().isRECEIVE_ENABLED()
                        && ConfigHandler.cached().getStatus().isS_QQ_WELCOME_ENABLE()) {
                    BotApi.bot.sendGroupMsg(event.getGroupId(), ConfigHandler.cached().getCmd().getWelcomeNotice(), true);
                }
            }
        });

        dispatchers.addListener(new SimpleEventListener<GroupIncreaseNoticeEvent>() {
            @Override
            public void onMessage(GroupIncreaseNoticeEvent event) {
                if (ConfigHandler.cached().getCommon().getGroupIdList().contains(event.getGroupId())
                        && ConfigHandler.cached().getStatus().isRECEIVE_ENABLED()
                        && ConfigHandler.cached().getStatus().isS_QQ_LEAVE_ENABLE()) {
                    BotApi.bot.sendGroupMsg(event.getGroupId(), ConfigHandler.cached().getCmd().getLeaveNotice(), true);
                }
            }
        });

    }

    private static void GuildChatHandler(EventBus dispatchers) {
        dispatchers.addListener(new SimpleEventListener<GuildMessageEvent>() {
            @Override
            public void onMessage(GuildMessageEvent event) {
                if (event.getGuildId().equals(ConfigHandler.cached().getCommon().getGuildId())
                        && ConfigHandler.cached().getCommon().getChannelIdList().contains(event.getChannelId())
                        && !event.getMessage().startsWith(ConfigHandler.cached().getCmd().getCommandStart())//过滤命令前缀
                        && ConfigHandler.cached().getStatus().isRECEIVE_ENABLED()//总接受开关
                        && ConfigHandler.cached().getStatus().isR_CHAT_ENABLE()//接受聊天开关
                        && event.getUserId() != ConfigHandler.cached().getCommon().getBotId()
                ) {

                    String send = CQUtils.replace(event.getMessage());//暂时匹配仅符合字符串聊天内容与图片
                    if (ConfigHandler.cached().getCmd().isQqChatPrefixEnable()) {
                        var split = event.getMessage().split(" ");
                        if (ConfigHandler.cached().getCmd().getQqChatPrefix().equals(split[0])) //指定前缀发送
                            send = split[1];
                        else return;
                    }
                    String toSend = String.format("§b[§l%s§r(§5%s§b)]§a<%s>§f %s", ConfigHandler.cached().getCmd().getGuildPrefix(), event.getChannelId(), event.getSender().getNickname(), send);
                    TickEventHandler.getToSendQueue().add(toSend);

                }
            }
        });
    }

    private static void GuildCmdsHandler(EventBus dispatchers) {
        dispatchers.addListener(new SimpleEventListener<GuildMessageEvent>() {
            @Override
            public void onMessage(GuildMessageEvent event) {
                if (ConfigHandler.cached().getCommon().getChannelIdList().contains(event.getChannelId())
                        && event.getMessage().startsWith(ConfigHandler.cached().getCmd().getCommandStart())//命令前缀
                        && ConfigHandler.cached().getStatus().isRECEIVE_ENABLED()//总接受开关
                        && ConfigHandler.cached().getStatus().isR_COMMAND_ENABLED()//接受命令开关
                ) {
                    CmdApi.invokeCommandGuild(event);
                }
            }
        });
    }


    private static void LifeCycleHandler(EventBus dispatchers) {
        dispatchers.addListener(new SimpleEventListener<LifecycleMetaEvent>() {
            @Override
            public void onMessage(LifecycleMetaEvent event) {
                if (!event.getSubType().equals("connect")) return;
                if (!ConfigHandler.cached().getCommon().getGroupIdList().isEmpty()
                ) {
                    for (val id : ConfigHandler.cached().getCommon().getGroupIdList()){
                        BotApi.bot.sendGroupMsg(id, "▌ 群服互联已连接 ┈━═☆", true);
                    }
                }
                if (!ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()
                ) {
                    for (val id : ConfigHandler.cached().getCommon().getChannelIdList()){
                        BotApi.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId() , id, "▌ 群服互联已连接 ┈━═☆");
                    }
                }
            }
        });
    }


}
