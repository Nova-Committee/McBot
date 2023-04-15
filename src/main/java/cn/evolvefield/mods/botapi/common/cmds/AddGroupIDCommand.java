package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import lombok.val;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class AddGroupIDCommand {


    public static void execute(ICommandSender sender, String[] args) throws CommandException {
        val id = Long.getLong(args[1]);
        if (ConfigHandler.cached().getCommon().getGroupIdList().contains(id)) {
            sender.addChatMessage(new ChatComponentText("QQ群号:" + id + "已经出现了！"));
        } else {
            ConfigHandler.cached().getCommon().addGroupId(id);
            sender.addChatMessage(new ChatComponentText("已成功添加QQ群号:" + id + "！"));
        }
        ConfigHandler.save();
    }


}
