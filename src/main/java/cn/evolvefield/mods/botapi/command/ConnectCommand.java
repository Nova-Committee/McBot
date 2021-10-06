package cn.evolvefield.mods.botapi.command;


import cn.evolvefield.mods.botapi.config.ModConfig;
import cn.evolvefield.mods.botapi.service.ClientThreadService;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ConnectCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("connect")
                .executes(ConnectCommand::execute)
                .then(Commands.argument("arguments", StringArgumentType.greedyString())
                        .executes(ConnectCommand::execute));
    }
    public static int execute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        String[] args = context.getInput().split("\\s+");
        switch(args.length) {
            default: {
                context.getSource().sendSuccess(new TextComponent("参数不合法"), true);
                break;
            }
            case 4: {
                Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                Matcher matcher = pattern.matcher(args[2]);
                if (matcher.find()) {
                    ModConfig.wsHOST.set(matcher.group(1));
                    ModConfig.wsPORT.set(Integer.parseInt(matcher.group(2)));
                    ModConfig.KEY.set(args[3]);
                    context.getSource().sendSuccess(new TextComponent("已保存，正在尝试建立连接"), true);
                    ClientThreadService.runWebSocketClient();
                } else {
                    context.getSource().sendSuccess(new TextComponent("格式错误"), true);
                }
                break;
            }
            case 3: {
                Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                Matcher matcher = pattern.matcher(args[2]);
                if(matcher.find()) {
                    ModConfig.wsHOST.set(matcher.group(1));
                    ModConfig.wsPORT.set(Integer.parseInt(matcher.group(2)));
                    context.getSource().sendSuccess(new TextComponent("已保存，正在尝试建立连接"), true);
                    ClientThreadService.runWebSocketClient();
                } else {
                    context.getSource().sendSuccess(new TextComponent("格式错误"), true);
                }
                break;
            }
            case 2: {
                context.getSource().sendSuccess(new TextComponent("尝试建立连接"), true);
                ClientThreadService.runWebSocketClient();
                break;
            }
        }
        ModConfig.IS_ENABLED.set(true);
        return 0;
    }
}
