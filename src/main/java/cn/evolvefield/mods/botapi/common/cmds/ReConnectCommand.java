package cn.evolvefield.mods.botapi.common.cmds;


import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;


public class ReConnectCommand {

    public static void execute(ICommandSender sender, String[] args) throws CommandException {
        boolean isEnabled = Boolean.getBoolean(args[1]);
        ConfigHandler.cached().getBotConfig().setReconnect(isEnabled);
        if (isEnabled) {
            sender.addChatMessage(new ChatComponentText("已设置自动重连"));
        } else {
            sender.addChatMessage(new ChatComponentText("已关闭自动重连"));
        }

        ConfigHandler.save();

    }
}
