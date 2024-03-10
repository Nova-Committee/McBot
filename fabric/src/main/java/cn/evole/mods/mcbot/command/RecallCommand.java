package cn.evole.mods.mcbot.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;

public class RecallCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        int id = context.getArgument("MessageId", Integer.class);
        //McBot.bot.deleteMsg(id);
        return 1;
    }
}
