package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

/**
 * @author cnlimiter
 * @date 2021/11/17 13:10
 */
public class DebugCommand extends CommandBase {
    private final String command;

    public DebugCommand(String command){
        this.command = command;
    }


    @Override
    public String getName() {
        return this.command;
    }


    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/mcbot " + this.command ;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        boolean isEnabled ;
        isEnabled = parseBoolean(args[0]);
        BotApi.config.getCommon().setDebuggable(isEnabled);
        ConfigHandler.onChange();
        if (isEnabled) {
            sender.sendMessage(new TextComponentString("已开启开发者模式"));
        } else {
            sender.sendMessage(new TextComponentString("已关闭开发者模式"));
        }

    }
}
