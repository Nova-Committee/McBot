package cn.evole.mods.mcbot.command;


import cn.evole.mods.multi.api.mapping.MappingHelper;
import cn.evole.mods.multi.common.ComponentWrapper;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.val;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelpCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        String toSend =
                "\n群服互联使用说明:\n"
                        + "如果你是第一次使用请按照以下步骤设置\n"
                        + "1.请先开启机器人框架，go-cqhttp或者mirai\n"
                        + "2.请使用/mcbot addGroup <GroupId> 添加互通的群\n"
                        + "3.请使用/mcbot setBot <BotId> 设置机器人的qq号\n"
                        + "4.如果使用的是mirai，同时打开了VerifyKey验证，请输入/mcbot setVerifyKey <VerifyKey> 设置\n"
                        + "5.准备工作完成，请使用/mcbot connect <cqhttp/mirai> <host:port> 与框架对接\n"
                        + "*************************************\n"
                        + "在框架默认配置下，请使用/mcbot connect <cqhttp/mirai>\n"
                        + "如果使用的cqhttp，配置完cqhttp后，本模组会自动连接\n"
                        + "*************************************\n"
                        + "全部命令：\n"
                        + "/mcbot connect <cqhttp/mirai> <host:port>\n"
                        + "/mcbot setFrame <cqhttp/mirai>  默认cqhttp\n"
                        + "/mcbot setGroup <GroupId>       添加群\n"
                        + "/mcbot removeGroup <GroupId>    删除群\n"
                        + "/mcbot setBot <BotId>           设置机器人id\n"
                        + "/mcbot setVerifyKey <VerifyKey> mirai可能用\n"
                        + "/mcbot receive <all|chat|cmd> <true|false>\n"
                        + "/mcbot send <all|join|leave|death|achievements|welcome> <true|false>\n"
                        + "/mcbot status   机器人状态\n"
                        + "/mcbot help     帮助\n"
                        + "/mcbot reload   重载配置\n"
                        + "/mcbot customs  列出所有自定义命令\n"
                        + "*************************************\n"
                        + "感谢您的支持，如有问题请联系我\n"
                        + "QQ群：720975019找群主\n"
                        + "Github：\n";

        val url = "https://github.com/Nova-Committee/Bot-Connect/issues/new";
        val end = "提交问题";
        Class<?> BaseText_class = MappingHelper.mapAndLoadClass("net.minecraft.class_2554", MappingHelper.CLASS_MAPPER_FUNCTION);
        Method BaseText_setStyle = MappingHelper.mapAndGetMethod(BaseText_class, "setStyle", Component.class, Style.class);
        Method BaseText_append = MappingHelper.mapAndGetMethod(BaseText_class, "append", Component.class, Component.class);
        val send = ComponentWrapper.literal(toSend);
        val urlC = ComponentWrapper.literal(url);
        val endC = ComponentWrapper.literal(end);
        try {
            BaseText_setStyle.invoke(urlC, Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Nova-Committee/Bot-Connect/issues/new")));
            BaseText_append.invoke(send, urlC);
            BaseText_append.invoke(urlC, endC);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new UnsupportedOperationException("Failed to invoke method \"BaseComponent/MutableComponent::setStyle/append\" with reflection", e);
        }
        context.getSource().sendSuccess(send, true);
        return 1;
    }
}
