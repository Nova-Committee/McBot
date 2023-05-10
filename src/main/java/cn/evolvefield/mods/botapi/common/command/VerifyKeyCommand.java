package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.mods.multi.common.ComponentWrapper;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;

public class VerifyKeyCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var id = context.getArgument("VerifyKey", String.class);
        ConfigHandler.cached().getBotConfig().setToken(id);
        context.getSource().sendSuccess(
                ComponentWrapper.literal("已设置Mirai框架的VerifyKey为:" + id), true);
        ConfigHandler.save();
        return 1;
    }


}
