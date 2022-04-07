package cn.evolvefield.mods.botapi.common.command;


import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

public class HelpCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        String toSend =
                """

                        群服互联使用说明:
                        如果你是第一次使用请按照以下步骤设置
                        1.请先开启机器人框架，go-cqhttp或者mirai
                        2.请使用/mcbot setFrame <cqhttp/mirai> 设置框架
                        3.请使用/mcbot setGroup <GroupId> 设置互通的群
                        4.请使用/mcbot setBot <BotId> 设置机器人的qq号
                        5.如果使用的是mirai，同时打开了VerifyKey验证，请输入/mcbot setVerifyKey <VerifyKey> 设置
                        6.准备工作完成，请使用/mcbot connect <cqhttp/mirai> <host:port> 与框架对接
                        在框架默认配置下，请使用/mcbot connect <cqhttp/mirai>
                        *************************************
                        全部命令：
                        /mcbot connect <cqhttp/mirai> <host:port>
                        /mcbot setFrame <cqhttp/mirai>
                        /mcbot setGroup <GroupId>
                        /mcbot setBot <BotId>
                        /mcbot setVerifyKey <VerifyKey>
                        /mcbot receive <all|chat|cmd> <true|false>
                        /mcbot send <all|join|leave|death|achievements|welcome> <true|false>
                        /mcbot status
                        /mcbot help
                        *************************************
                        感谢您的支持，如有问题请联系我
                        QQ群：720975019找群主
                        Github：https://github.com/Nova-Committee/Bot-Connect/issues/new 提交问题
                        """;
        context.getSource().sendSuccess(new TextComponent(toSend), true);
        return 1;
    }
}
