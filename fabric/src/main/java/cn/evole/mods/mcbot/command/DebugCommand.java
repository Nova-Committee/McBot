package cn.evole.mods.mcbot.command;

import cn.evole.mods.mcbot.init.handler.ConfigHandler;
import cn.evole.mods.multi.common.ComponentWrapper;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;

/**
 * @author cnlimiter
 * @date 2021/11/17 13:05
 */
public class DebugCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ConfigHandler.cached().getCommon().setDebuggable(isEnabled);
        if (isEnabled) {
            context.getSource().sendSuccess(ComponentWrapper.literal("已开启开发者模式"), true);
        } else {
            context.getSource().sendSuccess(ComponentWrapper.literal("已关闭开发者模式"), true);
        }
        ConfigHandler.save();
        return 1;
    }
}
