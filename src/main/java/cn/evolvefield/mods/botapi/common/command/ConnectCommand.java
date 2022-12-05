package cn.evolvefield.mods.botapi.common.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.Static;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.onebot.sdk.connection.ConnectFactory;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import java.util.regex.Pattern;

public class ConnectCommand {

    public static int cqhttpExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var parameter = context.getArgument("parameter", String.class);


        var pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
        var matcher = pattern.matcher(parameter);
        if (matcher.find()) {
            BotApi.config.getBotConfig().setUrl("ws://" + parameter);
            context.getSource().sendSuccess(Component.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "cqhttp"), true);
            BotApi.config.getBotConfig().setMiraiHttp(false);
            try {
                BotApi.service = ConnectFactory.createWebsocketClient(BotApi.config.getBotConfig(), BotApi.blockingQueue);
                BotApi.service.create();//创建websocket连接
                BotApi.bot = BotApi.service.createBot();//创建机器人实例
            } catch (Exception e) {
                Static.LOGGER.error(e.getMessage());
            }
            BotApi.config.getStatus().setRECEIVE_ENABLED(true);
            BotApi.config.getCommon().setEnable(true);
            ConfigHandler.save(BotApi.config);

            return Command.SINGLE_SUCCESS;

        } else {
            context.getSource().sendSuccess(Component.literal(ChatFormatting.RED + "参数错误❌"), true);
            return 0;
        }
    }

    public static int miraiExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var parameter = context.getArgument("parameter", String.class);

        var pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
        var matcher = pattern.matcher(parameter);
        if (matcher.find()) {
            BotApi.config.getBotConfig().setUrl("ws://" + parameter);
            context.getSource().sendSuccess(Component.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "mirai"), true);
            BotApi.config.getBotConfig().setMiraiHttp(true);
            try {
                BotApi.service = ConnectFactory.createWebsocketClient(BotApi.config.getBotConfig(), BotApi.blockingQueue);
                BotApi.service.create();//创建websocket连接
                BotApi.bot = BotApi.service.createBot();//创建机器人实例
            } catch (Exception e) {
                Static.LOGGER.error(e.getMessage());
            }
            BotApi.config.getStatus().setRECEIVE_ENABLED(true);
            BotApi.config.getCommon().setEnable(true);
            ConfigHandler.save(BotApi.config);

            return Command.SINGLE_SUCCESS;

        } else {
            context.getSource().sendSuccess(Component.literal(ChatFormatting.RED + "参数错误"), true);
            return 0;
        }
    }

    public static int cqhttpCommonExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        context.getSource().sendSuccess(Component.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "cqhttp"), true);
        BotApi.config.getBotConfig().setMiraiHttp(false);
        try {
            BotApi.service = ConnectFactory.createWebsocketClient(BotApi.config.getBotConfig(), BotApi.blockingQueue);
            BotApi.service.create();//创建websocket连接
            BotApi.bot = BotApi.service.createBot();//创建机器人实例
        } catch (Exception e) {
            Static.LOGGER.error(e.getMessage());
        }
        BotApi.config.getStatus().setRECEIVE_ENABLED(true);
        BotApi.config.getCommon().setEnable(true);
        ConfigHandler.save(BotApi.config);
        return Command.SINGLE_SUCCESS;

    }

    public static int miraiCommonExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {


        context.getSource().sendSuccess(Component.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "mirai"), true);
        BotApi.config.getBotConfig().setMiraiHttp(true);
        try {
            BotApi.service = ConnectFactory.createWebsocketClient(BotApi.config.getBotConfig(), BotApi.blockingQueue);
            BotApi.service.create();//创建websocket连接
            BotApi.bot = BotApi.service.createBot();//创建机器人实例
        } catch (Exception e) {
            Static.LOGGER.error(e.getMessage());
        }
        BotApi.config.getStatus().setRECEIVE_ENABLED(true);
        BotApi.config.getCommon().setEnable(true);
        ConfigHandler.save(BotApi.config);

        return Command.SINGLE_SUCCESS;

    }

}
