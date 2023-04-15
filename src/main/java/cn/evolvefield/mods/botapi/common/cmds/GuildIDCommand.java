package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import lombok.val;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class GuildIDCommand {


    public static void execute(ICommandSender sender, String[] args) throws CommandException {
        val id = args[1];
        ConfigHandler.cached().getCommon().setGuildOn(true);
        ConfigHandler.cached().getCommon().setGuildId(id);
        sender.addChatMessage(
                new ChatComponentText("已设置互通的频道号为:" + id));
        ConfigHandler.save();

    }


}
