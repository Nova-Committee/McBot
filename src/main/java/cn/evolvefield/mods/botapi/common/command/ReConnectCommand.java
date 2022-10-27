package cn.evolvefield.mods.botapi.common.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;


public class ReConnectCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getBotConfig().setReconnect(isEnabled);
        ConfigHandler.save(BotApi.config);
        if (isEnabled) {
            context.getSource().sendSuccess(Component.literal("已设置自动重连"), true);
        } else {
            context.getSource().sendSuccess(Component.literal("已关闭自动重连"), true);
        }

        return 0;
    }
}
