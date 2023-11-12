package cn.evole.mods.mcbot.command;


import cn.evole.mods.mcbot.McBot;
import cn.evole.mods.mcbot.init.config.ModConfig;
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
        boolean clientEnabled =  ModConfig.INSTANCE.getCommon().isEnable();

        boolean receiveEnabled =  ModConfig.INSTANCE.getStatus().isREnable();
        boolean rChatEnabled =  ModConfig.INSTANCE.getStatus().isRChatEnable();
        boolean rCmdEnabled =  ModConfig.INSTANCE.getStatus().isRCmdEnable();

        boolean sendEnabled =  ModConfig.INSTANCE.getStatus().isSEnable();
        boolean sJoinEnabled =  ModConfig.INSTANCE.getStatus().isSJoinEnable();
        boolean sLeaveEnabled =  ModConfig.INSTANCE.getStatus().isSLeaveEnable();
        boolean sDeathEnabled =  ModConfig.INSTANCE.getStatus().isSDeathEnable();
        boolean sAchievementsEnabled =  ModConfig.INSTANCE.getStatus().isSAdvanceEnable();
        boolean sQqWelcomeEnabled =  ModConfig.INSTANCE.getStatus().isSQqWelcomeEnable();
        boolean sQqLeaveEnabled =  ModConfig.INSTANCE.getStatus().isSQqLeaveEnable();

        val groupId =  ModConfig.INSTANCE.getCommon().getGroupIdList().toString();
        val guildId =  ModConfig.INSTANCE.getCommon().getGuildId();
        val channelId =  ModConfig.INSTANCE.getCommon().getChannelIdList().toString();
        boolean debuggable =  ModConfig.INSTANCE.getCommon().isDebug();
        boolean connected = McBot.service != null;
        boolean white = McBot.SERVER.getPlayerList().isUsingWhitelist();
        String host =  ModConfig.INSTANCE.getBotConfig().getUrl();
        long QQid =  ModConfig.INSTANCE.getCommon().getBotId();
        String toSend =
                "\n姬妻人服务状态:\n"
                        + "姬妻人QQId:" + QQid + " \n"
                        + "框架服务器:" + host + " \n"
                        + "WebSocket连接状态:" + connected + "\n"
                        + "互通的群号:" + groupId + "\n"
                        + "互通的频道号:" + guildId + "\n"
                        + "互通的子频道号:" + channelId + "\n"
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
        ModConfig.INSTANCE.save();
        return 1;
    }
}
