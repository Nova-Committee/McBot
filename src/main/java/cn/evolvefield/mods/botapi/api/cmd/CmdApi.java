package cn.evolvefield.mods.botapi.api.cmd;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.CustomCmdHandler;
import cn.evolvefield.mods.botapi.util.onebot.BotUtils;
import cn.evolvefield.onebot.sdk.core.Bot;
import cn.evolvefield.onebot.sdk.model.event.message.GroupMessageEvent;
import cn.evolvefield.onebot.sdk.model.event.message.GuildMessageEvent;

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
        if (!BotUtils.varParse(event)) return;
        String commandHead = event.getMessage().split(" ")[0].substring(1);

        if (BotUtils.groupAdminParse(event)) {
            CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                    .filter(customCmd -> customCmd.getRequirePermission() == 1 && customCmd.getCmdAlies().equals(commandHead))
                    .forEach(customCmd -> GroupCmd(BotApi.bot, customCmd, event));//admin
        }
        CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                .filter(customCmd -> customCmd.getRequirePermission() == 0 && customCmd.getCmdAlies().equals(commandHead))
                .forEach(customCmd -> GroupCmd(BotApi.bot, customCmd, event));

    }

    public static void invokeCommandGuild(GuildMessageEvent event) {
        if (!BotUtils.varParse(event)) return;
        String commandHead = event.getMessage().split(" ")[0].substring(1);


        if (BotUtils.guildAdminParse(event)) {
            CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                    .filter(customCmd -> customCmd.getRequirePermission() == 1 && customCmd.getCmdAlies().equals(commandHead))
                    .forEach(customCmd -> GuildCmd(BotApi.bot, customCmd, event));//admin
        } else {
            CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                    .filter(customCmd -> customCmd.getRequirePermission() == 0 && customCmd.getCmdAlies().equals(commandHead))
                    .forEach(customCmd -> GuildCmd(BotApi.bot, customCmd, event));
        }


    }
}
