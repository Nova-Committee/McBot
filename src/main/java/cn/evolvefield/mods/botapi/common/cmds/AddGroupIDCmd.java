package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class AddGroupIDCmd extends CommandBase {


    private final String command;

    public AddGroupIDCmd(String command) {
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
        return "/mcbot " + this.command + "<GroupId>";
    }




    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        long id;

        id = parseLong(args[0]);
        if (BotApi.config.getCommon().getGroupIdList().contains(id)) {
            sender.sendMessage(new TextComponentString("QQ群号:" + id + "已经出现了！"));
        } else {
            BotApi.config.getCommon().addGroupId(id);
        }
        ConfigHandler.onChange();
        sender.sendMessage(new TextComponentString("已添加互通的群号为:" + id));


    }
}
