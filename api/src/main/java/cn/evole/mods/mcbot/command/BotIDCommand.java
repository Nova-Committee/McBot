package cn.evole.mods.mcbot.command;

import cn.evole.mods.mcbot.init.handler.ConfigHandler;
import cn.evole.mods.multi.common.ComponentWrapper;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;

public class BotIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        long id = context.getArgument("BotId", Long.class);
        ConfigHandler.cached().getCommon().setBotId(id);
        context.getSource().sendSuccess(
                ComponentWrapper.literal("已设置机器人QQ号为:" + id), true);
        ConfigHandler.save();
        return 1;
    }


}