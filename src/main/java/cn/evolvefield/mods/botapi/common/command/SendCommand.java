package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.CommandBase;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.List;

import static net.minecraft.command.CommandBase.parseBoolean;


public class SendCommand extends CommandBase {


    public SendCommand(){
        super("send");
    }


    @Override
    public int getPermissionLevel() {
        return 2;
    }

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        boolean isEnabled ;
        isEnabled = parseBoolean(sender, args[1]);
        switch (args[0]){
            default:{
                sender.addChatMessage(new ChatComponentText("参数不合法"));
                break;
            }
            case "all":{
                BotApi.config.getCommon().setSEND_ENABLED(isEnabled);
                ConfigManger.saveBotConfig(BotApi.config);
                if (isEnabled)
                {
                    sender.addChatMessage(new ChatComponentText("发送消息开关已被设置为打开"));
                }
                else
                {
                    sender.addChatMessage(new ChatComponentText("发送消息开关已被设置为关闭"));
                }
                break;
            }
            case "welcome":{
                BotApi.config.getCommon().setS_WELCOME_ENABLE(isEnabled);
                ConfigManger.saveBotConfig(BotApi.config);
                if (isEnabled)
                {
                    sender.addChatMessage(new ChatComponentText("发送新人加入QQ群的消息开关已被设置为打开"));
                }
                else
                {
                    sender.addChatMessage(new ChatComponentText("发送新人加入QQ群的消息开关已被设置为关闭"));
                }
                break;
            }
            case "join":{
                BotApi.config.getCommon().setS_JOIN_ENABLE(isEnabled);
                if (isEnabled)
                {
                    BotApi.config.getCommon().setSEND_ENABLED(true);
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.addChatMessage(new ChatComponentText("发送玩家加入游戏消息开关已被设置为打开"));
                }
                else
                {
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.addChatMessage(new ChatComponentText("发送玩家加入游戏消息开关已被设置为关闭"));
                }
                break;
            }
            case "leave":{
                BotApi.config.getCommon().setS_LEAVE_ENABLE(isEnabled);
                if (isEnabled)
                {
                    BotApi.config.getCommon().setSEND_ENABLED(true);
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.addChatMessage(new ChatComponentText("发送玩家离开游戏消息开关已被设置为打开"));
                }
                else
                {
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.addChatMessage(new ChatComponentText("发送玩家离开游戏消息开关已被设置为关闭"));
                }
                break;
            }
            case "death":{
                BotApi.config.getCommon().setS_DEATH_ENABLE(isEnabled);
                if (isEnabled)
                {
                    BotApi.config.getCommon().setSEND_ENABLED(true);
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.addChatMessage(new ChatComponentText("发送玩家死亡游戏消息开关已被设置为打开"));
                }
                else
                {
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.addChatMessage(new ChatComponentText("发送玩家死亡游戏消息开关已被设置为关闭"));
                }
                break;
            }
            case "chat":{
                BotApi.config.getCommon().setS_CHAT_ENABLE(isEnabled);
                if (isEnabled)
                {
                    BotApi.config.getCommon().setSEND_ENABLED(true);
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.addChatMessage(new ChatComponentText("发送玩家聊天游戏消息开关已被设置为打开"));
                }
                else
                {
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.addChatMessage(new ChatComponentText("发送玩家聊天游戏消息开关已被设置为关闭"));
                }
                break;
            }
            case "achievements":{
                BotApi.config.getCommon().setS_ADVANCE_ENABLE(isEnabled);
                if (isEnabled)
                {
                    BotApi.config.getCommon().setSEND_ENABLED(true);
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.addChatMessage(new ChatComponentText("发送玩家成就游戏消息开关已被设置为打开"));
                }
                else
                {
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.addChatMessage(new ChatComponentText("发送玩家成就游戏消息开关已被设置为关闭"));
                }
                break;
            }
        }
    }


    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        List<String> list = super.addTabCompletionOptions(sender, args);

        if (args[0].isEmpty() || args[0].startsWith("al"))
            list.add("all");
        if (args[0].isEmpty() || args[0].startsWith("j"))
            list.add("join");
        if (args[0].isEmpty() || args[0].startsWith("l"))
            list.add("leave");
        if (args[0].isEmpty() || args[0].startsWith("d"))
            list.add("death");
        if (args[0].isEmpty() || args[0].startsWith("ac"))
            list.add("achievements");
        if (args[0].isEmpty() || args[0].startsWith("w"))
            list.add("welcome");


        return list;
    }

}
