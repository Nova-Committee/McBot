package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import lombok.val;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class RemoveGroupIDCommand {


    public static void execute(ICommandSender sender, String[] args) throws CommandException {
        val id = Long.getLong(args[1]);
        if (ConfigHandler.cached().getCommon().getGroupIdList().contains(id)) {
            ConfigHandler.cached().getCommon().removeGroupId(id);
        } else {
            sender.addChatMessage(new ChatComponentText("QQ群号:" + id + "并未出现！"));
        }
        ConfigHandler.save();

    }


}
