package cn.evole.mods.mcbot.init.event;

import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.McBot;
import cn.evole.mods.mcbot.cmds.CmdApi;
import cn.evole.mods.mcbot.data.ChatRecordApi;
import cn.evole.mods.mcbot.init.config.ModConfig;
import cn.evole.mods.mcbot.util.onebot.CQUtils;
import cn.evole.onebot.client.factory.ListenerFactory;
import cn.evole.onebot.client.interfaces.handler.DefaultHandler;
import cn.evole.onebot.client.interfaces.listener.SimpleListener;
import cn.evole.onebot.sdk.event.message.GroupMessageEvent;
import cn.evole.onebot.sdk.event.message.GuildMessageEvent;
import cn.evole.onebot.sdk.event.meta.LifecycleMetaEvent;
import cn.evole.onebot.sdk.event.notice.group.GroupDecreaseNoticeEvent;
import cn.evole.onebot.sdk.event.notice.group.GroupIncreaseNoticeEvent;
import cn.evole.onebot.sdk.util.BotUtils;
import cn.evole.onebot.sdk.util.MsgUtils;
import lombok.val;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
//#if MC <11900
import net.minecraft.network.chat.TextComponent;
//#endif

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/20 8:13
 * Version: 1.0
 */
public class IBotEvent {
    public static void init(ListenerFactory dispatchers) {

        GroupChatHandler(dispatchers);
        GroupCmdsHandler(dispatchers);
        GroupNoticeHandler(dispatchers);
        GuildChatHandler(dispatchers);
        GuildCmdsHandler(dispatchers);
        LifeCycleHandler(dispatchers);
    }

    private static void GroupChatHandler(ListenerFactory dispatchers) {
        dispatchers.addListener(new DefaultHandler<GroupMessageEvent>() {
            @Override
            public void onMessage(GroupMessageEvent event) {
                String send = CQUtils.replace(event);//暂时匹配仅符合字符串聊天内容与图片
                if (ModConfig.INSTANCE.getCommon().getGroupIdList().contains(event.getGroupId())//判断是否是配置中的群
                        && !send.startsWith(ModConfig.INSTANCE.getCmd().getCmdStart())//过滤命令前缀
                        && ModConfig.INSTANCE.getStatus().isREnable()//总接受开关
                        && ModConfig.INSTANCE.getStatus().isRChatEnable()//接受聊天开关
                        && event.getUserId() != ModConfig.INSTANCE.getCommon().getBotId()//过滤机器人
                ) {

                    if (ModConfig.INSTANCE.getCmd().isQqChatPrefixOn()) {
                        val split = event.getMessage().split(" ");
                        if (ModConfig.INSTANCE.getCmd().getQqChatPrefix().equals(split[0])) //指定前缀发送
                            send = split[1];
                        else return;
                    }

                    val nick = McBot.bot.getGroupMemberInfo(event.getGroupId(), event.getUserId(), true);
                    String groupNick = ModConfig.INSTANCE.getCmd().isGroupNickOn() // 是否使用群昵称
                            ? nick == null ? event.getSender().getCard() : nick.getData().getCard() // 防止api返回为空
                            : event.getSender().getNickname();

                    String finalMsg = ModConfig.INSTANCE.getCmd().isGamePrefixOn()
                            ? ModConfig.INSTANCE.getCmd().isIdGamePrefixOn()
                            ? String.format("§b[§l%s§r(§5%s§b)]§a<%s>§f %s", ModConfig.INSTANCE.getCmd().getQqGamePrefix(), event.getGroupId(), groupNick, send)
                            : String.format("§b[§l%s§b]§a<%s>§f %s", ModConfig.INSTANCE.getCmd().getQqGamePrefix(), groupNick, send)
                            : String.format("§a<%s>§f %s", groupNick, send);

                    ChatRecordApi.add(String.valueOf(event.getMessageId()), String.valueOf(event.getGroupId()), String.valueOf(event.getSelfId()), finalMsg);


                    //todo 撤回机制？
//                    val recallCmd = "/mcbot recall" + event.getMessageId();
//                    val end = " [撤回]";
//                    //#if MC >= 11900
//                    //$$ val recall = Component.literal(end).withStyle(ChatFormatting.BLUE).setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, recallCmd)));
//                    //$$ val toSend = Component.literal(finalMsg).append(recall);
//                    //#else
//                    val recall = new TextComponent(end).withStyle(ChatFormatting.BLUE).setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, recallCmd)));
//                    val toSend = new TextComponent(finalMsg).append(recall);
//                    //#endif

                    //#if MC >= 11900
                    //$$ val toSend = Component.literal(finalMsg);
                    //#else
                    val toSend = new TextComponent(finalMsg);
                    //#endif

                    ITickEvent.getSendQueue().add(toSend);
                }
            }
        });
    }

    private static void GroupCmdsHandler(ListenerFactory dispatchers) {
        dispatchers.addListener(new SimpleListener<GroupMessageEvent>() {
            @Override
            public void onMessage(GroupMessageEvent event) {
                String send = CQUtils.replace(event);//暂时匹配仅符合字符串聊天内容与图片
                if (ModConfig.INSTANCE.getCommon().getGroupIdList().contains(event.getGroupId())
                        && send.startsWith(ModConfig.INSTANCE.getCmd().getCmdStart())//命令前缀
                        && ModConfig.INSTANCE.getStatus().isREnable()//总接受开关
                        && ModConfig.INSTANCE.getStatus().isRCmdEnable()//接受命令开关
                ) {
                    CmdApi.invokeCommandGroup(event);
                }
            }
        });
    }

