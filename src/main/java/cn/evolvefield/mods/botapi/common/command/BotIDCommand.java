package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.mods.multi.common.ComponentWrapper;
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
