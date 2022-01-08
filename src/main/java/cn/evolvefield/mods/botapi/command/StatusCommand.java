package cn.evolvefield.mods.botapi.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.service.ClientThreadService;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class StatusCommand  extends CommandBase {
    private final String command;

    public StatusCommand(String command){
        this.command = command;
    }


    @Override
    public String getName() {
        return this.command;
    }


    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/mcbot " + this.command ;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
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
        sender.sendMessage(new TextComponentString(toSend));
    }


}
