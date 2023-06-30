package cn.evole.mods.mcbot.cmds;

import cn.evole.mods.mcbot.IMcBot;
import cn.evole.mods.mcbot.init.handler.CustomCmdHandler;
import cn.evole.mods.mcbot.util.onebot.BotUtils;
import cn.evole.onebot.client.core.Bot;
import cn.evole.onebot.sdk.event.message.GroupMessageEvent;
import cn.evole.onebot.sdk.event.message.GuildMessageEvent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/2 15:56
 * Version: 1.0
 */
public class CmdApi {
    private static StringBuilder CmdMain(String cmd, boolean isOp) {
        StringBuilder result = new StringBuilder();
        //#if MC >= 11900
        IMcBot.SERVER.getCommands().performPrefixedCommand(isOp ? BotCmdRun.OP : BotCmdRun.CUSTOM, cmd);//优雅
        //#else
        //$$ IMcBot.SERVER.getCommands().performCommand(isOp ? BotCmdRun.OP : BotCmdRun.CUSTOM, cmd);
        //#endif
        for (String s : (isOp ? BotCmdRun.OP.outPut : BotCmdRun.CUSTOM.outPut)) {
            result.append(s.replaceAll("§\\S", "")).append("\n");
        }
        if (isOp) BotCmdRun.OP.outPut.clear();
        else BotCmdRun.CUSTOM.outPut.clear();
        return result;
    }

    private static void GroupCmd(Bot bot, long groupId, String cmd, boolean isOp) {
        bot.sendGroupMsg(groupId, CmdMain(cmd, isOp).toString(), true);
    }

    private static void GuildCmd(Bot bot, String guildId, String channelId, String cmd, boolean isOp) {
        bot.sendGuildMsg(guildId, channelId, CmdMain(cmd, isOp).toString());
    }

    public static void invokeCommandGroup(GroupMessageEvent event) {
        String command = event.getMessage().substring(1);//去除前缀

        if (BotUtils.groupAdminParse(event)) {
            IMcBot.CMD.getCustomCmds().stream()
                    .filter(customCmd -> command.contains(customCmd.getCmdAlies()))
                    .forEach(customCmd -> GroupCmd(IMcBot.bot, event.getGroupId(), BotUtils.varParse(customCmd, command), true));//admin
        } else
            IMcBot.CMD.getCustomCmds().stream()
                    .filter(customCmd -> customCmd.getRequirePermission() < 1 && command.contains(customCmd.getCmdAlies()))
                    .forEach(customCmd -> GroupCmd(IMcBot.bot, event.getGroupId(), BotUtils.varParse(customCmd, command), false));

    }

    public static void invokeCommandGuild(GuildMessageEvent event) {
        String command = event.getMessage().substring(1);//去除前缀

        if (BotUtils.guildAdminParse(event)) {
            IMcBot.CMD.getCustomCmds().stream()
                    .filter(customCmd -> command.contains(customCmd.getCmdAlies()))
                    .forEach(customCmd -> GuildCmd(IMcBot.bot, event.getGuildId(), event.getChannelId(), BotUtils.varParse(customCmd, command), true));//admin
        } else IMcBot.CMD.getCustomCmds().stream()
                .filter(customCmd -> customCmd.getRequirePermission() < 1 && command.contains(customCmd.getCmdAlies()))
                .forEach(customCmd -> GuildCmd(IMcBot.bot, event.getGuildId(), event.getChannelId(), BotUtils.varParse(customCmd, command), false));
    }
}