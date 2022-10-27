package cn.evolvefield.mods.botapi.api.cmd;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.CustomCmdHandler;
import cn.evolvefield.onebot.sdk.core.Bot;
import cn.evolvefield.onebot.sdk.model.event.message.GroupMessageEvent;
import cn.evolvefield.onebot.sdk.model.event.message.GuildMessageEvent;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/2 15:56
 * Version: 1.0
 */
public class CmdApi {
    private static StringBuilder CmdMain(CustomCmd customCmd) {
        StringBuilder result = new StringBuilder();
        BotApi.SERVER.getCommands().performPrefixedCommand(CustomCmdRun.CUSTOM, customCmd.getCmdContent());

        for (String s : CustomCmdRun.CUSTOM.outPut) {
            result.append(s.replaceAll("§\\S", "")).append("\n");
        }
        CustomCmdRun.CUSTOM.outPut.clear();
        return result;
    }

    public static void GroupCmd(Bot bot, CustomCmd customCmd, GroupMessageEvent event) {
        bot.sendGroupMsg(event.getGroupId(), CmdMain(customCmd).toString(), true);
    }

    public static void GuildCmd(Bot bot, CustomCmd customCmd, GuildMessageEvent event) {
        bot.sendGuildMsg(event.getGuildId(), event.getChannelId(), CmdMain(customCmd).toString());
    }

    public static void invokeCommandGroup(GroupMessageEvent event) {
        String[] formatMsg = event.getMessage().split(" ");
        String commandBody = formatMsg[0].substring(1);

        if (!event.getSender().getRole().equals("MEMBER") || !event.getSender().getRole().equals("member")) {
            CustomCmdHandler.getInstance().getCustomCmds().stream()
                    .filter(customCmd -> customCmd.getRequirePermission() == 1 && customCmd.getCmdAlies().equals(commandBody))
                    .forEach(customCmd -> GroupCmd(BotApi.bot, customCmd, event));
        }
        CustomCmdHandler.getInstance().getCustomCmds().stream()
                .filter(customCmd -> customCmd.getRequirePermission() == 0 && customCmd.getCmdAlies().equals(commandBody))
                .forEach(customCmd -> GroupCmd(BotApi.bot, customCmd, event));

    }

    public static void invokeCommandGuild(GuildMessageEvent event) {
        String[] adminList = {"频道主", "管理员", "机器人管理员"};
        String[] formatMsg = event.getMessage().split(" ");
        String commandBody = formatMsg[0].substring(1);
        AtomicBoolean isAdmin = new AtomicBoolean(false);
        BotApi.bot.getGuildMemberProfile(event.getGuildId(), event.getSender().getTinyId())
                .getData()
                .getRoles()
                .stream()
                .filter(roleInfo -> {
                    if (Integer.parseInt(roleInfo.getRoleId()) >= 2 || Arrays.stream(adminList).anyMatch(s -> s.equals(roleInfo.getRoleName())))
                        isAdmin.set(true);
                    return false;
                });


        if (isAdmin.get()) {
            CustomCmdHandler.getInstance().getCustomCmds().stream()
                    .filter(customCmd -> customCmd.getRequirePermission() == 1 && customCmd.getCmdAlies().equals(commandBody))
                    .forEach(customCmd -> GuildCmd(BotApi.bot, customCmd, event));
        } else {
            CustomCmdHandler.getInstance().getCustomCmds().stream()
                    .filter(customCmd -> customCmd.getRequirePermission() == 0 && customCmd.getCmdAlies().equals(commandBody))
                    .forEach(customCmd -> GuildCmd(BotApi.bot, customCmd, event));
        }


    }
}
