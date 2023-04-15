package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import lombok.val;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class RemoveChannelIDCommand {


    public static void execute(ICommandSender sender, String[] args) throws CommandException {
        val id = args[1];
        if (ConfigHandler.cached().getCommon().getChannelIdList().contains(id)) {
            ConfigHandler.cached().getCommon().removeChannelId(id);
        } else {
            sender.addChatMessage(new ChatComponentText("子频道号:" + id + "并未出现！"));
        }
        ConfigHandler.save();

    }


}
