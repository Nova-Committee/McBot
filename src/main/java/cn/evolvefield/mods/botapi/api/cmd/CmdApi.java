package cn.evolvefield.mods.botapi.api.cmd;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.Const;
import cn.evolvefield.mods.botapi.init.handler.CustomCmdHandler;
import cn.evolvefield.mods.botapi.util.onebot.BotUtils;
import cn.evolvefield.onebot.client.core.Bot;
import cn.evolvefield.onebot.sdk.event.message.GroupMessageEvent;
import cn.evolvefield.onebot.sdk.event.message.GuildMessageEvent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/2 15:56
 * Version: 1.0
 */
public class CmdApi {
    private static StringBuilder CmdMain(String cmd, boolean isOp) {
        StringBuilder result = new StringBuilder();
        BotApi.SERVER.getCommands().performPrefixedCommand(isOp ? BotCmdRun.OP : BotCmdRun.CUSTOM, cmd);//优雅
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
        String commandHead = event.getMessage().split(" ")[0].substring(1);
        String command = event.getMessage().substring(1);//去除前缀
        Const.LOGGER.info(event);
        Const.LOGGER.info(BotUtils.groupAdminParse(event));
        if (BotUtils.groupAdminParse(event)) {
            CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                    .filter(customCmd -> customCmd.getCmdAlies().equals(commandHead))
                    .forEach(customCmd -> GroupCmd(BotApi.bot, event.getGroupId(), BotUtils.varParse(event, customCmd, command), true));//admin
        } else
            CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                    .filter(customCmd -> customCmd.getRequirePermission() < 1 && customCmd.getCmdAlies().equals(commandHead))
                    .forEach(customCmd -> GroupCmd(BotApi.bot, event.getGroupId(), BotUtils.varParse(event, customCmd, command), false));

    }

    public static void invokeCommandGuild(GuildMessageEvent event) {
        String commandHead = event.getMessage().split(" ")[0].substring(1);
        String command = event.getMessage().substring(1);//去除前缀

        if (BotUtils.guildAdminParse(event)) {
            CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                    .filter(customCmd -> customCmd.getCmdAlies().equals(commandHead))
                    .forEach(customCmd -> GuildCmd(BotApi.bot, event.getGuildId(), event.getChannelId(), BotUtils.varParse(event, customCmd, command), true));//admin
        } else CustomCmdHandler.INSTANCE.getCustomCmds().stream()
                .filter(customCmd -> customCmd.getRequirePermission() < 1 && customCmd.getCmdAlies().equals(commandHead))
                .forEach(customCmd -> GuildCmd(BotApi.bot, event.getGuildId(), event.getChannelId(), BotUtils.varParse(event, customCmd, command), false));
    }
}
