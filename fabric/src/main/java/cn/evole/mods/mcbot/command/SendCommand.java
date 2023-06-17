package cn.evole.mods.mcbot.command;

import cn.evole.mods.mcbot.init.handler.ConfigHandler;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
//#if MC >= 11900
import net.minecraft.network.chat.Component;
//#else
//$$ import net.minecraft.network.chat.TextComponent;
//#endif


public class SendCommand {


    public static int welcomeExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ConfigHandler.cached().getStatus().setS_QQ_WELCOME_ENABLE(isEnabled);
        if (isEnabled) {
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("发送新人加入QQ群的消息开关已被设置为打开"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("发送新人加入QQ群的消息开关已被设置为打开"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("发送新人加入QQ群的消息开关已被设置为打开"), true);
            //#endif
        } else {
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("发送新人加入QQ群的消息开关已被设置为关闭"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("发送新人加入QQ群的消息开关已被设置为关闭"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("发送新人加入QQ群的消息开关已被设置为关闭"), true);
            //#endif
        }
        ConfigHandler.save();
        return 1;
    }

    public static int allExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ConfigHandler.cached().getStatus().setSEND_ENABLED(isEnabled);
        if (isEnabled) {
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("全局发送消息开关已被设置为打开"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("全局发送消息开关已被设置为打开"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("全局发送消息开关已被设置为打开"), true);
            //#endif
        } else {
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("全局发送消息开关已被设置为关闭"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("全局发送消息开关已被设置为关闭"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("全局发送消息开关已被设置为关闭"), true);
            //#endif
        }
        ConfigHandler.save();
        return 1;
    }

    public static int joinExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ConfigHandler.cached().getStatus().setS_JOIN_ENABLE(isEnabled);
        if (isEnabled) {
            ConfigHandler.cached().getStatus().setSEND_ENABLED(true);
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("发送玩家加入游戏消息开关已被设置为打开"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("发送玩家加入游戏消息开关已被设置为打开"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("发送玩家加入游戏消息开关已被设置为打开"), true);
            //#endif
        } else {
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("发送玩家加入游戏消息开关已被设置为关闭"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("发送玩家加入游戏消息开关已被设置为关闭"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("发送玩家加入游戏消息开关已被设置为关闭"), true);
            //#endif
        }
        ConfigHandler.save();
        return 1;
    }

    public static int leaveExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ConfigHandler.cached().getStatus().setS_LEAVE_ENABLE(isEnabled);
        if (isEnabled) {
            ConfigHandler.cached().getStatus().setSEND_ENABLED(true);
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("发送玩家离开游戏消息开关已被设置为打开"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("发送玩家离开游戏消息开关已被设置为打开"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("发送玩家离开游戏消息开关已被设置为打开"), true);
            //#endif
        } else {
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("发送玩家离开游戏消息开关已被设置为关闭"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("发送玩家离开游戏消息开关已被设置为关闭"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("发送玩家离开游戏消息开关已被设置为关闭"), true);
            //#endif
        }
        ConfigHandler.save();
        return 1;
    }

    public static int deathExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ConfigHandler.cached().getStatus().setS_DEATH_ENABLE(isEnabled);
        if (isEnabled) {
            ConfigHandler.cached().getStatus().setSEND_ENABLED(true);
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("发送玩家死亡游戏消息开关已被设置为打开"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("发送玩家死亡游戏消息开关已被设置为打开"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("发送玩家死亡游戏消息开关已被设置为打开"), true);
            //#endif
        } else {
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("发送玩家死亡游戏消息开关已被设置为关闭"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("发送玩家死亡游戏消息开关已被设置为关闭"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("发送玩家死亡游戏消息开关已被设置为关闭"), true);
            //#endif
        }
        ConfigHandler.save();
        return 1;
    }

    public static int chatExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ConfigHandler.cached().getStatus().setS_CHAT_ENABLE(isEnabled);
        if (isEnabled) {
            ConfigHandler.cached().getStatus().setSEND_ENABLED(true);
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("发送玩家聊天游戏消息开关已被设置为打开"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("发送玩家聊天游戏消息开关已被设置为打开"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("发送玩家聊天游戏消息开关已被设置为打开"), true);
            //#endif
        } else {
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("发送玩家聊天游戏消息开关已被设置为关闭"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("发送玩家聊天游戏消息开关已被设置为关闭"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("发送玩家聊天游戏消息开关已被设置为关闭"), true);
            //#endif
        }
        ConfigHandler.save();
        return 1;
    }

    public static int achievementsExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ConfigHandler.cached().getStatus().setS_ADVANCE_ENABLE(isEnabled);
        if (isEnabled) {
            ConfigHandler.cached().getStatus().setSEND_ENABLED(true);
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("发送玩家成就游戏消息开关已被设置为打开"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("发送玩家成就游戏消息开关已被设置为打开"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("发送玩家成就游戏消息开关已被设置为打开"), true);
            //#endif
        } else {
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("发送玩家成就游戏消息开关已被设置为关闭"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("发送玩家成就游戏消息开关已被设置为关闭"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("发送玩家成就游戏消息开关已被设置为关闭"), true);
            //#endif
        }
        ConfigHandler.save();
        return 1;
    }

}
