package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class ReceiveCommand {

    public static void allExecute(ICommandSender sender, String[] args) throws CommandException {
        boolean isEnabled = Boolean.getBoolean(args[2]);
        ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(isEnabled);
        if (isEnabled) {
            sender.addChatMessage(
                    new ChatComponentText("全局接收群消息开关已被设置为打开"));
        } else {
            sender.addChatMessage(
                    new ChatComponentText("全局接收群消息开关已被设置为关闭"));
        }
        ConfigHandler.save();

    }

    public static void chatExecute(ICommandSender sender, String[] args) throws CommandException {
        boolean isEnabled = Boolean.getBoolean(args[2]);
        ConfigHandler.cached().getStatus().setR_CHAT_ENABLE(isEnabled);
        if (isEnabled) {
            ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
            sender.addChatMessage(
                    new ChatComponentText("接收群内聊天消息开关已被设置为打开"));
        } else {
            sender.addChatMessage(
                    new ChatComponentText("接收群内聊天消息开关已被设置为关闭"));
        }
        ConfigHandler.save();


    }

    public static void cmdExecute(ICommandSender sender, String[] args) throws CommandException {
        boolean isEnabled = Boolean.getBoolean(args[2]);
        ConfigHandler.cached().getStatus().setR_COMMAND_ENABLED(isEnabled);
        if (isEnabled) {
            ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
            sender.addChatMessage(
                    new ChatComponentText("接收群内命令消息开关已被设置为打开"));
        } else {
            sender.addChatMessage(
                    new ChatComponentText("接收群内命令消息开关已被设置为关闭"));
        }
        ConfigHandler.save();

    }
}
