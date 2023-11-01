package cn.evole.mods.mcbot.command;


import cn.evole.mods.mcbot.IMcBot;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.val;
import net.minecraft.commands.CommandSourceStack;
//#if MC >= 11900
import net.minecraft.network.chat.Component;
//#else
//$$ import net.minecraft.network.chat.TextComponent;
//#endif
public class StatusCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean clientEnabled = IMcBot.config.getCommon().isEnable();

        boolean receiveEnabled = IMcBot.config.getStatus().isREnable();
        boolean rChatEnabled = IMcBot.config.getStatus().isRChatEnable();
        boolean rCmdEnabled = IMcBot.config.getStatus().isRCmdEnable();

        boolean sendEnabled = IMcBot.config.getStatus().isSEnable();
        boolean sJoinEnabled = IMcBot.config.getStatus().isSJoinEnable();
        boolean sLeaveEnabled = IMcBot.config.getStatus().isSLeaveEnable();
        boolean sDeathEnabled = IMcBot.config.getStatus().isSDeathEnable();
        boolean sAchievementsEnabled = IMcBot.config.getStatus().isSAdvanceEnable();
        boolean sQqWelcomeEnabled = IMcBot.config.getStatus().isSQqWelcomeEnable();
        boolean sQqLeaveEnabled = IMcBot.config.getStatus().isSQqLeaveEnable();

        val groupId = IMcBot.config.getCommon().getGroupIdList().toString();
        boolean debuggable = IMcBot.config.getCommon().isDebug();
        boolean connected = IMcBot.service != null;
        boolean white = IMcBot.SERVER.getPlayerList().isUsingWhitelist();
        String host = IMcBot.config.getBotConfig().getUrl();
        long QQid = IMcBot.config.getCommon().getBotId();
        String toSend =
                "\n姬妻人服务状态:\n"
                        + "姬妻人QQId:" + QQid + " \n"
                        + "框架服务器:" + host + " \n"
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
                        + "发送群成员进群消息状态:" + sQqWelcomeEnabled + "\n"
                        + "发送群成员退群消息状态:" + sQqLeaveEnabled + "\n";
        //#if MC >= 12000
        context.getSource().sendSuccess(()->Component.literal(toSend), true);
        //#elseif MC < 11900
        //$$ context.getSource().sendSuccess(new TextComponent(toSend), true);
        //#else
        //$$ context.getSource().sendSuccess(Component.literal(toSend), true);
        //#endif
        IMcBot.config.save();
        return 1;
    }
}
