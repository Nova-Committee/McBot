package cn.evolvefield.mods.botapi.common.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.core.bot.BotData;
import cn.evolvefield.mods.botapi.core.service.WebSocketService;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

public class StatusCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean clientEnabled = BotApi.config.getCommon().isEnable();

        boolean receiveEnabled = BotApi.config.getStatus().isRECEIVE_ENABLED();
        boolean rChatEnabled = BotApi.config.getStatus().isR_CHAT_ENABLE();
        boolean rCmdEnabled = BotApi.config.getStatus().isR_COMMAND_ENABLED();

        boolean sendEnabled = BotApi.config.getStatus().isSEND_ENABLED();
        boolean sJoinEnabled = BotApi.config.getStatus().isS_JOIN_ENABLE();
        boolean sLeaveEnabled = BotApi.config.getStatus().isS_LEAVE_ENABLE();
        boolean sDeathEnabled = BotApi.config.getStatus().isS_DEATH_ENABLE();
        boolean sAchievementsEnabled = BotApi.config.getStatus().isS_ADVANCE_ENABLE();
        boolean sWelcomeEnabled = BotApi.config.getStatus().isS_WELCOME_ENABLE();

        long groupId = BotApi.config.getCommon().getGroupId();
        boolean debuggable = BotApi.config.getCommon().isDebuggable();
        boolean connected = WebSocketService.client != null;
        boolean white = BotApi.SERVER.getPlayerList().isUsingWhitelist();
        String host = BotData.getWs();
        long QQid = BotApi.config.getCommon().getBotId();
        String key = BotApi.config.getCommon().getWsKey();
        String toSend =
                "\n姬妻人服务状态:\n"
                        + "姬妻人QQId:" + QQid + " \n"
                        + "框架服务器:" + host + " \n"
                        + "WebSocket Key:" + key + "\n"
                        + "WebSocket连接状态:" + connected + "\n"
                        + "互通的群号:" + groupId + "\n"
                        + "全局服务状态:" + clientEnabled + "\n"
                        + "开发者模式状态:" + debuggable + "\n"
                        + "白名单是否开启:" + white + "\n"
                        + "*************************************\n"
                        + "全局接收消息状态:" + receiveEnabled + "\n"
                        + "接收QQ群聊天消息状态:" + rChatEnabled + "\n"
                        + "接收QQ群命令消息状态:" + rCmdEnabled + "\n"
                        + "*************************************\n"
                        + "全局发送消息状态:" + sendEnabled + "\n"
                        + "发送玩家加入消息状态:" + sJoinEnabled + "\n"
                        + "发送玩家离开消息状态:" + sLeaveEnabled + "\n"
                        + "发送玩家死亡消息状态:" + sDeathEnabled + "\n"
                        + "发送玩家成就消息状态:" + sAchievementsEnabled + "\n"
                        + "发送群成员进/退群消息状态:" + sWelcomeEnabled + "\n";
        context.getSource().sendSuccess(new TextComponent(toSend), true);
        return 1;
    }
}
