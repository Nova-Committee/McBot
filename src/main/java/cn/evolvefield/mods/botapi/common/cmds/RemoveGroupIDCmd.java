package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class RemoveGroupIDCmd extends CommandBase {


    private final String command;

    public RemoveGroupIDCmd(String command) {
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
        return "/mcbot " + this.command + "<ChannelId>";
    }


    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        long id = parseLong(args[0]);

        if (BotApi.config.getCommon().getGroupIdList().contains(id)) {
            BotApi.config.getCommon().removeGroupId(id);
        } else {
            sender.sendMessage(new TextComponentString("QQ群号:" + args[0] + "并未出现！"));
        }
        ConfigHandler.onChange();

    }
}
