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
        boolean clientEnabled = BotApi.config.getCommon().isENABLED();
        boolean receiveEnabled = BotApi.config.getCommon().isRECEIVE_ENABLED();
        boolean sendEnabled = BotApi.config.getCommon().isSEND_ENABLED();
        boolean connected = ClientThreadService.client != null;
        String host = BotApi.config.getCommon().getWsHOST();
        int port = BotApi.config.getCommon().getWsPORT();
        String key = BotApi.config.getCommon().getKEY();
        String toSend = "GO_CQHTTP服务器:" + host + ":" + port + "\n"
                + "key:" + key + "\n"
                + "全局服务开启:" + clientEnabled + "\n"
                + "接收消息开启:" + receiveEnabled + "\n"
                + "发送消息开启:" + sendEnabled + "\n"
                + "连接状态:" + connected;
        sender.sendMessage(new TextComponentString(toSend));
    }


}
