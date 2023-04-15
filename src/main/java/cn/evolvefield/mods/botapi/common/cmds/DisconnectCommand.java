package cn.evolvefield.mods.botapi.common.cmds;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;


public class DisconnectCommand {

    public static void execute(ICommandSender sender, String[] args) throws CommandException {
        if (BotApi.service != null) {
            BotApi.service.stop();
            if (!BotApi.service.ws.isOpen()) {
                sender.addChatMessage(new ChatComponentText("WebSocket已断开连接"));
            } else {
                sender.addChatMessage(new ChatComponentText("WebSocket目前未连接"));
            }
            ConfigHandler.cached().getCommon().setEnable(false);
        }
        ConfigHandler.save();

    }
}
