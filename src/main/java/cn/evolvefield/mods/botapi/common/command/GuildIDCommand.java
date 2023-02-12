package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class GuildIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var id = context.getArgument("GuildID", String.class);
        ConfigHandler.cached().getCommon().setGuildOn(true);
        ConfigHandler.cached().getCommon().setGuildId(id);
        context.getSource().sendSuccess(
                Component.literal("已设置互通的频道号为:" + id), true);
        ConfigHandler.save();
        return Command.SINGLE_SUCCESS;
    }


}
