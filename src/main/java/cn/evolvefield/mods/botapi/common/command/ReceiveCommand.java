package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.mods.multi.common.ComponentWrapper;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class ReceiveCommand {

    public static int allExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(isEnabled);
        if (isEnabled) {
            context.getSource().sendSuccess(
                    ComponentWrapper.literal("全局接收群消息开关已被设置为打开"), true);
        } else {
            context.getSource().sendSuccess(
                    ComponentWrapper.literal("全局接收群消息开关已被设置为关闭"), true);
        }
        ConfigHandler.save();
        return 1;
    }

    public static int chatExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ConfigHandler.cached().getStatus().setR_CHAT_ENABLE(isEnabled);
        if (isEnabled) {
            ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
            context.getSource().sendSuccess(
                    ComponentWrapper.literal("接收群内聊天消息开关已被设置为打开"), true);
        } else {
            context.getSource().sendSuccess(
                    ComponentWrapper.literal("接收群内聊天消息开关已被设置为关闭"), true);
        }
        ConfigHandler.save();
        return 1;

    }

    public static int cmdExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ConfigHandler.cached().getStatus().setR_COMMAND_ENABLED(isEnabled);
        if (isEnabled) {
            ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
            context.getSource().sendSuccess(
                    Component.literal("接收群内命令消息开关已被设置为打开"), true);
        } else {
            context.getSource().sendSuccess(
                    Component.literal("接收群内命令消息开关已被设置为关闭"), true);
        }
        ConfigHandler.save();
        return 1;
    }
}
