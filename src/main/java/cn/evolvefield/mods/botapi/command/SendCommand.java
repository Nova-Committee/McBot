package cn.evolvefield.mods.botapi.command;
import cn.evolvefield.mods.botapi.config.ModConfig;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;


public class SendCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("send")
                .then(Commands.argument("enabled", BoolArgumentType.bool())
                        .executes(SendCommand::execute));
    }
    public static int execute(CommandContext<CommandSource> context) throws CommandException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ModConfig.SEND_ENABLED.set(isEnabled);
        context.getSource().sendSuccess(
                new StringTextComponent("发送消息开关已被设置为 " + isEnabled), true);
        return 0;
    }
}
