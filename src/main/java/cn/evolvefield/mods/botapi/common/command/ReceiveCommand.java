package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.CommandBase;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.List;

import static net.minecraft.command.CommandBase.parseBoolean;

public class ReceiveCommand extends CommandBase {



    public ReceiveCommand(){
        super("receive");
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
                BotApi.config.getCommon().setRECEIVE_ENABLED(isEnabled);
                ConfigManger.saveBotConfig(BotApi.config);
                if (isEnabled)
                {
                    sender.addChatMessage(new ChatComponentText("接收所有群消息开关已被设置为打开"));
                }
                else
                {
                    sender.addChatMessage(new ChatComponentText("接收所有群消息开关已被设置为关闭"));
                }
                break;
            }
            case "chat":{
                BotApi.config.getCommon().setR_CHAT_ENABLE(isEnabled);
                if (isEnabled)
                {
                    BotApi.config.getCommon().setRECEIVE_ENABLED(true);
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.addChatMessage(new ChatComponentText("接收群内聊天消息开关已被设置为打开"));
                }
                else
                {
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.addChatMessage(new ChatComponentText("接收群内聊天消息开关已被设置为关闭"));
                }
                break;
            }
            case "cmd":{
                BotApi.config.getCommon().setR_COMMAND_ENABLED(isEnabled);
                if (isEnabled)
                {
                    BotApi.config.getCommon().setRECEIVE_ENABLED(true);
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.addChatMessage(new ChatComponentText("接收群内命令消息开关已被设置为打开"));
                }
                else
                {
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.addChatMessage(new ChatComponentText("接收群内命令消息开关已被设置为关闭"));
                }
                break;
            }
        }
    }


    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        List<String> list = super.addTabCompletionOptions(sender, args);
        if (args[0].isEmpty() || args[0].startsWith("a"))
            list.add("all");
        if (args[0].isEmpty() || args[0].startsWith("ch"))
            list.add("chat");
        if (args[0].isEmpty() || args[0].startsWith("cm"))
            list.add("cmd");
        return list;
    }

}
