package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

/**
 * @author cnlimiter
 * @date 2021/11/17 13:05
 */
public class DebugCommand {

    public static void execute(ICommandSender sender, String[] args) throws CommandException {
        boolean isEnabled = Boolean.getBoolean(args[1]);
        ConfigHandler.cached().getCommon().setDebuggable(isEnabled);
        if (isEnabled) {
            sender.addChatMessage(new ChatComponentText("已开启开发者模式"));
        } else {
            sender.addChatMessage(new ChatComponentText("已关闭开发者模式"));
        }
        ConfigHandler.save();

    }
}
