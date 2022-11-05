package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.Static;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.onebot.sdk.connection.ConnectFactory;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/7 17:21
 * Version: 1.0
 */
public class MiraiCmd extends CommandBase {
    @Override
    public String getName() {
        return "mirai";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/mcbot connect mirai <host:port>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (args[0] != null) {
            String parameter = args[0];
            Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
            Matcher matcher = pattern.matcher(parameter);
            if (matcher.find()) {
                BotApi.config.getBotConfig().setUrl("ws://" + parameter);
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
                sender.sendMessage(new TextComponentString(TextFormatting.RED + "参数错误❌"));
            }
            return;
        }


    }
}
