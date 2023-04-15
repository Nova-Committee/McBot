package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;


public class SendCommand {


    public static void welcomeExecute(ICommandSender sender, String[] args) throws CommandException {
        boolean isEnabled = Boolean.getBoolean(args[2]);
        ConfigHandler.cached().getStatus().setS_QQ_WELCOME_ENABLE(isEnabled);
        if (isEnabled) {
            sender.addChatMessage(
                    new ChatComponentText("发送新人加入QQ群的消息开关已被设置为打开"));
        } else {
            sender.addChatMessage(
                    new ChatComponentText("发送新人加入QQ群的消息开关已被设置为关闭"));
        }
        ConfigHandler.save();

    }

    public static void allExecute(ICommandSender sender, String[] args) throws CommandException {
        boolean isEnabled = Boolean.getBoolean(args[2]);
        ConfigHandler.cached().getStatus().setSEND_ENABLED(isEnabled);
        if (isEnabled) {
            sender.addChatMessage(
                    new ChatComponentText("全局发送消息开关已被设置为打开"));
        } else {
            sender.addChatMessage(
                    new ChatComponentText("全局发送消息开关已被设置为关闭"));
        }
        ConfigHandler.save();

    }

    public static void joinExecute(ICommandSender sender, String[] args) throws CommandException {
        boolean isEnabled = Boolean.getBoolean(args[2]);
        ConfigHandler.cached().getStatus().setS_JOIN_ENABLE(isEnabled);
        if (isEnabled) {
            ConfigHandler.cached().getStatus().setSEND_ENABLED(true);
            sender.addChatMessage(
                    new ChatComponentText("发送玩家加入游戏消息开关已被设置为打开"));
        } else {
            sender.addChatMessage(
                    new ChatComponentText("发送玩家加入游戏消息开关已被设置为关闭"));
        }
        ConfigHandler.save();

    }

    public static void leaveExecute(ICommandSender sender, String[] args) throws CommandException {
        boolean isEnabled = Boolean.getBoolean(args[2]);
        ConfigHandler.cached().getStatus().setS_LEAVE_ENABLE(isEnabled);
        if (isEnabled) {
            ConfigHandler.cached().getStatus().setSEND_ENABLED(true);
            sender.addChatMessage(
                    new ChatComponentText("发送玩家离开游戏消息开关已被设置为打开"));
        } else {
            sender.addChatMessage(
                    new ChatComponentText("发送玩家离开游戏消息开关已被设置为关闭"));
        }
        ConfigHandler.save();

    }

    public static void deathExecute(ICommandSender sender, String[] args) throws CommandException {
        boolean isEnabled = Boolean.getBoolean(args[2]);
        ConfigHandler.cached().getStatus().setS_DEATH_ENABLE(isEnabled);
        if (isEnabled) {
            ConfigHandler.cached().getStatus().setSEND_ENABLED(true);
            sender.addChatMessage(
                    new ChatComponentText("发送玩家死亡游戏消息开关已被设置为打开"));
        } else {
            sender.addChatMessage(
                    new ChatComponentText("发送玩家死亡游戏消息开关已被设置为关闭"));
        }
        ConfigHandler.save();

    }

    public static void chatExecute(ICommandSender sender, String[] args) throws CommandException {
        boolean isEnabled = Boolean.getBoolean(args[2]);
        ConfigHandler.cached().getStatus().setS_CHAT_ENABLE(isEnabled);
        if (isEnabled) {
            ConfigHandler.cached().getStatus().setSEND_ENABLED(true);
            sender.addChatMessage(
                    new ChatComponentText("发送玩家聊天游戏消息开关已被设置为打开"));
        } else {
            sender.addChatMessage(
                    new ChatComponentText("发送玩家聊天游戏消息开关已被设置为关闭"));
        }
        ConfigHandler.save();

    }

    public static void achievementsExecute(ICommandSender sender, String[] args) throws CommandException {
        boolean isEnabled = Boolean.getBoolean(args[2]);
        ConfigHandler.cached().getStatus().setS_ADVANCE_ENABLE(isEnabled);
        if (isEnabled) {
            ConfigHandler.cached().getStatus().setSEND_ENABLED(true);
            sender.addChatMessage(
                    new ChatComponentText("发送玩家成就游戏消息开关已被设置为打开"));
        } else {
            sender.addChatMessage(
                    new ChatComponentText("发送玩家成就游戏消息开关已被设置为关闭"));
        }
        ConfigHandler.save();

    }

}