    private static void GroupNoticeHandler(ListenerFactory dispatchers) {
        dispatchers.addListener(new SimpleListener<GroupIncreaseNoticeEvent>() {
            @Override
            public void onMessage(GroupIncreaseNoticeEvent event) {
                if (ModConfig.INSTANCE.getCommon().getGroupIdList().contains(event.getGroupId())
                        && ModConfig.INSTANCE.getStatus().isSEnable()
                        && ModConfig.INSTANCE.getStatus().isSQqWelcomeEnable()) {
                    val msg = MsgUtils.builder().at(event.getUserId()).build() + "\n" + ModConfig.INSTANCE.getCmd().getWelcomeNotice();
                    Const.groupMsg(event.getGroupId(), msg);
                }
            }
        });

        dispatchers.addListener(new SimpleListener<GroupDecreaseNoticeEvent>() {
            @Override
            public void onMessage(GroupDecreaseNoticeEvent event) {
                if (ModConfig.INSTANCE.getCommon().getGroupIdList().contains(event.getGroupId())
                        && ModConfig.INSTANCE.getStatus().isSEnable()
                        && ModConfig.INSTANCE.getStatus().isSQqLeaveEnable()) {

                    val msg = MsgUtils.builder().text(String.valueOf(event.getUserId())).build() + "\n" +ModConfig.INSTANCE.getCmd().getLeaveNotice();
                    Const.groupMsg(event.getGroupId(), msg);
                }
            }
        });

    }

    private static void GuildChatHandler(ListenerFactory dispatchers) {
        dispatchers.addListener(new SimpleListener<GuildMessageEvent>() {
            @Override
            public void onMessage(GuildMessageEvent event) {
                String send = CQUtils.replace(event);//暂时匹配仅符合字符串聊天内容与图片
                if (event.getGuildId().equals(ModConfig.INSTANCE.getCommon().getGuildId())
                        && ModConfig.INSTANCE.getCommon().getChannelIdList().contains(event.getChannelId())
                        && !send.startsWith(ModConfig.INSTANCE.getCmd().getCmdStart())//过滤命令前缀
                        && ModConfig.INSTANCE.getStatus().isREnable()//总接受开关
                        && ModConfig.INSTANCE.getStatus().isRChatEnable()//接受聊天开关
                        && event.getUserId() != ModConfig.INSTANCE.getCommon().getBotId()
                ) {

                    if (ModConfig.INSTANCE.getCmd().isQqChatPrefixOn()) {
                        val split = event.getMessage().split(" ");
                        if (ModConfig.INSTANCE.getCmd().getQqChatPrefix().equals(split[0])) //指定前缀发送
                            send = split[1];
                        else return;
                    }
                    val nick = McBot.bot.getGuildMemberProfile(event.getGuildId(), String.valueOf(event.getUserId()));
                    String guildNick = ModConfig.INSTANCE.getCmd().isGroupNickOn()
                            ? nick == null ? event.getSender().getNickname() : nick.getData().getNickname()
                            : event.getSender().getNickname();


                    String finalMsg = ModConfig.INSTANCE.getCmd().isGamePrefixOn()
                            ? ModConfig.INSTANCE.getCmd().isIdGamePrefixOn()
                            ? String.format("§b[§l%s§r(§5%s§b)]§a<%s>§f %s", ModConfig.INSTANCE.getCmd().getGuildGamePrefix(), event.getChannelId(), guildNick, send)
                            : String.format("§b[§l%s§b]§a<%s>§f %s", ModConfig.INSTANCE.getCmd().getGuildGamePrefix(), guildNick, send)
                            : String.format("§a<%s>§f %s", guildNick, send);


                    //#if MC >= 11900
                    //$$ val toSend = Component.literal(finalMsg);
                    //#else
                    val toSend = new TextComponent(finalMsg);
                    //#endif

                    ITickEvent.getSendQueue().add(toSend);

                }
            }
        });
    }

    private static void GuildCmdsHandler(ListenerFactory dispatchers) {
        dispatchers.addListener(new SimpleListener<GuildMessageEvent>() {
            @Override
            public void onMessage(GuildMessageEvent event) {
                String send = CQUtils.replace(event);//暂时匹配仅符合字符串聊天内容与图片
                if (ModConfig.INSTANCE.getCommon().getChannelIdList().contains(event.getChannelId())
                        && send.startsWith(ModConfig.INSTANCE.getCmd().getCmdStart())//命令前缀
                        && ModConfig.INSTANCE.getStatus().isREnable()//总接受开关
                        && ModConfig.INSTANCE.getStatus().isRCmdEnable()//接受命令开关
                ) {
                    CmdApi.invokeCommandGuild(event);
                }
            }
        });
    }


    private static void LifeCycleHandler(ListenerFactory dispatchers) {
        dispatchers.addListener(new SimpleListener<LifecycleMetaEvent>() {
            @Override
            public void onMessage(LifecycleMetaEvent event) {
                if (!event.getSubType().equals("connect")) return;
                if (!ModConfig.INSTANCE.getCommon().getGroupIdList().isEmpty()
                ) {
                    val msg = "▌ 群服互联已连接 ┈━═☆";
                    Const.sendGroupMsg(msg);

                }
                if (!ModConfig.INSTANCE.getCommon().getChannelIdList().isEmpty()
                ) {
                    val msg = "▌ 群服互联已连接 ┈━═☆";
                    Const.sendGuildMsg(msg);
                }
            }
        });
    }


}
