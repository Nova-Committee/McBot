package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;


public class SendCommand {


    public static int welcomeExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setS_WELCOME_ENABLE(isEnabled);
        ConfigManger.saveBotConfig(BotApi.config);
        if (isEnabled) {
            context.getSource().sendSuccess(
                    new TextComponent("发送新人加入QQ群的消息开关已被设置为打开"), true);
        } else {
            context.getSource().sendSuccess(
                    new TextComponent("发送新人加入QQ群的消息开关已被设置为关闭"), true);
        }
        return 0;
    }

    public static int allExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setSEND_ENABLED(isEnabled);
        ConfigManger.saveBotConfig(BotApi.config);
        if (isEnabled) {
            context.getSource().sendSuccess(
                    new TextComponent("全局发送消息开关已被设置为打开"), true);
        } else {
            context.getSource().sendSuccess(
                    new TextComponent("全局发送消息开关已被设置为关闭"), true);
        }
        return 0;
    }

    public static int joinExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setS_JOIN_ENABLE(isEnabled);
        if (isEnabled) {
            BotApi.config.getStatus().setSEND_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家加入游戏消息开关已被设置为打开"), true);
        } else {
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家加入游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }

    public static int leaveExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setS_LEAVE_ENABLE(isEnabled);
        if (isEnabled) {
            BotApi.config.getStatus().setSEND_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家离开游戏消息开关已被设置为打开"), true);
        } else {
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家离开游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }

    public static int deathExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setS_DEATH_ENABLE(isEnabled);
        if (isEnabled) {
            BotApi.config.getStatus().setSEND_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家死亡游戏消息开关已被设置为打开"), true);
        } else {
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家死亡游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }

    public static int chatExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setS_CHAT_ENABLE(isEnabled);
        if (isEnabled) {
            BotApi.config.getStatus().setSEND_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家聊天游戏消息开关已被设置为打开"), true);
        } else {
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家聊天游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }

    public static int achievementsExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setS_ADVANCE_ENABLE(isEnabled);
        if (isEnabled) {
            BotApi.config.getStatus().setSEND_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家成就游戏消息开关已被设置为打开"), true);
        } else {
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家成就游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }

}
