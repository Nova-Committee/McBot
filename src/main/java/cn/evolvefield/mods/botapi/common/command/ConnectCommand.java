package cn.evolvefield.mods.botapi.common.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.bot.BotData;
import cn.evolvefield.mods.botapi.core.service.WebSocketService;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

import java.util.regex.Pattern;

public class ConnectCommand {

    public static int cqhttpExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var parameter = context.getArgument("parameter", String.class);


        var pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
        var matcher = pattern.matcher(parameter);
        if (matcher.find()) {
            BotData.setWs("ws://" + parameter);
            BotApi.config.getCommon().setWsCommon("ws://" + parameter);
            BotData.setBotFrame("cqhttp");
            BotApi.config.getCommon().setFrame("cqhttp");
            context.getSource().sendSuccess(new TextComponent("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "cqhttp"), true);
            WebSocketService.main(BotData.getWs());
            BotApi.config.getStatus().setRECEIVE_ENABLED(true);
            BotApi.config.getCommon().setEnable(true);
            ConfigManger.saveBotConfig(BotApi.config);

            return 1;

        } else {
            context.getSource().sendSuccess(new TextComponent(ChatFormatting.RED + "参数错误❌"), true);
            return 0;
        }
    }

    public static int miraiExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var parameter = context.getArgument("parameter", String.class);

        var pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
        var matcher = pattern.matcher(parameter);
        if (matcher.find()) {
            BotData.setWs("ws://" + parameter);
            BotApi.config.getMirai().setWsMirai("ws://" + parameter);
            BotData.setBotFrame("mirai");
            BotApi.config.getCommon().setFrame("mirai");
            context.getSource().sendSuccess(new TextComponent("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "mirai"), true);
            WebSocketService.main(BotData.getWs() + "/all?verifyKey=" + BotData.getVerifyKey() + "&qq=" + BotData.getQQId());
            BotApi.config.getStatus().setRECEIVE_ENABLED(true);
            BotApi.config.getCommon().setEnable(true);
            ConfigManger.saveBotConfig(BotApi.config);

            return 1;

        } else {
            context.getSource().sendSuccess(new TextComponent(ChatFormatting.RED + "参数错误"), true);
            return 0;
        }
    }

    public static int cqhttpCommonExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        context.getSource().sendSuccess(new TextComponent("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "cqhttp"), true);
        WebSocketService.main(BotApi.config.getCommon().getWsCommon());
        BotApi.config.getStatus().setRECEIVE_ENABLED(true);
        BotApi.config.getCommon().setEnable(true);
        ConfigManger.saveBotConfig(BotApi.config);
        return 1;

    }

    public static int miraiCommonExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {


        context.getSource().sendSuccess(new TextComponent("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "mirai"), true);
        WebSocketService.main(BotApi.config.getMirai().getWsMirai() + "/all?verifyKey=" + BotData.getVerifyKey() + "&qq=" + BotData.getQQId());
        BotApi.config.getStatus().setRECEIVE_ENABLED(true);
        BotApi.config.getCommon().setEnable(true);
        ConfigManger.saveBotConfig(BotApi.config);

        return 1;

    }

}
