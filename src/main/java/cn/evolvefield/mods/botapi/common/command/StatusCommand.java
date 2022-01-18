package cn.evolvefield.mods.botapi.common.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.core.service.ClientThreadService;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

public class StatusCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean clientEnabled = BotApi.config.getCommon().isEnable();

        boolean receiveEnabled = BotApi.config.getCommon().isRECEIVE_ENABLED();
        boolean rChatEnabled = BotApi.config.getCommon().isR_CHAT_ENABLE();
        boolean rCmdEnabled = BotApi.config.getCommon().isR_COMMAND_ENABLED();

        boolean sendEnabled = BotApi.config.getCommon().isSEND_ENABLED();
        boolean sJoinEnabled = BotApi.config.getCommon().isS_JOIN_ENABLE();
        boolean sLeaveEnabled = BotApi.config.getCommon().isS_LEAVE_ENABLE();
        boolean sDeathEnabled = BotApi.config.getCommon().isS_DEATH_ENABLE();
        boolean sAchievementsEnabled = BotApi.config.getCommon().isS_ADVANCE_ENABLE();

        boolean debuggable = BotApi.config.getCommon().isDebuggable();
        boolean connected = ClientThreadService.client != null;
        String host = BotApi.config.getCommon().getWsHost();
        int port = BotApi.config.getCommon().getWsPort();
        String key = BotApi.config.getCommon().getWsKey();
        String toSend = "姬妻人服务状态:\n" +
                "GO_CQHTTP服务器:" + host + ":" + port + "\n"
                + "全局服务状态:" + clientEnabled + "\n"
                + "全局接收消息状态:" + receiveEnabled + "\n"
                + "接收QQ群聊天消息状态:" + rChatEnabled + "\n"
                + "接收QQ群命令消息状态:" + rCmdEnabled + "\n"
                + "全局发送消息状态:" + sendEnabled + "\n"
                + "发送玩家加入消息状态:" + sJoinEnabled + "\n"
                + "发送玩家离开消息状态:" + sLeaveEnabled + "\n"
                + "发送玩家死亡消息状态:" + sDeathEnabled + "\n"
                + "发送玩家成就消息状态:" + sAchievementsEnabled + "\n"
                + "开发者模式状态:" + debuggable + "\n"
                + "WebSocket Key:" + key + "\n"
                + "WebSocket连接状态:" + connected;
        context.getSource().sendSuccess(new TextComponent(toSend), true);
        return 0;
    }
}
