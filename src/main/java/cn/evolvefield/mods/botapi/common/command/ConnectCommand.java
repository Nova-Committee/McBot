package cn.evolvefield.mods.botapi.common.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.CommandBase;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.service.ClientThreadService;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectCommand  extends CommandBase {


    public ConnectCommand(){
        super("connect");

    }



    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        List<String> list = super.addTabCompletionOptions(sender, args);
        if (args[0].isEmpty() || args[0].startsWith("s"))
            list.add("send");
        if (args[0].isEmpty() || args[0].startsWith("r"))
            list.add("receive");
        return list;

    }


    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        switch(args.length) {
            default: {
                sender.addChatMessage(new ChatComponentText("参数不合法"));
                break;
            }
            case 2: {
                Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                Matcher matcher = pattern.matcher(args[0]);
                if (matcher.find()) {
                    BotApi.config.getCommon().setRECEIVE_ENABLED(true);
                    BotApi.config.getCommon().setWsHost(matcher.group(1));
                    BotApi.config.getCommon().setWsPort(Integer.parseInt(matcher.group(2)));
                    BotApi.config.getCommon().setWsKey(args[2]);
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.addChatMessage(new ChatComponentText("已保存，正在尝试建立webSocket连接"));
                    ClientThreadService.runWebSocketClient();
                } else {
                    sender.addChatMessage(new ChatComponentText("格式错误"));
                }
                break;
            }
            case 1: {
                Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                Matcher matcher = pattern.matcher(args[0]);
                if(matcher.find()) {
                    BotApi.config.getCommon().setRECEIVE_ENABLED(true);
                    BotApi.config.getCommon().setWsHost(matcher.group(1));
                    BotApi.config.getCommon().setWsPort(Integer.parseInt(matcher.group(2)));
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.addChatMessage(new ChatComponentText("已保存，正在尝试建立webSocket连接"));
                    ClientThreadService.runWebSocketClient();
                } else {
                    sender.addChatMessage(new ChatComponentText("格式错误"));
                }
                break;
            }
            case 0: {
                sender.addChatMessage(new ChatComponentText("尝试建立连接"));
                ClientThreadService.runWebSocketClient();
                break;
            }

        }
        BotApi.config.getCommon().setEnable(true);
        ConfigManger.saveBotConfig(BotApi.config);
    }

    @Override
    public int getPermissionLevel() {
        return 2;
    }



}
