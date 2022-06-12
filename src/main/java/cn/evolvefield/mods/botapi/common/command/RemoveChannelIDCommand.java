package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class RemoveChannelIDCommand extends CommandBase {


    private final String command;

    public RemoveChannelIDCommand(String command) {
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
        return "/mcbot " + this.command + "<ChannelId>>";
    }


    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (BotApi.config.getCommon().getChannelIdList().contains(args[0])) {
            BotApi.config.getCommon().removeChannelId(args[0]);
        } else {
            sender.sendMessage(new TextComponentString("子频道号:" + args[0] + "并未出现！"));
        }
        ConfigManger.saveBotConfig(BotApi.config);

    }
}
