package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.cmd.CmdApi;
import cn.evolvefield.mods.botapi.util.onebot.CQUtils;
import cn.evolvefield.onebot.client.handler.EventBus;
import cn.evolvefield.onebot.client.listener.SimpleListener;
import cn.evolvefield.onebot.sdk.event.message.GroupMessageEvent;
import cn.evolvefield.onebot.sdk.event.message.GuildMessageEvent;
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
        GroupMemberNotice(dispatchers);
        GuildChatHandler(dispatchers);
        GuildCmdsHandler(dispatchers);
        dispatchers.start(4);//线程组处理任务
    }

    private static void GroupChatHandler(EventBus dispatchers) {
        dispatchers.addListener(new SimpleListener<GroupMessageEvent>() {
            @Override
            public void onMessage(GroupMessageEvent event) {
                if (ConfigHandler.cached().getCommon().getGroupIdList().contains(event.getGroupId())//判断是否是配置中的群
                        && !event.getMessage().startsWith(ConfigHandler.cached().getCmd().getCommandStart())//过滤命令前缀
                        && ConfigHandler.cached().getStatus().isRECEIVE_ENABLED()//总接受开关
                        && ConfigHandler.cached().getStatus().isR_CHAT_ENABLE()//接受聊天开关
                        && event.getUserId() != ConfigHandler.cached().getCommon().getBotId()
                ) {

                    String send = CQUtils.replace(event.getMessage());//暂时匹配仅符合字符串聊天内容与图片

                    if (ConfigHandler.cached().getCmd().isQqChatPrefixEnable()) {
                        val split = event.getMessage().split(" ");
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
        dispatchers.addListener(new SimpleListener<GroupMessageEvent>() {
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

    private static void GroupMemberNotice(EventBus dispatchers) {
        dispatchers.addListener(new SimpleListener<GroupDecreaseNoticeEvent>() {
            @Override
            public void onMessage(GroupDecreaseNoticeEvent event) {
                if (ConfigHandler.cached().getCommon().getGroupIdList().contains(event.getGroupId())
                        && ConfigHandler.cached().getStatus().isRECEIVE_ENABLED()
                        && ConfigHandler.cached().getStatus().isS_QQ_WELCOME_ENABLE()) {
                    BotApi.bot.sendGroupMsg(event.getGroupId(), ConfigHandler.cached().getCmd().getWelcomeNotice(), true);
                }
            }
        });

        dispatchers.addListener(new SimpleListener<GroupIncreaseNoticeEvent>() {
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
        dispatchers.addListener(new SimpleListener<GuildMessageEvent>() {
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
                        val split = event.getMessage().split(" ");
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
        dispatchers.addListener(new SimpleListener<GuildMessageEvent>() {
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


}
