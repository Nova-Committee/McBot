package cn.evolvefield.mods.botapi.common.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.bot.BotData;
import cn.evolvefield.mods.botapi.core.service.WebSocketService;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.server.command.CommandTreeBase;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class ConnectCommand extends CommandTreeBase {


    public ConnectCommand() {
        this.addSubcommand(new MiraiCmd());
        this.addSubcommand(new CqhttpCmd());
    }


    @Override
    public String getName() {
        return "connect";
    }


    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/mcbot connect <mirai|cqhttp> <host>:<port>";
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "mirai", "cqhttp");
        }

        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args[0].equalsIgnoreCase("cqhttp")) {
            sender.sendMessage(new TextComponentString("尝试链接框架" + TextFormatting.LIGHT_PURPLE + "cqhttp"));
            WebSocketService.main(BotApi.config.getCommon().getWsCommon());
            BotApi.config.getStatus().setRECEIVE_ENABLED(true);
            BotApi.config.getCommon().setEnable(true);
            ConfigManger.saveBotConfig(BotApi.config);

        } else if (args[0].equalsIgnoreCase("mirai")) {
            sender.sendMessage(new TextComponentString("尝试链接框架" + TextFormatting.LIGHT_PURPLE + "mirai"));
            WebSocketService.main(BotApi.config.getMirai().getWsMirai() + "/all?verifyKey=" + BotData.getVerifyKey() + "&qq=" + BotData.getQQId());
            BotApi.config.getStatus().setRECEIVE_ENABLED(true);
            BotApi.config.getCommon().setEnable(true);
            ConfigManger.saveBotConfig(BotApi.config);

        } else {
            sender.sendMessage(new TextComponentString("链接框架" + TextFormatting.RED + "失败"));
        }
    }
}
