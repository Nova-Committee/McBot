package cn.evolvefield.mods.botapi.common.cmds;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public class CommandTree extends CommandTreeBase {
    public CommandTree () {

        this.addSubcommand(new ConnectCommand());
        this.addSubcommand(new DisconnectCommand("disconnect"));
        this.addSubcommand(new ReceiveCommand("receive"));
        this.addSubcommand(new SendCommand("send"));
        this.addSubcommand(new StatusCommand("status"));
        this.addSubcommand(new AddGroupIDCmd("addGroup"));
        this.addSubcommand(new RemoveGroupIDCmd("delGroup"));
        this.addSubcommand(new BotIDCommand("setBot"));
        this.addSubcommand(new VerifyKeyCommand("setVerifyKey"));
        this.addSubcommand(new DebugCommand("debug"));
        this.addSubcommand(new HelpCommand("help"));
        this.addSubcommand(new GuildIDCommand("setGuild"));
        this.addSubcommand(new AddChannelIDCommand("addChannelId"));
        this.addSubcommand(new RemoveChannelIDCommand("delChannelId"));
        this.addSubcommand(new ReloadConfigCmd("reload"));

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
