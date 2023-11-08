package cn.evole.mods.mcbot.command;


import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.McBot;
import cn.evole.mods.mcbot.init.config.ModConfig;
import cn.evole.onebot.client.connection.ConnectFactory;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.val;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
//#if MC >= 11900
import net.minecraft.network.chat.Component;
//#else
//$$ import net.minecraft.network.chat.TextComponent;
//#endif

import java.util.regex.Pattern;

public class ConnectCommand {

    public static int cqhttpExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val parameter = context.getArgument("parameter", String.class);


        val pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
        val matcher = pattern.matcher(parameter);
        if (matcher.find()) {
            ModConfig.INSTANCE.getBotConfig().setUrl("ws://" + parameter);
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "cqhttp"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "cqhttp"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "cqhttp"), true);
            //#endif
            ModConfig.INSTANCE.getBotConfig().setMiraiHttp(false);

            try {
                McBot.app = new Thread(() -> {
                    McBot.service = new ConnectFactory(ModConfig.INSTANCE.getBotConfig().toBot(), McBot.blockingQueue);//创建websocket连接
                    McBot.bot = McBot.service.ws.createBot();//创建机器人实例
                }, "BotServer");
                McBot.app.start();
            } catch (Exception e) {
                Const.LOGGER.error("§c机器人服务端配置不正确");
            }
            ModConfig.INSTANCE.getStatus().setREnable(true);
            ModConfig.INSTANCE.getCommon().setEnable(true);
            ModConfig.INSTANCE.reload();
            return 1;

        } else {
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal(ChatFormatting.RED + "参数错误❌"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent(ChatFormatting.RED + "参数错误❌"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal(ChatFormatting.RED + "参数错误❌"), true);
            //#endif
            return 0;
        }
    }

    public static int miraiExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val parameter = context.getArgument("parameter", String.class);

        val pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
        val matcher = pattern.matcher(parameter);
        if (matcher.find()) {
            ModConfig.INSTANCE.getBotConfig().setUrl("ws://" + parameter);
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "mirai"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "mirai"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "mirai"), true);
            //#endif
            ModConfig.INSTANCE.getBotConfig().setMiraiHttp(true);
            try {
                McBot.app = new Thread(() -> {
                    McBot.service = new ConnectFactory(ModConfig.INSTANCE.getBotConfig().toBot(), McBot.blockingQueue);//创建websocket连接
                    McBot.bot = McBot.service.ws.createBot();//创建机器人实例
                }, "BotServer");
                McBot.app.start();
            } catch (Exception e) {
                Const.LOGGER.error("§c机器人服务端配置不正确");
            }
            ModConfig.INSTANCE.getStatus().setREnable(true);
            ModConfig.INSTANCE.getCommon().setEnable(true);
            ModConfig.INSTANCE.reload();
            return 1;

        } else {
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal(ChatFormatting.RED + "参数错误❌"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent(ChatFormatting.RED + "参数错误❌"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal(ChatFormatting.RED + "参数错误❌"), true);
            //#endif
            return 0;
        }
    }

    public static int cqhttpCommonExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        //#if MC >= 12000
        context.getSource().sendSuccess(()->Component.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "cqhttp"), true);
        //#elseif MC < 11900
        //$$ context.getSource().sendSuccess(new TextComponent("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "cqhttp"), true);
        //#else
        //$$ context.getSource().sendSuccess(Component.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "cqhttp"), true);
        //#endif
        ModConfig.INSTANCE.getBotConfig().setMiraiHttp(false);
        try {
            McBot.app = new Thread(() -> {
                    McBot.service = new ConnectFactory(ModConfig.INSTANCE.getBotConfig().toBot(), McBot.blockingQueue);//创建websocket连接
                    McBot.bot = McBot.service.ws.createBot();//创建机器人实例
                }, "BotServer");
            McBot.app.start();
        } catch (Exception e) {
            Const.LOGGER.error("§c机器人服务端配置不正确");
        }
        ModConfig.INSTANCE.getStatus().setREnable(true);
        ModConfig.INSTANCE.getCommon().setEnable(true);
        ModConfig.INSTANCE.reload();
        return 1;

    }

    public static int miraiCommonExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        //#if MC >= 12000
        context.getSource().sendSuccess(()->Component.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "mirai"), true);
        //#elseif MC < 11900
        //$$ context.getSource().sendSuccess(new TextComponent("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "mirai"), true);
        //#else
        //$$ context.getSource().sendSuccess(Component.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "mirai"), true);
        //#endif
        ModConfig.INSTANCE.getBotConfig().setMiraiHttp(true);
        try {
            McBot.app = new Thread(() -> {
                    McBot.service = new ConnectFactory(ModConfig.INSTANCE.getBotConfig().toBot(), McBot.blockingQueue);//创建websocket连接
                    McBot.bot = McBot.service.ws.createBot();//创建机器人实例
                }, "BotServer");
            McBot.app.start();
        } catch (Exception e) {
            Const.LOGGER.error("§c机器人服务端配置不正确");
        }
        ModConfig.INSTANCE.getStatus().setREnable(true);
        ModConfig.INSTANCE.getCommon().setEnable(true);

        ModConfig.INSTANCE.reload();
        return 1;

    }

}
