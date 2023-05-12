package cn.evole.mods.mcbot.command;


import cn.evole.mods.mcbot.init.handler.ConfigHandler;
import cn.evole.mods.multi.common.ComponentWrapper;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;


public class ReConnectCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ConfigHandler.cached().getBotConfig().setReconnect(isEnabled);
        if (isEnabled) {
            context.getSource().sendSuccess(ComponentWrapper.literal("已设置自动重连"), true);
        } else {
            context.getSource().sendSuccess(ComponentWrapper.literal("已关闭自动重连"), true);
        }

        ConfigHandler.save();
        return 1;
    }
}
