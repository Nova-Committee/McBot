package cn.evolvefield.mods.botapi.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.config.ConfigManger;
import cn.evolvefield.mods.botapi.service.ClientThreadService;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
        return "/mcbot " + this.command + "send/receive" +"<host>:<port> <token(可选)>";
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, "send", "receive");
        }

        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        switch (args[0]){
            default:{
                sender.sendMessage(new TextComponentString("参数不合法"));
                break;
            }
            case "send":
            {
                switch(args.length) {
                    default: {
                        sender.sendMessage(new TextComponentString("参数不合法"));
                        break;
                    }
                    case 2: {
                        Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                        Matcher matcher = pattern.matcher(args[1]);
                        if(matcher.find()) {
                            BotApi.config.getCommon().setSEND_ENABLED(true);
                            BotApi.config.getCommon().setSendHOST(matcher.group(1));
                            BotApi.config.getCommon().setSendPORT(Integer.parseInt(matcher.group(2)));
                            ConfigManger.saveBotConfig(BotApi.config);
                            sender.sendMessage(new TextComponentString("已保存，正在尝试建立http连接"));
                            //ClientThreadService.runWebSocketClient();
                        } else {
                            sender.sendMessage(new TextComponentString("格式错误"));
                        }
                        break;
                    }
                    case 1: {
                        sender.sendMessage(new TextComponentString("尝试建立http连接"));
                        //ClientThreadService.runWebSocketClient();
                        break;
                    }
                }
                break;
            }
            case "receive":
            {
                switch(args.length) {
                    default: {
                        sender.sendMessage(new TextComponentString("参数不合法"));
                        break;
                    }
                    case 3: {
                        Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                        Matcher matcher = pattern.matcher(args[1]);
                        if (matcher.find()) {
                            BotApi.config.getCommon().setRECEIVE_ENABLED(true);
                            BotApi.config.getCommon().setWsHOST(matcher.group(1));
                            BotApi.config.getCommon().setWsPORT(Integer.parseInt(matcher.group(2)));
                            BotApi.config.getCommon().setKEY(args[2]);
                            ConfigManger.saveBotConfig(BotApi.config);
                            sender.sendMessage(new TextComponentString("已保存，正在尝试建立webSocket连接"));
                            ClientThreadService.runWebSocketClient();
                        } else {
                            sender.sendMessage(new TextComponentString("格式错误"));
                        }
                        break;
                    }
                    case 2: {
                        Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                        Matcher matcher = pattern.matcher(args[1]);
                        if(matcher.find()) {
                            BotApi.config.getCommon().setRECEIVE_ENABLED(true);
                            BotApi.config.getCommon().setWsHOST(matcher.group(1));
                            BotApi.config.getCommon().setWsPORT(Integer.parseInt(matcher.group(2)));
                            ConfigManger.saveBotConfig(BotApi.config);
                            sender.sendMessage(new TextComponentString("已保存，正在尝试建立webSocket连接"));
                            ClientThreadService.runWebSocketClient();
                        } else {
                            sender.sendMessage(new TextComponentString("格式错误"));
                        }
                        break;
                    }
                    case 1: {
                        sender.sendMessage(new TextComponentString("尝试建立连接"));
                        ClientThreadService.runWebSocketClient();
                        break;
                    }
                }
                break;
            }
        }
        BotApi.config.getCommon().setENABLED(true);
        ConfigManger.saveBotConfig(BotApi.config);
    }
}
