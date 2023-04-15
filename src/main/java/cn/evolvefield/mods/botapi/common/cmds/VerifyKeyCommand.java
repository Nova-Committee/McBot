package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import lombok.val;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class VerifyKeyCommand {


    public static void execute(ICommandSender sender, String[] args) throws CommandException {
        val id = args[1];
        ConfigHandler.cached().getBotConfig().setToken(id);
        sender.addChatMessage(
                new ChatComponentText("已设置Mirai框架的VerifyKey为:" + id));
        ConfigHandler.save();

    }


}
