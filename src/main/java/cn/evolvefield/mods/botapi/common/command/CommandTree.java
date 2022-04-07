package cn.evolvefield.mods.botapi.common.command;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public class CommandTree extends CommandTreeBase {
    public CommandTree () {

        this.addSubcommand(new ConnectCommand());
        this.addSubcommand(new DisconnectCommand("disconnect"));
        this.addSubcommand(new ReceiveCommand("receive"));
        this.addSubcommand(new SendCommand("send"));
        this.addSubcommand(new StatusCommand("status"));
        this.addSubcommand(new GroupIDCommand("setGroup"));
        this.addSubcommand(new FrameCommand("setFrame"));
        this.addSubcommand(new BotIDCommand("setBot"));
        this.addSubcommand(new VerifyKeyCommand("setVerifyKey"));
        this.addSubcommand(new DebugCommand("debug"));
        this.addSubcommand(new HelpCommand("help"));
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
