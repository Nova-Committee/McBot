package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.CommandBase;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class GroupIDCommand extends CommandBase {



    public GroupIDCommand(){
        super("setID");
    }


    @Override
    public int getPermissionLevel() {
        return 2;
    }

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        long id ;
        id = Long.parseLong(args[0]);

        BotApi.config.getCommon().setGroupId(id);
        ConfigManger.saveBotConfig(BotApi.config);

        sender.addChatMessage(new ChatComponentText("已设置互通的群号为:"+ id));

    }

}
