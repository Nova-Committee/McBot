package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import lombok.val;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class AddChannelIDCommand{


    public static void execute(ICommandSender sender, String[] args) throws CommandException {
        val id = args[1];
        ConfigHandler.cached().getCommon().setGuildOn(true);
        if (ConfigHandler.cached().getCommon().getChannelIdList().contains(id)) {
            sender.addChatMessage(new ChatComponentText("子频道号:" + id + "已经出现了！"));
        } else {
            ConfigHandler.cached().getCommon().addChannelId(id);
        }
        ConfigHandler.save();
    }


}
