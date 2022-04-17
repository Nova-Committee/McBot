package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.bot.BotData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class GroupIDCommand extends CommandBase {


    private final String command;

    public GroupIDCommand(String command){
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
        return "/mcbot " + this.command + "<GroupId>>";
    }




    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        long id ;

        id = parseLong(args[0]);
        BotData.setGroupId(id);
        BotApi.config.getCommon().setGroupId(id);
        ConfigManger.saveBotConfig(BotApi.config);

        sender.sendMessage(new TextComponentString("已设置互通的群号为:"+ id));


    }
}
