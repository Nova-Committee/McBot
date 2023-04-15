package cn.evolvefield.mods.botapi.common.cmds;


import cn.evolvefield.mods.botapi.init.handler.CustomCmdHandler;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class ListCustomCommand {

    public static void execute(ICommandSender sender, String[] args) throws CommandException {
        StringBuilder out = new StringBuilder();
        for (String s : CustomCmdHandler.INSTANCE.getCustomCmdMap().keySet()) {
            out.append(s).append("\n");
        }
        sender.addChatMessage(new ChatComponentText(out.toString()));

    }
}
