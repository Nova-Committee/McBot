package cn.evole.mods.mcbot.cmds;

import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.McBot;
import cn.evole.mods.mcbot.init.handler.CustomCmdHandler;
import cn.evole.mods.mcbot.util.onebot.BotUtils;
import cn.evole.onebot.sdk.event.message.GroupMessageEvent;
import cn.evole.onebot.sdk.event.message.GuildMessageEvent;

//兼容1.20.1版本vanish
//#if MC == 12001
//$$ import cn.evole.mods.mcbot.init.compat.VanishAPI;
//#endif

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/2 15:56
 * Version: 1.0
 */
public class CmdApi {
    private static StringBuilder CmdMain(String cmd, boolean isOp, boolean OPEscape) {
        StringBuilder result = new StringBuilder();
        //#if MC == 12001
        //$$ if (!OPEscape) {
        //$$    VanishAPI.performPrefixedCommand(McBot.SERVER.getCommands(), isOp ? BotCmdRun.OP : BotCmdRun.CUSTOM, cmd);
        //$$ }
        //#else
        //#if MC >= 11900
        //$$ McBot.SERVER.getCommands().performPrefixedCommand(isOp ? BotCmdRun.OP : BotCmdRun.CUSTOM, cmd);//优雅
        //#else
        McBot.SERVER.getCommands().performCommand(isOp ? BotCmdRun.OP : BotCmdRun.CUSTOM, cmd);
        //#endif
        //#endif
        for (String s : (isOp ? BotCmdRun.OP.outPut : BotCmdRun.CUSTOM.outPut)) {
            result.append(s.replaceAll("§\\S", "")).append("\n");
        }
        if (isOp) BotCmdRun.OP.outPut.clear();
        else BotCmdRun.CUSTOM.outPut.clear();
        return result;
    }

    private static void GroupCmd(long groupId, String cmd, boolean isOp, boolean OPEscape) {
        Const.groupMsg(groupId, CmdMain(cmd, isOp, OPEscape).toString());
    }

    private static void GuildCmd(String guildId, String channelId, String cmd, boolean isOp, boolean OPEscape) {
        Const.guildMsg(guildId, channelId, CmdMain(cmd, isOp, OPEscape).toString());
    }

    public static void invokeCommandGroup(GroupMessageEvent event) {
        String command = event.getMessage().substring(1);//去除前缀
        String origincommand = command;
        command = BotUtils.cmdParse(command);
        String performedcommand = command;

        if (performedcommand.equals("list")) {
            // 如果指令包含list,则强行以非管理员身份执行
            CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                    .filter(customCmd -> customCmd.getRequirePermission() < 1 && performedcommand.equals(customCmd.getCmdAlies()))
                    .forEach(customCmd -> GroupCmd(event.getGroupId(), BotUtils.varParse(customCmd, origincommand), false, customCmd.isOPEscape()));
        }else{
            if (BotUtils.groupAdminParse(event)) {
                CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                        .filter(customCmd -> performedcommand.equals(customCmd.getCmdAlies()))
                        .forEach(customCmd -> GroupCmd(event.getGroupId(), BotUtils.varParse(customCmd, origincommand), true, customCmd.isOPEscape()));//admin
            } else
                CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                        .filter(customCmd -> customCmd.getRequirePermission() < 1 && performedcommand.equals(customCmd.getCmdAlies()))
                        .forEach(customCmd -> GroupCmd(event.getGroupId(), BotUtils.varParse(customCmd, origincommand), false, customCmd.isOPEscape()));
        }

    }

    public static void invokeCommandGuild(GuildMessageEvent event) {
        String command = event.getMessage().substring(1);//去除前缀

        if (BotUtils.guildAdminParse(event)) {
            CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                    .filter(customCmd -> command.contains(customCmd.getCmdAlies()))
                    .forEach(customCmd -> GuildCmd(event.getGuildId(), event.getChannelId(), BotUtils.varParse(customCmd, command), true, customCmd.isOPEscape()));//admin
        } else CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                .filter(customCmd -> customCmd.getRequirePermission() < 1 && command.contains(customCmd.getCmdAlies()))
                .forEach(customCmd -> GuildCmd(event.getGuildId(), event.getChannelId(), BotUtils.varParse(customCmd, command), false, customCmd.isOPEscape()));
    }
}
