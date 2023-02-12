package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class AddChannelIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var id = context.getArgument("ChannelID", String.class);
        ConfigHandler.cached().getCommon().setGuildOn(true);
        if (ConfigHandler.cached().getCommon().getChannelIdList().contains(id)) {
            context.getSource().sendSuccess(Component.literal("子频道号:" + id + "已经出现了！"), true);
        } else {
            ConfigHandler.cached().getCommon().addChannelId(id);
        }
        ConfigHandler.save();
        return Command.SINGLE_SUCCESS;
    }


}
