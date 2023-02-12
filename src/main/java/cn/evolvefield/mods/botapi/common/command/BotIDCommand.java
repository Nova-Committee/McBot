package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class BotIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        long id = context.getArgument("BotId", Long.class);
        ConfigHandler.cached().getCommon().setBotId(id);
        context.getSource().sendSuccess(
                Component.literal("已设置机器人QQ号为:" + id), true);
        ConfigHandler.save();
        return Command.SINGLE_SUCCESS;
    }


}
