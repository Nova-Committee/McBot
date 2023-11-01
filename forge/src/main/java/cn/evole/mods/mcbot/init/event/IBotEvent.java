package cn.evole.mods.mcbot.init.event;

import cn.evole.mods.mcbot.IMcBot;
import cn.evole.mods.mcbot.cmds.CmdApi;
import cn.evole.mods.mcbot.util.onebot.CQUtils;
import cn.evole.onebot.client.handler.EventBus;
import cn.evole.onebot.client.listener.SimpleEventListener;
import cn.evole.onebot.sdk.event.message.GroupMessageEvent;
import cn.evole.onebot.sdk.event.message.GuildMessageEvent;
import cn.evole.onebot.sdk.event.meta.LifecycleMetaEvent;
import cn.evole.onebot.sdk.event.notice.group.GroupDecreaseNoticeEvent;
import cn.evole.onebot.sdk.event.notice.group.GroupIncreaseNoticeEvent;
import cn.evole.onebot.sdk.util.MsgUtils;
import lombok.val;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/20 8:13
 * Version: 1.0
 */
public class IBotEvent {
    public static void init(EventBus dispatchers) {

        if (IMcBot.config.getCommon().isEnable())
        {
            GroupChatHandler(dispatchers);
            GroupCmdsHandler(dispatchers);
            GroupNoticeHandler(dispatchers);
            GuildChatHandler(dispatchers);
            GuildCmdsHandler(dispatchers);
            LifeCycleHandler(dispatchers);
        }

    }

    private static void GroupChatHandler(EventBus dispatchers) {
        dispatchers.addListener(new SimpleEventListener<GroupMessageEvent>() {
            @Override
            public void onMessage(GroupMessageEvent event) {
                if (IMcBot.config.getCommon().getGroupIdList().contains(event.getGroupId())//判断是否是配置中的群
                        && !event.getMessage().startsWith(IMcBot.config.getCmd().getCmdStart())//过滤命令前缀
                        && IMcBot.config.getStatus().isREnable()//总接受开关
                        && IMcBot.config.getStatus().isRChatEnable()//接受聊天开关
                        && event.getUserId() != IMcBot.config.getCommon().getBotId()//过滤机器人
                ) {

                    String send = CQUtils.replace(event.getMessage());//暂时匹配仅符合字符串聊天内容与图片

                    if (IMcBot.config.getCmd().isQqChatPrefixOn()) {
                        val split = event.getMessage().split(" ");
                        if (IMcBot.config.getCmd().getQqChatPrefix().equals(split[0])) //指定前缀发送
                            send = split[1];
                        else return;
                    }
                    String nick = IMcBot.bot.getGroupMemberInfo(event.getGroupId(), event.getUserId(), true).getData().getCard();
                    String groupNick = IMcBot.config.getCmd().isGroupNickOn() // 是否使用群昵称
                            ? nick == null ? event.getSender().getCard() : nick // 防止api返回为空
                            : event.getSender().getNickname();

                    String toSend = IMcBot.config.getCmd().isGamePrefixOn()
                            ? IMcBot.config.getCmd().isIdGamePrefixOn()
                            ? String.format("§b[§l%s§r(§5%s§b)]§a<%s>§f %s", IMcBot.config.getCmd().getQqGamePrefix(), event.getGroupId(), groupNick, send)
                            : String.format("§b[§l%s§b]§a<%s>§f %s", IMcBot.config.getCmd().getQqGamePrefix(), groupNick, send)
                            : String.format("§a<%s>§f %s", groupNick, send);
                    ITickEvent.getSendQueue().add(toSend);
                }
            }
        });
    }

    private static void GroupCmdsHandler(EventBus dispatchers) {
        dispatchers.addListener(new SimpleEventListener<GroupMessageEvent>() {
            @Override
            public void onMessage(GroupMessageEvent event) {
                if (IMcBot.config.getCommon().getGroupIdList().contains(event.getGroupId())
                        && event.getMessage().startsWith(IMcBot.config.getCmd().getCmdStart())//命令前缀
                        && IMcBot.config.getStatus().isREnable()//总接受开关
                        && IMcBot.config.getStatus().isRCmdEnable()//接受命令开关
                ) {
                    CmdApi.invokeCommandGroup(event);
                }
            }
        });
    }

