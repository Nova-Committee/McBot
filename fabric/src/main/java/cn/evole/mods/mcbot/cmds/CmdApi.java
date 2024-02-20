package cn.evole.mods.mcbot.cmds;

import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.McBot;
import cn.evole.mods.mcbot.init.handler.CustomCmdHandler;
import cn.evole.mods.mcbot.util.onebot.BotUtils;
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
        McBot.SERVER.getCommands().performPrefixedCommand(isOp ? BotCmdRun.OP : BotCmdRun.CUSTOM, cmd);//优雅
        //#else
        //$$ McBot.SERVER.getCommands().performCommand(isOp ? BotCmdRun.OP : BotCmdRun.CUSTOM, cmd);
        //#endif
        for (String s : (isOp ? BotCmdRun.OP.outPut : BotCmdRun.CUSTOM.outPut)) {
            result.append(s.replaceAll("§\\S", "")).append("\n");
        }
        if (isOp) BotCmdRun.OP.outPut.clear();
        else BotCmdRun.CUSTOM.outPut.clear();
        return result;
    }

    private static void GroupCmd(long groupId, String cmd, boolean isOp) {
        Const.groupMsg(groupId, CmdMain(cmd, isOp).toString());
    }

    private static void GuildCmd(String guildId, String channelId, String cmd, boolean isOp) {
        Const.guildMsg(guildId, channelId, CmdMain(cmd, isOp).toString());
    }

    public static void invokeCommandGroup(GroupMessageEvent event) {
        String command = event.getMessage().substring(1);//去除前缀
        String origincommand = command;//对原始命令内容进行保存
        command = BotUtils.cmdParse(command);//提取命令中关键词以便于严格校验，现在指令会经过简单的处理，严格校验格式。
        String performedcommand = command;//必须创建一个新的变量才能用于lambda表达式

        if (performedcommand.equals("list")) {
            // 如果指令包含list,则强行以非管理员身份执行，这是对vanish模组的兼容
            CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                    .filter(customCmd -> customCmd.getRequirePermission() < 1 && performedcommand.equals(customCmd.getCmdAlies()))
                    .forEach(customCmd -> GroupCmd(event.getGroupId(), BotUtils.varParse(customCmd, origincommand), false));
        }else{
            if (BotUtils.groupAdminParse(event)) {
                CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                        .filter(customCmd -> performedcommand.equals(customCmd.getCmdAlies()))
                        .forEach(customCmd -> GroupCmd(event.getGroupId(), BotUtils.varParse(customCmd, origincommand), true));//admin
            } else
                CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                        .filter(customCmd -> customCmd.getRequirePermission() < 1 && performedcommand.equals(customCmd.getCmdAlies()))
                        .forEach(customCmd -> GroupCmd(event.getGroupId(), BotUtils.varParse(customCmd, origincommand), false));
        }

    }

    public static void invokeCommandGuild(GuildMessageEvent event) {
        String command = event.getMessage().substring(1);//去除前缀

        if (BotUtils.guildAdminParse(event)) {
            CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                    。filter(customCmd -> command.contains(customCmd.getCmdAlies()))
                    。forEach(customCmd -> GuildCmd(event.getGuildId(), event.getChannelId(), BotUtils.varParse(customCmd, command), true));//admin
        } else CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                。filter(customCmd -> customCmd.getRequirePermission() < 1 && command.contains(customCmd.getCmdAlies()))
                。forEach(customCmd -> GuildCmd(event.getGuildId(), event.getChannelId(), BotUtils.varParse(customCmd, command)， false));
    }
}
