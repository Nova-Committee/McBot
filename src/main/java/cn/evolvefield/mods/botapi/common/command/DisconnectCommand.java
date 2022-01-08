package cn.evolvefield.mods.botapi.common.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.service.ClientThreadService;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;


public class DisconnectCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("disconnect").executes(DisconnectCommand::execute);
    }
    public static int execute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        boolean isSuccess = ClientThreadService.stopWebSocketClient();
        if (isSuccess) {
            context.getSource().sendSuccess(new TextComponent("WebSocket已断开连接"), true);
        } else {
            context.getSource().sendSuccess(new TextComponent("WebSocket目前未连接"), true);
        }
        BotApi.config.getCommon().setEnable(false);
        ConfigManger.saveBotConfig(BotApi.config);
        return 0;
    }
}
