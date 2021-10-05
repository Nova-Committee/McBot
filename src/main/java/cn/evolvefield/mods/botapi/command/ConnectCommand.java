package cn.evolvefield.mods.botapi.command;


import cn.evolvefield.mods.botapi.config.ModConfig;
import cn.evolvefield.mods.botapi.service.ClientThreadService;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ConnectCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("connect")
                .executes(ConnectCommand::execute)
                .then(Commands.argument("arguments", StringArgumentType.greedyString())
                        .executes(ConnectCommand::execute));
    }
    public static int execute(CommandContext<CommandSource> context) throws CommandException {
        String[] args = context.getInput().split("\\s+");
        switch(args.length) {
            default: {
                context.getSource().sendSuccess(new StringTextComponent("参数不合法"), true);
                break;
            }
            case 4: {
                Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                Matcher matcher = pattern.matcher(args[2]);
                if (matcher.find()) {
                    ModConfig.wsHOST.set(matcher.group(1));
                    ModConfig.wsPORT.set(Integer.parseInt(matcher.group(2)));
                    ModConfig.KEY.set(args[3]);
                    context.getSource().sendSuccess(new StringTextComponent("已保存，正在尝试建立连接"), true);
                    ClientThreadService.runWebSocketClient();
                } else {
                    context.getSource().sendSuccess(new StringTextComponent("格式错误"), true);
                }
                break;
            }
            case 3: {
                Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                Matcher matcher = pattern.matcher(args[2]);
                if(matcher.find()) {
                    ModConfig.wsHOST.set(matcher.group(1));
                    ModConfig.wsPORT.set(Integer.parseInt(matcher.group(2)));
                    context.getSource().sendSuccess(new StringTextComponent("已保存，正在尝试建立连接"), true);
                    ClientThreadService.runWebSocketClient();
                } else {
                    context.getSource().sendSuccess(new StringTextComponent("格式错误"), true);
                }
                break;
            }
            case 2: {
                context.getSource().sendSuccess(new StringTextComponent("尝试建立连接"), true);
                ClientThreadService.runWebSocketClient();
                break;
            }
        }
        ModConfig.IS_ENABLED.set(true);
        return 0;
    }
}
