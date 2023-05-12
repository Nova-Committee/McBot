package cn.evole.mods.mcbot.command;

import cn.evole.mods.mcbot.init.handler.ConfigHandler;
import cn.evole.mods.multi.common.ComponentWrapper;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.val;
import net.minecraft.commands.CommandSourceStack;

public class RemoveChannelIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val id = context.getArgument("ChannelID", String.class);
        if (ConfigHandler.cached().getCommon().getChannelIdList().contains(id)) {
            ConfigHandler.cached().getCommon().removeChannelId(id);
        } else {
            context.getSource().sendSuccess(ComponentWrapper.literal("子频道号:" + id + "并未出现！"), true);
        }
        ConfigHandler.save();
        return 1;
    }


}
