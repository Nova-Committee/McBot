package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

import static net.minecraft.commands.Commands.literal;


public class SendCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return literal("send")
                .then(literal("all")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::allExecute)))
                .then(literal("join")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::joinExecute)))
                .then(literal("leave")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::leaveExecute)))
                .then(literal("death")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::deathExecute)))
                .then(literal("chat")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::chatExecute)))
                .then(literal("achievements")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::achievementsExecute)))
                .then(literal("welcome")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::welcomeExecute)))
                ;
    }
    public static int welcomeExecute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setS_WELCOME_ENABLE(isEnabled);
        ConfigManger.saveBotConfig(BotApi.config);
        if (isEnabled)
        {
            context.getSource().sendSuccess(
                    new TextComponent("发送新人加入QQ群的消息开关已被设置为打开"), true);
        }
        else
        {
            context.getSource().sendSuccess(
                    new TextComponent("发送新人加入QQ群的消息开关已被设置为关闭"), true);
        }
        return 0;
    }

    public static int allExecute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setSEND_ENABLED(isEnabled);
        ConfigManger.saveBotConfig(BotApi.config);
        if (isEnabled)
        {
            context.getSource().sendSuccess(
                    new TextComponent("全局发送消息开关已被设置为打开"), true);
        }
        else
        {
            context.getSource().sendSuccess(
                    new TextComponent("全局发送消息开关已被设置为关闭"), true);
        }
        return 0;
    }
    public static int joinExecute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setS_JOIN_ENABLE(isEnabled);
        if (isEnabled)
        {
            BotApi.config.getCommon().setSEND_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家加入游戏消息开关已被设置为打开"), true);
        }
        else
        {
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家加入游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }
    public static int leaveExecute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setS_LEAVE_ENABLE(isEnabled);
        if (isEnabled)
        {
            BotApi.config.getCommon().setSEND_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家离开游戏消息开关已被设置为打开"), true);
        }
        else
        {
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家离开游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }
    public static int deathExecute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setS_DEATH_ENABLE(isEnabled);
        if (isEnabled)
        {
            BotApi.config.getCommon().setSEND_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家死亡游戏消息开关已被设置为打开"), true);
        }
        else
        {
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家死亡游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }
    public static int chatExecute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setS_CHAT_ENABLE(isEnabled);
        if (isEnabled)
        {
            BotApi.config.getCommon().setSEND_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家聊天游戏消息开关已被设置为打开"), true);
        }
        else
        {
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家聊天游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }
    public static int achievementsExecute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setS_ADVANCE_ENABLE(isEnabled);
        if (isEnabled)
        {
            BotApi.config.getCommon().setSEND_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家成就游戏消息开关已被设置为打开"), true);
        }
        else
        {
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家成就游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }

}
