package cn.evole.mods.mcbot.command;

import cn.evole.mods.mcbot.data.UserBindApi;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;

public class DelBindCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        String qq_id = context.getArgument("QQId", String.class);
        UserBindApi.del(qq_id);
        return 1;
    }
}
