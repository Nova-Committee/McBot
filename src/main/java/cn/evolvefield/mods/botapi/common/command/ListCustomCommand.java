package cn.evolvefield.mods.botapi.common.command;


import cn.evolvefield.mods.botapi.init.handler.CustomCmdHandler;
import cn.evolvefield.mods.multi.common.ComponentWrapper;
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
