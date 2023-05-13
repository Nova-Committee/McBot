package cn.evole.mods.mcbot.command;


import cn.evole.mods.mcbot.McBot;
import cn.evole.mods.mcbot.init.handler.ConfigHandler;
import cn.evole.mods.multi.common.ComponentWrapper;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;


public class DisconnectCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        if (McBot.service != null) {
            McBot.service.stop();
            if (!McBot.service.ws.isOpen()) {
                context.getSource().sendSuccess(ComponentWrapper.literal("WebSocket已断开连接"), true);
            } else {
                context.getSource().sendSuccess(ComponentWrapper.literal("WebSocket目前未连接"), true);
            }
            ConfigHandler.cached().getCommon().setEnable(false);
        }
        ConfigHandler.save();
        return 1;
    }
}
