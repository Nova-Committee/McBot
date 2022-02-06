package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.CommandBase;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import static net.minecraft.command.CommandBase.parseBoolean;

/**
 * @author cnlimiter
 * @date 2021/11/17 13:10
 */
public class DebugCommand extends CommandBase {

    public DebugCommand(){
        super("debug");
    }

    @Override
    public int getPermissionLevel() {
        return 2;
    }

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        boolean isEnabled ;
        isEnabled = parseBoolean(sender, args[0]);
        BotApi.config.getCommon().setDebuggable(isEnabled);
        ConfigManger.saveBotConfig(BotApi.config);
        if (isEnabled) {
            sender.addChatMessage(new ChatComponentText("已开启开发者模式"));
        } else {
            sender.addChatMessage(new ChatComponentText("已关闭开发者模式"));
        }
    }

}
