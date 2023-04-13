package cn.evolvefield.mods.botapi.util.onebot;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.cmd.CustomCmd;
import cn.evolvefield.mods.botapi.init.handler.CustomCmdHandler;
import cn.evolvefield.onebot.sdk.event.message.GroupMessageEvent;
import cn.evolvefield.onebot.sdk.event.message.GuildMessageEvent;
import cn.evolvefield.onebot.sdk.event.message.MessageEvent;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Project: Bot-Connect-fabric-1.18
 * Author: cnlimiter
 * Date: 2023/1/21 19:04
 * Description:
 */
public class BotUtils {
    /**
     * q群消息是否存在变量
     *
     * @param event q群消息
     * @return 是否存在变量
     */

    private static boolean isVar(MessageEvent event) {
        AtomicBoolean match = new AtomicBoolean(false);
        CustomCmdHandler.INSTANCE.getCustomCmdMap().keySet().forEach(
                s -> {
                    if (CustomCmdHandler.INSTANCE.getCmdByAlies(s).getCmdContent().contains("%")) {//是否变量模板
                        if (event.getMessage().substring(1).contains(s))//去除命令符号
                            match.set(true);
                    }

                }
        );
        return match.get();
    }

    /**
     * 变量解析
     *
     * @param event     消息事件
     * @param customCmd 自定义实例
     * @param cmd       q群指令
     * @return 处理完的指令
     */
    public static String varParse(MessageEvent event, CustomCmd customCmd, String cmd) {
        String returnCmd = "";
        if (isVar(event)) {//存在变量
            int headIndex = customCmd.getCmdContent().split(" ").length - getSubStr(customCmd.getCmdContent()) - 1;//除了变量以外的指令
            if (customCmd.getCmdContent().split(" ").length == cmd.split(" ").length
                    && cmd.split(" ")[headIndex].equals(customCmd.getCmdContent().split(" ")[headIndex])//核验
            ) {
                returnCmd = event.getMessage().substring(1);//返回q群指令
            }
        } else returnCmd = customCmd.getCmdContent();//返回普通自定义命令指令
        return returnCmd;
    }

    public static boolean guildAdminParse(GuildMessageEvent event) {
        AtomicBoolean isAdmin = new AtomicBoolean(false);
        for (var roleInfo : BotApi.bot.getGuildMemberProfile(event.getGuildId(), event.getSender().getTinyId())
                .getData()
                .getRoles()) {
            if (Integer.parseInt(roleInfo.getRoleId()) >= 2 || Arrays.stream(GuildRole.values()).anyMatch(s -> s.role.equals(roleInfo.getRoleName()))) {
                isAdmin.set(true);
                break;
            }
        }
        return isAdmin.get();
    }

    public static boolean groupAdminParse(GroupMessageEvent event) {
        return !event.getSender().getRole().equals("MEMBER") && !event.getSender().getRole().equals("member");
    }


    /**
     * 获取字符串%（即变量）出现的个数
     *
     * @param str 传进来的字符串
     * @return 统计%出现的个数
     */
    private static int getSubStr(String str) {
        // 用空字符串替换所有要查找的字符串
        String destStr = str.replaceAll("%", "");
        // 查找字符出现的个数 = （原字符串长度 - 替换后的字符串长度）/要查找的字符串长度
        return (str.length() - destStr.length()) / "%".length();
    }


}
