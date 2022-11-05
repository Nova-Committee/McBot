package cn.evolvefield.mods.botapi.common.cmds;


import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;

public class HelpCommand extends CommandBase {
    private final String command;

    public HelpCommand(String command) {
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
        return "/mcbot " + this.command;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String toSend =
                "\n群服互联使用说明:\n"
                        + "如果你是第一次使用请按照以下步骤设置\n"
                        + "1.请先开启机器人框架，go-cqhttp或者mirai\n"
                        + "2.请使用/mcbot setFrame <cqhttp/mirai> 设置框架\n"
                        + "3.请使用/mcbot setGroup <GroupId> 设置互通的群\n"
                        + "4.请使用/mcbot setBot <BotId> 设置机器人的qq号\n"
                        + "5.如果使用的是mirai，同时打开了VerifyKey验证，请输入/mcbot setVerifyKey <VerifyKey> 设置\n"
                        + "6.准备工作完成，请使用/mcbot connect <cqhttp/mirai> <host:port> 与框架对接\n"
                        + "在框架默认配置下，请使用/mcbot connect <cqhttp/mirai>\n"
                        + "*************************************\n"
                        + "全部命令：\n"
                        + "/mcbot connect <cqhttp/mirai> <host:port>\n"
                        + "/mcbot setFrame <cqhttp/mirai>\n"
                        + "/mcbot setGroup <GroupId>\n"
                        + "/mcbot setBot <BotId>\n"
                        + "/mcbot setVerifyKey <VerifyKey>\n"
                        + "/mcbot receive <all|chat|cmd> <true|false>\n"
                        + "/mcbot send <all|join|leave|death|achievements|welcome> <true|false>\n"
                        + "/mcbot help\n"
                        + "*************************************\n"
                        + "感谢您的支持，如有问题请联系我\n"
                        + "QQ群：720975019找群主\n"
                        + "Github：\n";

        String url = "https://github.com/Nova-Committee/Bot-Connect/issues/new";
        String end = "提交问题";
        TextComponentString urlC = new TextComponentString(url);
        urlC.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Nova-Committee/Bot-Connect/issues/new"));
        TextComponentString endC = new TextComponentString(end);
        sender.sendMessage(new TextComponentString(toSend).appendSibling(urlC).appendSibling(endC));
    }


}
