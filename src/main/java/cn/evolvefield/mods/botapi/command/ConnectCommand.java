package cn.evolvefield.mods.botapi.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.config.ConfigManger;
import cn.evolvefield.mods.botapi.service.ClientThreadService;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.minecraft.commands.Commands.literal;

public class ConnectCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return literal("connect")
                .then(literal("send")
                        .then(Commands.argument("<address:port>", StringArgumentType.greedyString())
                                .executes(ConnectCommand::sendExecute)))
                .then(literal("receive")
                        .then(Commands.argument("<address:port>", StringArgumentType.greedyString())
                                .executes(ConnectCommand::receiveExecute)))
                ;
    }
    public static int sendExecute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        String input = context.getArgument("<address:port>", String.class);
        String[] args = context.getInput().split("\\s+");
        switch(args.length) {
            default: {
                context.getSource().sendSuccess(new TextComponent("参数不合法"), true);
                break;
            }
            case 4: {
                Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                Matcher matcher = pattern.matcher(args[3]);
                if(matcher.find()) {
                    BotApi.config.getCommon().setSEND_ENABLED(true);
                    BotApi.config.getCommon().setSendHOST(matcher.group(1));
                    BotApi.config.getCommon().setSendPORT(Integer.parseInt(matcher.group(2)));
                    ConfigManger.saveBotConfig(BotApi.config);
                    context.getSource().sendSuccess(new TextComponent("已保存，正在尝试建立http连接"), true);
                } else {
                    context.getSource().sendSuccess(new TextComponent("格式错误"), true);
                }
                break;
            }
            case 3: {
                context.getSource().sendSuccess(new TextComponent("尝试建立http连接"), true);
                break;
            }
        }
        BotApi.config.getCommon().setENABLED(true);
        ConfigManger.saveBotConfig(BotApi.config);
        return 0;
    }
    public static int receiveExecute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        String[] args = context.getInput().split("\\s+");
        switch(args.length) {
            default: {
                context.getSource().sendSuccess(new TextComponent("参数不合法"), true);
                break;
            }
            case 5: {
                Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                Matcher matcher = pattern.matcher(args[3]);
                if (matcher.find()) {
                    BotApi.config.getCommon().setRECEIVE_ENABLED(true);
                    BotApi.config.getCommon().setWsHOST(matcher.group(1));
                    BotApi.config.getCommon().setWsPORT(Integer.parseInt(matcher.group(2)));
                    BotApi.config.getCommon().setKEY(args[4]);
                    ConfigManger.saveBotConfig(BotApi.config);
                    context.getSource().sendSuccess(new TextComponent("已保存，正在尝试建立WebSocket连接"), true);
                    ClientThreadService.runWebSocketClient();
                } else {
                    context.getSource().sendSuccess(new TextComponent("格式错误"), true);
                }
                break;
            }
            case 4: {
                Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                Matcher matcher = pattern.matcher(args[3]);
                if(matcher.find()) {
                    BotApi.config.getCommon().setRECEIVE_ENABLED(true);
                    BotApi.config.getCommon().setWsHOST(matcher.group(1));
                    BotApi.config.getCommon().setWsPORT(Integer.parseInt(matcher.group(2)));
                    ConfigManger.saveBotConfig(BotApi.config);
                    context.getSource().sendSuccess(new TextComponent("已保存，正在尝试建立WebSocket连接"), true);
                    ClientThreadService.runWebSocketClient();
                } else {
                    context.getSource().sendSuccess(new TextComponent("格式错误"), true);
                }
                break;
            }
            case 3: {
                context.getSource().sendSuccess(new TextComponent("尝试建立WebSocket连接"), true);
                ClientThreadService.runWebSocketClient();
                break;
            }
        }
        BotApi.config.getCommon().setENABLED(true);
        ConfigManger.saveBotConfig(BotApi.config);
        return 0;
    }
}
