package cn.evolvefield.mods.botapi.command;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public class CommandTree extends CommandTreeBase {
    public CommandTree () {

        this.addSubcommand(new ConnectCommand("connect"));
        this.addSubcommand(new DisconnectCommand("disconnect"));
        this.addSubcommand(new ReceiveCommand("receive"));
        this.addSubcommand(new SendCommand("send"));
        this.addSubcommand(new StatusCommand("status"));
        this.addSubcommand(new GroupIDCommand("setID"));

    }

    @Override
    public String getName() {
        return "mcbot";
    }

    @Override
    public int getRequiredPermissionLevel () {

        return 0;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/mcbot";
        }
}
