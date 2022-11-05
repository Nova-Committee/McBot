package cn.evolvefield.mods.botapi.common.cmds;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.Static;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.onebot.sdk.connection.ConnectFactory;
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
            BotApi.config.getBotConfig().setMiraiHttp(false);
            try {
                BotApi.service = ConnectFactory.createWebsocketClient(BotApi.config.getBotConfig(), BotApi.blockingQueue);
                BotApi.service.create();//创建websocket连接
                BotApi.bot = BotApi.service.createBot();//创建机器人实例
            } catch (Exception e) {
                Static.LOGGER.error(e.getMessage());
            }
            BotApi.config.getStatus().setRECEIVE_ENABLED(true);
            BotApi.config.getCommon().setEnable(true);
            ConfigHandler.onChange();


        } else if (args[0].equalsIgnoreCase("mirai")) {
            sender.sendMessage(new TextComponentString("尝试链接框架" + TextFormatting.LIGHT_PURPLE + "mirai"));
            BotApi.config.getBotConfig().setMiraiHttp(true);
            try {
                BotApi.service = ConnectFactory.createWebsocketClient(BotApi.config.getBotConfig(), BotApi.blockingQueue);
                BotApi.service.create();//创建websocket连接
                BotApi.bot = BotApi.service.createBot();//创建机器人实例
            } catch (Exception e) {
                Static.LOGGER.error(e.getMessage());
            }
            BotApi.config.getStatus().setRECEIVE_ENABLED(true);
            BotApi.config.getCommon().setEnable(true);
            ConfigHandler.onChange();
        } else {
            sender.sendMessage(new TextComponentString("链接框架" + TextFormatting.RED + "失败"));
        }
    }
}
