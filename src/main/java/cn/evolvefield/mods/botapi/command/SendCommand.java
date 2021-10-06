package cn.evolvefield.mods.botapi.command;

import cn.evolvefield.mods.botapi.config.ModConfig;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;


public class SendCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("send")
                .then(Commands.argument("enabled", BoolArgumentType.bool())
                        .executes(SendCommand::execute));
    }
    public static int execute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ModConfig.SEND_ENABLED.set(isEnabled);
        context.getSource().sendSuccess(
                new TextComponent("发送消息开关已被设置为 " + isEnabled), true);
        return 0;
    }
}
