package cn.evolvefield.mods.botapi.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.config.ConfigManger;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;


public class SendCommand extends CommandBase {
    private final String command;

    public SendCommand(String command){
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
        return "/mcbot " + this.command + "<true|false>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        boolean isEnabled ;

        isEnabled = parseBoolean(args[0]);
        BotApi.config.getCommon().setSEND_ENABLED(isEnabled);
        ConfigManger.saveBotConfig(BotApi.config);
        if (isEnabled)
        {
            sender.sendMessage(new TextComponentString("发送消息开关已被设置为打开"));
        }
        else
        {
            sender.sendMessage(new TextComponentString("发送消息开关已被设置为关闭"));
        }


    }

}
