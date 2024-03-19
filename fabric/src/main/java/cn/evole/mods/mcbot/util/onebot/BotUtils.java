package cn.evole.mods.mcbot.util.onebot;

import cn.evole.mods.mcbot.cmds.CustomCmd;
import cn.evole.mods.mcbot.init.handler.CustomCmdHandler;
import cn.evole.onebot.sdk.event.message.GroupMessageEvent;
import cn.evole.onebot.sdk.util.NetUtils;
import lombok.val;

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
     * @param msg q群消息
     * @return 是否存在变量
     */

    private static boolean isVar(String msg) {
        AtomicBoolean match = new AtomicBoolean(false);
        CustomCmdHandler.INSTANCE.getCustomCmds().forEach(
                cmd -> {
                    if (cmd.getCmdContent().contains("%")) {//是否变量模板
                        if (msg.contains(cmd.getCmdAlies()))//去除命令符号
                            match.set(true);
                    }

                }
        );
        return match.get();
    }

    /**
     * 变量解析
     *
     * @param customCmd 自定义实例
     * @param cmd       q群指令
     * @return 处理完的指令
     */
    public static String varParse(CustomCmd customCmd, String cmd) {
        String returnCmd = "";
        if (isVar(cmd)) {//存在变量
            val replaceContent = customCmd.getCmdContent().split("%")[0].trim();
            returnCmd = cmd.replace(customCmd.getCmdAlies(), replaceContent);//返回q群指令
        } else returnCmd = customCmd.getCmdContent();//返回普通自定义命令指令
        return returnCmd;
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
    public static String cmdParse(String command) {
        // 找到最后一个空格的位置
        int lastSpaceIndex = command.lastIndexOf(" ");

        // 如果没有空格，则整个命令就是关键词
        if (lastSpaceIndex == -1) {
            return command;
        }

        // 返回最后一个空格之前的内容
        return command.substring(0, lastSpaceIndex);
    }



    /**
     * 获取群头像(弃用)
     * TODO 过时的API，无法使用
     * @param groupId 群号
     * @param size    头像尺寸
     * @return 头像链接 （size为0返回真实大小, 40(40*40), 100(100*100), 640(640*640)）
     */
    public static String getGroupAvatar(long groupId, int size) {
        return String.format("https://p.qlogo.cn/gh/%s/%s/%s", groupId, groupId, size);
    }

    /**
     * 获取用户昵称(弃用)
     * TODO 过时的API，无法使用
     * @param userId QQ号
     * @return 用户昵称
     */
    public static String getNickname(long userId) {
        val url = String.format("https://r.qzone.qq.com/fcg-bin/cgi_get_portrait.fcg?uins=%s", userId);
        val result = NetUtils.get(url, "GBK");
        if (result != null && !result.isEmpty()) {
            String nickname = result.split(",")[6];
            return nickname.substring(1, nickname.length() - 1);
        }
        return "";
    }

    /**
     * 获取用户头像(弃用)
     * TODO 过时的API，无法使用
     * @param userId QQ号
     * @param size   头像尺寸
     * @return 头像链接 （size为0返回真实大小, 40(40*40), 100(100*100), 640(640*640)）
     */
    public static String getUserAvatar(long userId, int size) {
        return String.format("https://q1.qlogo.cn/g?b=qq&nk=%s&s=%s", userId, size);
    }

}
