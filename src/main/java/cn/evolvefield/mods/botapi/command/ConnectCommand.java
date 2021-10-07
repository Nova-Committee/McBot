package cn.evolvefield.mods.botapi.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.config.ConfigManger;
import cn.evolvefield.mods.botapi.service.ClientThreadService;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectCommand  extends CommandBase {

    private final String command;

    public ConnectCommand(String command){
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
        return "/mcbot " + this.command +"<host>:<port> <token(可选)>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        //String[] args = Arrays.toString(arg).split("\\s+");
//        if (args.length == 2) {
//            BotApi.config.getCommon().setWsHOST(args[0]);
//            BotApi.config.getCommon().setWsPORT(Integer.parseInt(args[1]));
//            ConfigManger.saveBotConfig(BotApi.config);
//            sender.sendMessage(new TextComponentString("正在尝试建立连接"));
//            ClientThreadService.runWebSocketClient();
//        } else {
//            sender.sendMessage(new TextComponentString("参数不合法"));
//        }


        switch(args.length) {
            default: {
                sender.sendMessage(new TextComponentString("参数不合法"));
                break;
            }
            case 2: {
                Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                Matcher matcher = pattern.matcher(args[0]);
                if (matcher.find()) {
                    BotApi.config.getCommon().setWsHOST(matcher.group(1));
                    BotApi.config.getCommon().setWsPORT(Integer.parseInt(matcher.group(2)));
                    BotApi.config.getCommon().setKEY(args[1]);
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.sendMessage(new TextComponentString("已保存，正在尝试建立连接"));
                    ClientThreadService.runWebSocketClient();
                } else {
                    sender.sendMessage(new TextComponentString("格式错误"));
                }
                break;
            }
            case 1: {
                Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                Matcher matcher = pattern.matcher(args[0]);
                if(matcher.find()) {
                    BotApi.config.getCommon().setWsHOST(matcher.group(1));
                    BotApi.config.getCommon().setWsPORT(Integer.parseInt(matcher.group(2)));
                    ConfigManger.saveBotConfig(BotApi.config);
                    sender.sendMessage(new TextComponentString("已保存，正在尝试建立连接"));
                    ClientThreadService.runWebSocketClient();
                } else {
                    sender.sendMessage(new TextComponentString("格式错误"));
                }
                break;
            }
            case 0: {
                sender.sendMessage(new TextComponentString("尝试建立连接"));
                ClientThreadService.runWebSocketClient();
                break;
            }
        }
        BotApi.config.getCommon().setENABLED(true);
        ConfigManger.saveBotConfig(BotApi.config);
    }
}
