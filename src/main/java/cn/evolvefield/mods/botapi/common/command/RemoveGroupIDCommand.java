package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.mods.multi.common.ComponentWrapper;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;

public class RemoveGroupIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var id = context.getArgument("GroupID", Long.class);
        if (ConfigHandler.cached().getCommon().getGroupIdList().contains(id)) {
            ConfigHandler.cached().getCommon().removeGroupId(id);
        } else {
            context.getSource().sendSuccess(ComponentWrapper.literal("QQ群号:" + id + "并未出现！"), true);
        }
        ConfigHandler.save();
        return 1;
    }


}
