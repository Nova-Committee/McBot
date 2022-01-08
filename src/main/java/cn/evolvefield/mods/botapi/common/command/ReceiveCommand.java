package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.config.ConfigManger;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

import static net.minecraft.commands.Commands.literal;

public class ReceiveCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return literal("receive")
                .then(literal("all")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(ReceiveCommand::allExecute)))
                .then(literal("chat")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(ReceiveCommand::chatExecute)))
                .then(literal("cmd")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(ReceiveCommand::cmdExecute)))
                ;
    }
    public static int allExecute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setRECEIVE_ENABLED(isEnabled);
        ConfigManger.saveBotConfig(BotApi.config);
        if (isEnabled)
        {
            context.getSource().sendSuccess(
                    new TextComponent("全局接收群消息开关已被设置为打开"), true);
        }
        else
        {
            context.getSource().sendSuccess(
                    new TextComponent("全局接收群消息开关已被设置为关闭"), true);
        }
        return 0;
    }

    public static int chatExecute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setR_CHAT_ENABLE(isEnabled);
        if (isEnabled)
        {
            BotApi.config.getCommon().setRECEIVE_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("接收群内聊天消息开关已被设置为打开"), true);
        }
        else
        {
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("接收群内聊天消息开关已被设置为关闭"), true);
        }
        return 0;

    }
    public static int cmdExecute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setR_COMMAND_ENABLED(isEnabled);
        if (isEnabled)
        {
            BotApi.config.getCommon().setRECEIVE_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("接收群内命令消息开关已被设置为打开"), true);
        }
        else
        {
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("接收群内命令消息开关已被设置为关闭"), true);
        }
        return 0;
    }
}
