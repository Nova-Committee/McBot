package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.mods.multi.common.ComponentWrapper;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;

public class AddGroupIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var id = context.getArgument("GroupId", Long.class);
        if (ConfigHandler.cached().getCommon().getGroupIdList().contains(id)) {
            context.getSource().sendSuccess(ComponentWrapper.literal("QQ群号:" + id + "已经出现了！"), true);
        } else {
            ConfigHandler.cached().getCommon().addGroupId(id);
            context.getSource().sendSuccess(ComponentWrapper.literal("已成功添加QQ群号:" + id + "！"), true);
        }
        ConfigHandler.save();
        return 1;
    }


}
