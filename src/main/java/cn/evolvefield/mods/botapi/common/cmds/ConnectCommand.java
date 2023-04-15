package cn.evolvefield.mods.botapi.common.cmds;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.Const;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.onebot.client.connection.ConnectFactory;
import com.mojang.realmsclient.gui.ChatFormatting;
import lombok.val;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.regex.Pattern;

public class ConnectCommand {

    public static void cqhttpExecute(ICommandSender sender, String[] args) throws CommandException {
        val parameter = args[2];


        val pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
        val matcher = pattern.matcher(parameter);
        if (matcher.find()) {
            ConfigHandler.cached().getBotConfig().setUrl("ws://" + parameter);
            sender.addChatMessage(new ChatComponentText("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "cqhttp"));
            ConfigHandler.cached().getBotConfig().setMiraiHttp(false);

            try {
                BotApi.app.submit(() -> {
                    BotApi.service = new ConnectFactory(ConfigHandler.cached().getBotConfig(), BotApi.blockingQueue);//创建websocket连接
                    BotApi.bot = BotApi.service.ws.createBot();//创建机器人实例
                });
            } catch (Exception e) {
                Const.LOGGER.error("§c机器人服务端配置不正确");
            }
            ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
            ConfigHandler.cached().getCommon().setEnable(true);



        } else {
            sender.addChatMessage(new ChatComponentText(ChatFormatting.RED + "参数错误❌"));
        }
    }

    public static void miraiExecute(ICommandSender sender, String[] args) throws CommandException {
        val parameter = args[2];

        val pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
        val matcher = pattern.matcher(parameter);
        if (matcher.find()) {
            ConfigHandler.cached().getBotConfig().setUrl("ws://" + parameter);
            sender.addChatMessage(new ChatComponentText("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "mirai"));
            ConfigHandler.cached().getBotConfig().setMiraiHttp(true);
            try {
                BotApi.app.submit(() -> {
                    BotApi.service = new ConnectFactory(ConfigHandler.cached().getBotConfig(), BotApi.blockingQueue);//创建websocket连接
                    BotApi.bot = BotApi.service.ws.createBot();//创建机器人实例
                });
            } catch (Exception e) {
                Const.LOGGER.error("§c机器人服务端配置不正确");
            }
            ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
            ConfigHandler.cached().getCommon().setEnable(true);



        } else {
            sender.addChatMessage(new ChatComponentText(ChatFormatting.RED + "参数错误"));
        }
    }

    public static void cqhttpCommonExecute(ICommandSender sender, String[] args) throws CommandException {

        sender.addChatMessage(new ChatComponentText("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "cqhttp"));
        ConfigHandler.cached().getBotConfig().setMiraiHttp(false);
        try {
            BotApi.app.submit(() -> {
                BotApi.service = new ConnectFactory(ConfigHandler.cached().getBotConfig(), BotApi.blockingQueue);//创建websocket连接
                BotApi.bot = BotApi.service.ws.createBot();//创建机器人实例
            });
        } catch (Exception e) {
            Const.LOGGER.error("§c机器人服务端配置不正确");
        }
        ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
        ConfigHandler.cached().getCommon().setEnable(true);
        ConfigHandler.save();


    }

    public static void miraiCommonExecute(ICommandSender sender, String[] args) throws CommandException {


        sender.addChatMessage(new ChatComponentText("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "mirai"));
        ConfigHandler.cached().getBotConfig().setMiraiHttp(true);
        try {
            BotApi.app.submit(() -> {
                BotApi.service = new ConnectFactory(ConfigHandler.cached().getBotConfig(), BotApi.blockingQueue);//创建websocket连接
                BotApi.bot = BotApi.service.ws.createBot();//创建机器人实例
            });
        } catch (Exception e) {
            Const.LOGGER.error("§c机器人服务端配置不正确");
        }
        ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
        ConfigHandler.cached().getCommon().setEnable(true);

        ConfigHandler.save();


    }

}
