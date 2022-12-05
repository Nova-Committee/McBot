package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class VerifyKeyCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var id = context.getArgument("VerifyKey", String.class);
        BotApi.config.getBotConfig().setToken(id);
        ConfigHandler.save(BotApi.config);
        context.getSource().sendSuccess(
                Component.literal("已设置Mirai框架的VerifyKey为:" + id), true);
        return Command.SINGLE_SUCCESS;
    }


}
