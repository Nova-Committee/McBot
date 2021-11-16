package cn.evolvefield.mods.botapi.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.config.ConfigManger;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;


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
        return "/mcbot " + this.command + "<all|join|leave|death|achievements> " + "<true|false>";
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, "all", "join", "leave", "death", "achievements");
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
                break;
            }
            case "join":{
                BotApi.config.getCommon().setS_JOIN_ENABLE(isEnabled);
                if (isEnabled)
                {
                    BotApi.config.getCommon().setSEND_ENABLED(true);
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.sendMessage(new TextComponentString("发送玩家加入游戏消息开关已被设置为打开"));
                }
                else
                {
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.sendMessage(new TextComponentString("发送玩家加入游戏消息开关已被设置为关闭"));
                }
                break;
            }
            case "leave":{
                BotApi.config.getCommon().setS_LEAVE_ENABLE(isEnabled);
                if (isEnabled)
                {
                    BotApi.config.getCommon().setSEND_ENABLED(true);
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.sendMessage(new TextComponentString("发送玩家离开游戏消息开关已被设置为打开"));
                }
                else
                {
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.sendMessage(new TextComponentString("发送玩家离开游戏消息开关已被设置为关闭"));
                }
                break;
            }
            case "death":{
                BotApi.config.getCommon().setS_DEATH_ENABLE(isEnabled);
                if (isEnabled)
                {
                    BotApi.config.getCommon().setSEND_ENABLED(true);
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.sendMessage(new TextComponentString("发送玩家死亡游戏消息开关已被设置为打开"));
                }
                else
                {
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.sendMessage(new TextComponentString("发送玩家死亡游戏消息开关已被设置为关闭"));
                }
                break;
            }
            case "chat":{
                BotApi.config.getCommon().setS_CHAT_ENABLE(isEnabled);
                if (isEnabled)
                {
                    BotApi.config.getCommon().setSEND_ENABLED(true);
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.sendMessage(new TextComponentString("发送玩家聊天游戏消息开关已被设置为打开"));
                }
                else
                {
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.sendMessage(new TextComponentString("发送玩家聊天游戏消息开关已被设置为关闭"));
                }
                break;
            }
            case "achievements":{
                BotApi.config.getCommon().setS_ADVANCE_ENABLE(isEnabled);
                if (isEnabled)
                {
                    BotApi.config.getCommon().setSEND_ENABLED(true);
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.sendMessage(new TextComponentString("发送玩家成就游戏消息开关已被设置为打开"));
                }
                else
                {
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.sendMessage(new TextComponentString("发送玩家成就游戏消息开关已被设置为关闭"));
                }
                break;
            }
        }

    }

}
