package cn.evole.mods.mcbot.cmds;

import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.McBot;
import cn.evole.mods.mcbot.init.handler.CustomCmdHandler;
import cn.evole.mods.mcbot.util.onebot.BotUtils;
import cn.evole.onebot.sdk.event.message.GroupMessageEvent;
import cn.evole.onebot.sdk.event.message.GuildMessageEvent;

//兼容vanish
import cn.evole.mods.mcbot.init.compat.vanish.VanishAPI;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/2 15:56
 * Version: 1.0
 */
public class CmdApi {
    private static String CmdMain(String cmd, boolean isOp, boolean vanishSupport) {
        StringBuilder result = new StringBuilder();
        if (!vanishSupport) {
            VanishAPI.performPrefixedCommand(McBot.SERVER.getCommands(), isOp ? BotCmdRun.OP : BotCmdRun.CUSTOM, cmd);
        }
        for (String s : (isOp ? BotCmdRun.OP.outPut : BotCmdRun.CUSTOM.outPut)) {
            result.append(s.replaceAll("§\\S", "")).append("\n");
        }
        if (isOp) BotCmdRun.OP.outPut.clear();
        else BotCmdRun.CUSTOM.outPut.clear();
        return result.toString();
    }

    private static void GroupCmd(long groupId, String cmd, boolean isOp, boolean OPEscape) {
        Const.groupMsg(groupId, CmdMain(cmd, isOp, OPEscape));
    }

    private static void GuildCmd(String guildId, String channelId, String cmd, boolean isOp, boolean OPEscape) {
        Const.guildMsg(guildId, channelId, CmdMain(cmd, isOp, OPEscape));
    }

    public static void invokeCommandGroup(GroupMessageEvent event) {
        String command = event.getMessage().substring(1);//去除前缀
        String originCommand = command;
        command = BotUtils.cmdParse(command);
        String performedCommand = command;

        if (performedCommand.equals("list")) {
            // 如果指令包含list,则强行以非管理员身份执行
            CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                    .filter(customCmd -> customCmd.getRequirePermission() < 1 && performedCommand.equals(customCmd.getCmdAlies()))
                    .forEach(customCmd -> GroupCmd(event.getGroupId(), BotUtils.varParse(customCmd, originCommand), false, customCmd.isVanishSupport()));
        }else{
            if (BotUtils.groupAdminParse(event)) {
                CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                        .filter(customCmd -> performedCommand.equals(customCmd.getCmdAlies()))
                        .forEach(customCmd -> GroupCmd(event.getGroupId(), BotUtils.varParse(customCmd, originCommand), true, customCmd.isVanishSupport()));//admin
            } else
                CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                        .filter(customCmd -> customCmd.getRequirePermission() < 1 && performedCommand.equals(customCmd.getCmdAlies()))
                        .forEach(customCmd -> GroupCmd(event.getGroupId(), BotUtils.varParse(customCmd, originCommand), false, customCmd.isVanishSupport()));
        }

    }

    public static void invokeCommandGuild(GuildMessageEvent event) {
        String command = event.getMessage().substring(1);//去除前缀

        if (BotUtils.guildAdminParse(event)) {
            CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                    .filter(customCmd -> command.contains(customCmd.getCmdAlies()))
                    .forEach(customCmd -> GuildCmd(event.getGuildId(), event.getChannelId(), BotUtils.varParse(customCmd, command), true, customCmd.isVanishSupport()));//admin
        } else CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                .filter(customCmd -> customCmd.getRequirePermission() < 1 && command.contains(customCmd.getCmdAlies()))
                .forEach(customCmd -> GuildCmd(event.getGuildId(), event.getChannelId(), BotUtils.varParse(customCmd, command), false, customCmd.isVanishSupport()));
    }
}
