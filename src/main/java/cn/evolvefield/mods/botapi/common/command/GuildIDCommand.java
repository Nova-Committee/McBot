package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.mods.multi.common.ComponentWrapper;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;

public class GuildIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var id = context.getArgument("GuildID", String.class);
        ConfigHandler.cached().getCommon().setGuildOn(true);
        ConfigHandler.cached().getCommon().setGuildId(id);
        context.getSource().sendSuccess(
                ComponentWrapper.literal("已设置互通的频道号为:" + id), true);
        ConfigHandler.save();
        return 1;
    }


}
