package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

import static net.minecraft.commands.Commands.literal;

/**
 * @author cnlimiter
 * @date 2021/11/17 13:05
 */
public class DebugCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return literal("debug")
                .then(Commands.argument("enabled", BoolArgumentType.bool())
                    .executes(DebugCommand::execute));
    }
    public static int execute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setDebuggable(isEnabled);
        ConfigManger.saveBotConfig(BotApi.config);
        if (isEnabled) {
            context.getSource().sendSuccess(new TextComponent("已开启开发者模式"), true);
        } else {
            context.getSource().sendSuccess(new TextComponent("已关闭开发者模式"), true);
        }

        return 0;
    }
}
