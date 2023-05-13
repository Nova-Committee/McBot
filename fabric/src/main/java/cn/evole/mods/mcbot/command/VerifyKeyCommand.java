package cn.evole.mods.mcbot.command;

import cn.evole.mods.mcbot.init.handler.ConfigHandler;
import cn.evole.mods.multi.common.ComponentWrapper;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.val;
import net.minecraft.commands.CommandSourceStack;

public class VerifyKeyCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val id = context.getArgument("VerifyKey", String.class);
        ConfigHandler.cached().getBotConfig().setToken(id);
        context.getSource().sendSuccess(
                ComponentWrapper.literal("已设置Mirai框架的VerifyKey为:" + id), true);
        ConfigHandler.save();
        return 1;
    }


}
