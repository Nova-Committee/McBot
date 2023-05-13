package cn.evole.mods.mcbot.command;


import cn.evole.mods.mcbot.init.handler.CustomCmdHandler;
import cn.evole.mods.multi.common.ComponentWrapper;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;

public class ListCustomCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        StringBuilder out = new StringBuilder();
        for (String s : CustomCmdHandler.INSTANCE.getCustomCmdMap().keySet()) {
            out.append(s).append("\n");
        }
        context.getSource().sendSuccess(ComponentWrapper.literal(out.toString()), true);
        return 1;
    }
}
