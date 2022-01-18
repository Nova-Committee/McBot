package cn.evolvefield.mods.botapi.common.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.service.ClientThreadService;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectCommand {


    public static int receiveExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        String[] args = context.getInput().split("\\s+");
        switch (args.length) {
            default: {
                context.getSource().sendSuccess(new TextComponent("参数不合法"), true);
                break;
            }
            case 4: {
                Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                Matcher matcher = pattern.matcher(args[3]);
                if (matcher.find()) {
                    BotApi.config.getCommon().setRECEIVE_ENABLED(true);
                    BotApi.config.getCommon().setWsHost(matcher.group(1));
                    BotApi.config.getCommon().setWsPort(Integer.parseInt(matcher.group(2)));
                    BotApi.config.getCommon().setWsKey(args[3]);
                    ConfigManger.saveBotConfig(BotApi.config);
                    context.getSource().sendSuccess(new TextComponent("已保存，正在尝试建立WebSocket连接"), true);
                    ClientThreadService.runWebSocketClient();
                } else {
                    context.getSource().sendSuccess(new TextComponent("格式错误"), true);
                }
                break;
            }
            case 3: {
                Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                Matcher matcher = pattern.matcher(args[2]);
                if (matcher.find()) {
                    BotApi.config.getCommon().setRECEIVE_ENABLED(true);
                    BotApi.config.getCommon().setWsHost(matcher.group(1));
                    BotApi.config.getCommon().setWsPort(Integer.parseInt(matcher.group(2)));
                    ConfigManger.saveBotConfig(BotApi.config);
                    context.getSource().sendSuccess(new TextComponent("已保存，正在尝试建立WebSocket连接"), true);
                    ClientThreadService.runWebSocketClient();
                } else {
                    context.getSource().sendSuccess(new TextComponent("格式错误"), true);
                }
                break;
            }
            case 2: {
                context.getSource().sendSuccess(new TextComponent("尝试建立WebSocket连接"), true);
                ClientThreadService.runWebSocketClient();
                break;
            }
        }
        BotApi.config.getCommon().setEnable(true);
        ConfigManger.saveBotConfig(BotApi.config);
        return 0;
    }
}
