package cn.evole.mods.mcbot.command;


import cn.evole.mods.mcbot.IMcBot;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
//#if MC >= 11900
import net.minecraft.network.chat.Component;
//#else
//$$ import net.minecraft.network.chat.TextComponent;
//#endif

public class ReConnectCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        IMcBot.config.getBotConfig().setReconnect(isEnabled);
        if (isEnabled) {
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("已设置自动重连"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("已设置自动重连"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("已设置自动重连"), true);
            //#endif
        } else {
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("已关闭自动重连"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("已关闭自动重连"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("已关闭自动重连"), true);
            //#endif
        }

        IMcBot.config.save();
        return 1;
    }
}
