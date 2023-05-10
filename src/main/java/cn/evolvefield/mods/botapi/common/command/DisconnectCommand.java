package cn.evolvefield.mods.botapi.common.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.mods.multi.common.ComponentWrapper;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;


public class DisconnectCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        if (BotApi.service != null) {
            BotApi.service.stop();
            if (!BotApi.service.ws.isOpen()) {
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
