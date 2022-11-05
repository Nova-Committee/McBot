package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class ReloadConfigCmd extends CommandBase {


    private final String command;

    public ReloadConfigCmd(String command) {
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
        return "/mcbot " + this.command;
    }


    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        BotApi.config = ConfigHandler.load();
        try {
            BotApi.config = ConfigHandler.load();
            if (BotApi.config == null) {
                sender.sendMessage(new TextComponentString("重载配置失败"));
            }
            sender.sendMessage(new TextComponentString("重载配置成功"));
        } catch (Exception e) {
            sender.sendMessage(new TextComponentString("重载配置失败"));

        }

    }
}
