package cn.evolvefield.mods.botapi.command;


import cn.evolvefield.mods.botapi.config.ModConfig;
import cn.evolvefield.mods.botapi.service.ClientThreadService;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

public class StatusCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("status").executes(StatusCommand::execute);
    }
    public static int execute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        boolean clientEnabled = ModConfig.IS_ENABLED.get();
        boolean receiveEnabled = ModConfig.RECEIVE_ENABLED.get();
        boolean sendEnabled = ModConfig.SEND_ENABLED.get();
        boolean connected = ClientThreadService.client != null;
        String host = ModConfig.wsHOST.get();
        int port = ModConfig.wsPORT.get();
        String key = ModConfig.KEY.get();
        String toSend = "目标服务器:" + host + ":" + port + "\n"
                + "key:" + key + "\n"
                + "全局服务开启:" + clientEnabled + "\n"
                + "接收消息开启:" + receiveEnabled + "\n"
                + "发送消息开启:" + sendEnabled + "\n"
                + "连接状态:" + connected;
        context.getSource().sendSuccess(new TextComponent(toSend), true);
        return 0;
    }
}
