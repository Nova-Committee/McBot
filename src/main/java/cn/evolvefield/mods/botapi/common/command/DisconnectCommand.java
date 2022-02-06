package cn.evolvefield.mods.botapi.common.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.CommandBase;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.service.ClientThreadService;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class DisconnectCommand extends CommandBase {



    public DisconnectCommand(){
       super("disconnect");
    }

    @Override
    public int getPermissionLevel() {
        return 2;
    }

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        boolean isSuccess = ClientThreadService.stopWebSocketClient();
        if (isSuccess) {
            sender.addChatMessage(new ChatComponentText("WebSocket已断开连接"));
        } else {
            sender.addChatMessage(new ChatComponentText("WebSocket目前未连接"));
        }
        BotApi.config.getCommon().setEnable(false);
        ConfigManger.saveBotConfig(BotApi.config);
    }


}