    private static void GroupNoticeHandler(EventBus dispatchers) {
        dispatchers.addListener(new SimpleEventListener<GroupIncreaseNoticeEvent>() {
            @Override
            public void onMessage(GroupIncreaseNoticeEvent event) {
                if (IMcBot.config.getCommon().getGroupIdList().contains(event.getGroupId())
                        && IMcBot.config.getStatus().isREnable()
                        && IMcBot.config.getStatus().isSQqWelcomeEnable()) {

                    IMcBot.bot.sendGroupMsg(event.getGroupId(), MsgUtils.builder().at(event.getUserId()).build() + "\n" + IMcBot.config.getCmd().getWelcomeNotice(), true);
                }
            }
        });

        dispatchers.addListener(new SimpleEventListener<GroupDecreaseNoticeEvent>() {
            @Override
            public void onMessage(GroupDecreaseNoticeEvent event) {
                if (IMcBot.config.getCommon().getGroupIdList().contains(event.getGroupId())
                        && IMcBot.config.getStatus().isREnable()
                        && IMcBot.config.getStatus().isSQqLeaveEnable()) {
                    IMcBot.bot.sendGroupMsg(event.getGroupId(), MsgUtils.builder().text(String.valueOf(event.getUserId())).build() + "\n" +IMcBot.config.getCmd().getLeaveNotice(), true);
                }
            }
        });

    }

    private static void GuildChatHandler(EventBus dispatchers) {
        dispatchers.addListener(new SimpleEventListener<GuildMessageEvent>() {
            @Override
            public void onMessage(GuildMessageEvent event) {
                if (event.getGuildId().equals(IMcBot.config.getCommon().getGuildId())
                        && IMcBot.config.getCommon().getChannelIdList().contains(event.getChannelId())
                        && !event.getMessage().startsWith(IMcBot.config.getCmd().getCmdStart())//过滤命令前缀
                        && IMcBot.config.getStatus().isREnable()//总接受开关
                        && IMcBot.config.getStatus().isRChatEnable()//接受聊天开关
                        && event.getUserId() != IMcBot.config.getCommon().getBotId()
                ) {

                    String send = CQUtils.replace(event.getMessage());//暂时匹配仅符合字符串聊天内容与图片
                    if (IMcBot.config.getCmd().isQqChatPrefixOn()) {
                        val split = event.getMessage().split(" ");
                        if (IMcBot.config.getCmd().getQqChatPrefix().equals(split[0])) //指定前缀发送
                            send = split[1];
                        else return;
                    }
                    String nick = IMcBot.bot.getGuildMemberProfile(event.getGuildId(), String.valueOf(event.getUserId())).getData().getNickname();
                    String guildNick = IMcBot.config.getCmd().isGroupNickOn()
                            ? nick == null ? event.getSender().getNickname() : nick
                            : event.getSender().getNickname();


                    String toSend = IMcBot.config.getCmd().isGamePrefixOn()
                            ? IMcBot.config.getCmd().isIdGamePrefixOn()
                            ? String.format("§b[§l%s§r(§5%s§b)]§a<%s>§f %s", IMcBot.config.getCmd().getGuildGamePrefix(), event.getChannelId(), guildNick, send)
                            : String.format("§b[§l%s§b]§a<%s>§f %s", IMcBot.config.getCmd().getGuildGamePrefix(), guildNick, send)
                            : String.format("§a<%s>§f %s", guildNick, send);
                    ITickEvent.getSendQueue().add(toSend);

                }
            }
        });
    }

    private static void GuildCmdsHandler(EventBus dispatchers) {
        dispatchers.addListener(new SimpleEventListener<GuildMessageEvent>() {
            @Override
            public void onMessage(GuildMessageEvent event) {
                if (IMcBot.config.getCommon().getChannelIdList().contains(event.getChannelId())
                        && event.getMessage().startsWith(IMcBot.config.getCmd().getCmdStart())//命令前缀
                        && IMcBot.config.getStatus().isREnable()//总接受开关
                        && IMcBot.config.getStatus().isRCmdEnable()//接受命令开关
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
                if (!IMcBot.config.getCommon().getGroupIdList().isEmpty()
                ) {
                    for (val id : IMcBot.config.getCommon().getGroupIdList()){
                        IMcBot.bot.sendGroupMsg(id, "▌ 群服互联已连接 ┈━═☆", true);
                    }
                }
                if (!IMcBot.config.getCommon().getChannelIdList().isEmpty()
                ) {
                    for (val id : IMcBot.config.getCommon().getChannelIdList()){
                        IMcBot.bot.sendGuildMsg(IMcBot.config.getCommon().getGuildId() , id, "▌ 群服互联已连接 ┈━═☆");
                    }
                }
            }
        });
    }


}
