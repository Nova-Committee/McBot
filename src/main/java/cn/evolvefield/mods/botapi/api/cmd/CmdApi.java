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
    private static StringBuilder CmdMain(CustomCmd customCmd, String cmd, boolean isVar) {
        StringBuilder result = new StringBuilder();
        if (isVar)
            BotApi.SERVER.getCommands().performPrefixedCommand(BotCmdRun.OP, cmd);
        else
            BotApi.SERVER.getCommands().performPrefixedCommand(BotCmdRun.CUSTOM, customCmd.getCmdContent());

        for (String s : BotCmdRun.CUSTOM.outPut) {
            result.append(s.replaceAll("§\\S", "")).append("\n");
        }
        BotCmdRun.CUSTOM.outPut.clear();
        return result;
    }

    public static void GroupCmd(Bot bot, CustomCmd customCmd, long groupId, String cmd, boolean isVar) {
        bot.sendGroupMsg(groupId, CmdMain(customCmd, cmd, isVar).toString(), true);
    }

    public static void GuildCmd(Bot bot, CustomCmd customCmd, String guildId, String channelId, String cmd, boolean isVar) {
        bot.sendGuildMsg(guildId, channelId, CmdMain(customCmd, cmd, isVar).toString());
    }

    public static void invokeCommandGroup(GroupMessageEvent event) {
        String commandHead = event.getMessage().split(" ")[0].substring(1);
        String command = event.getMessage().substring(1);

        if (BotUtils.groupAdminParse(event)) {
            CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                    .filter(customCmd -> customCmd.getRequirePermission() == 1 && customCmd.getCmdAlies().equals(commandHead))
                    .forEach(customCmd -> GroupCmd(BotApi.bot, customCmd, event.getGroupId(), command, BotUtils.varParse(event)));//admin
        }
        CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                .filter(customCmd -> customCmd.getRequirePermission() == 0 && customCmd.getCmdAlies().equals(commandHead))
                .forEach(customCmd -> GroupCmd(BotApi.bot, customCmd, event.getGroupId(), command, BotUtils.varParse(event)));

    }

    public static void invokeCommandGuild(GuildMessageEvent event) {
        String commandHead = event.getMessage().split(" ")[0].substring(1);
        String command = event.getMessage().substring(1);

        if (BotUtils.guildAdminParse(event)) {
            CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                    .filter(customCmd -> customCmd.getRequirePermission() == 1 && customCmd.getCmdAlies().equals(commandHead))
                    .forEach(customCmd -> GuildCmd(BotApi.bot, customCmd, event.getGuildId(), event.getChannelId(), command, BotUtils.varParse(event)));//admin
        } else {
            CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                    .filter(customCmd -> customCmd.getRequirePermission() == 0 && customCmd.getCmdAlies().equals(commandHead))
                    .forEach(customCmd -> GuildCmd(BotApi.bot, customCmd, event.getGuildId(), event.getChannelId(), command, BotUtils.varParse(event)));
        }


    }
}
