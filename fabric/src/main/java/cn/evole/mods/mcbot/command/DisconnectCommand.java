package cn.evole.mods.mcbot.command;


import cn.evole.mods.mcbot.McBot;
import cn.evole.mods.mcbot.init.handler.ConfigHandler;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
//#if MC >= 11900
import net.minecraft.network.chat.Component;
//#else
//$$ import net.minecraft.network.chat.TextComponent;
//#endif

public class DisconnectCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        if (McBot.service != null) {
            McBot.service.stop();
            if (!McBot.service.ws.isOpen()) {
                //#if MC >= 11900
                context.getSource().sendSuccess(Component.literal("WebSocket已断开连接"), true);
                //#else
                //$$ context.getSource().sendSuccess(new TextComponent("WebSocket已断开连接"), true);
                //#endif
            } else {
                //#if MC >= 11900
                context.getSource().sendSuccess(Component.literal("WebSocket目前未连接"), true);
                //#else
                //$$ context.getSource().sendSuccess(new TextComponent("WebSocket目前未连接"), true);
                //#endif
            }
            ConfigHandler.cached().getCommon().setEnable(false);
        }
        ConfigHandler.save();
        return 1;
    }
}
