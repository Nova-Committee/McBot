package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class BotIDCommand {


    public static void execute(ICommandSender sender, String[] args) throws CommandException {
        long id = Long.getLong(args[1]);
        ConfigHandler.cached().getCommon().setBotId(id);
        sender.addChatMessage(
                new ChatComponentText("已设置机器人QQ号为:" + id));
        ConfigHandler.save();

    }


}
