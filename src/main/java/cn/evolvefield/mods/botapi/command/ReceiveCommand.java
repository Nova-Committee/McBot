package cn.evolvefield.mods.botapi.command;

import cn.evolvefield.mods.botapi.config.ModConfig;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class ReceiveCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("receive")
                .then(Commands.argument("enabled", BoolArgumentType.bool())
                        .executes(ReceiveCommand::execute));
    }
    public static int execute(CommandContext<CommandSource> context) throws CommandException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ModConfig.RECEIVE_ENABLED.set(isEnabled);
        context.getSource().sendSuccess(
                new StringTextComponent("接收消息开关已被设置为 " + isEnabled), true);
        return 0;
    }
}
