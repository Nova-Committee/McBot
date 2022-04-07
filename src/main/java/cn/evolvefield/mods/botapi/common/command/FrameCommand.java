package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.bot.BotData;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class FrameCommand extends CommandBase {


    private final String command;

    public FrameCommand(String command) {
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
        return "/mcbot " + this.command + "<mirai|cqhttp>";
    }


    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {


        BotApi.config.getCommon().setFrame(args[0]);
        BotData.setBotFrame(args[0]);
        ConfigManger.saveBotConfig(BotApi.config);

        sender.sendMessage(new TextComponentString("已设置机器人框架为:" + ChatFormatting.LIGHT_PURPLE + args[0]));


    }
}
