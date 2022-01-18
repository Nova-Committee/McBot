package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class ReceiveCommand extends CommandBase {


    private final String command;

    public ReceiveCommand(String command){
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
        return "/mcbot " + this.command + "<all|chat|cmd> " + "<true|false>";
    }


    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, "all", "chat", "cmd");
        }

        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        boolean isEnabled ;
        isEnabled = parseBoolean(args[1]);
        switch (args[0]){
            default:{
                sender.sendMessage(new TextComponentString("参数不合法"));
                break;
            }
            case "all":{
                BotApi.config.getCommon().setRECEIVE_ENABLED(isEnabled);
                ConfigManger.saveBotConfig(BotApi.config);
                if (isEnabled)
                {
                    sender.sendMessage(new TextComponentString("接收所有群消息开关已被设置为打开"));
                }
                else
                {
                    sender.sendMessage(new TextComponentString("接收所有群消息开关已被设置为关闭"));
                }
                break;
            }
            case "chat":{
                BotApi.config.getCommon().setR_CHAT_ENABLE(isEnabled);
                if (isEnabled)
                {
                    BotApi.config.getCommon().setRECEIVE_ENABLED(true);
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.sendMessage(new TextComponentString("接收群内聊天消息开关已被设置为打开"));
                }
                else
                {
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.sendMessage(new TextComponentString("接收群内聊天消息开关已被设置为关闭"));
                }
                break;
            }
            case "cmd":{
                BotApi.config.getCommon().setR_COMMAND_ENABLED(isEnabled);
                if (isEnabled)
                {
                    BotApi.config.getCommon().setRECEIVE_ENABLED(true);
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.sendMessage(new TextComponentString("接收群内命令消息开关已被设置为打开"));
                }
                else
                {
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.sendMessage(new TextComponentString("接收群内命令消息开关已被设置为关闭"));
                }
                break;
            }
        }
    }
}
