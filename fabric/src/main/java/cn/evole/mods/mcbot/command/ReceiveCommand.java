package cn.evole.mods.mcbot.command;

import cn.evole.mods.mcbot.init.handler.ConfigHandler;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
//#if MC >= 11900
import net.minecraft.network.chat.Component;
//#else
//$$ import net.minecraft.network.chat.TextComponent;
//#endif

public class ReceiveCommand {

    public static int allExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(isEnabled);
        if (isEnabled) {
            //#if MC >= 11900
            context.getSource().sendSuccess(Component.literal("全局接收群消息开关已被设置为打开"), true);
            //#else
            //$$ context.getSource().sendSuccess(new TextComponent("全局接收群消息开关已被设置为打开"), true);
            //#endif
        } else {
            //#if MC >= 11900
            context.getSource().sendSuccess(Component.literal("全局接收群消息开关已被设置为关闭"), true);
            //#else
            //$$ context.getSource().sendSuccess(new TextComponent("全局接收群消息开关已被设置为关闭"), true);
            //#endif
        }
        ConfigHandler.save();
        return 1;
    }

    public static int chatExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ConfigHandler.cached().getStatus().setR_CHAT_ENABLE(isEnabled);
        if (isEnabled) {
            ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
            //#if MC >= 11900
            context.getSource().sendSuccess(Component.literal("接收群内聊天消息开关已被设置为打开"), true);
            //#else
            //$$ context.getSource().sendSuccess(new TextComponent("接收群内聊天消息开关已被设置为打开"), true);
            //#endif
        } else {
            //#if MC >= 11900
            context.getSource().sendSuccess(Component.literal("接收群内聊天消息开关已被设置为关闭"), true);
            //#else
            //$$ context.getSource().sendSuccess(new TextComponent("接收群内聊天消息开关已被设置为关闭"), true);
            //#endif
        }
        ConfigHandler.save();
        return 1;

    }

    public static int cmdExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ConfigHandler.cached().getStatus().setR_COMMAND_ENABLED(isEnabled);
        if (isEnabled) {
            ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
            //#if MC >= 11900
            context.getSource().sendSuccess(Component.literal("接收群内命令消息开关已被设置为打开"), true);
            //#else
            //$$ context.getSource().sendSuccess(new TextComponent("接收群内命令消息开关已被设置为打开"), true);
            //#endif
        } else {
            //#if MC >= 11900
            context.getSource().sendSuccess(Component.literal("接收群内命令消息开关已被设置为关闭"), true);
            //#else
            //$$ context.getSource().sendSuccess(new TextComponent("接收群内命令消息开关已被设置为关闭"), true);
            //#endif
        }
        ConfigHandler.save();
        return 1;
    }
}
