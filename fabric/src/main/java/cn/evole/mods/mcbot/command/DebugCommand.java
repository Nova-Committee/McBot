package cn.evole.mods.mcbot.command;

import cn.evole.mods.mcbot.init.config.ModConfig;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
//#if MC >= 11900
import net.minecraft.network.chat.Component;
//#else
//$$ import net.minecraft.network.chat.TextComponent;
//#endif
/**
 * @author cnlimiter
 * @date 2021/11/17 13:05
 */
public class DebugCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ModConfig.INSTANCE.getCommon().setDebug(isEnabled);
        if (isEnabled) {
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("已开启开发者模式"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("已开启开发者模式"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("已开启开发者模式"), true);
            //#endif
        } else {
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("已关闭开发者模式"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("已关闭开发者模式"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("已关闭开发者模式"), true);
            //#endif
        }
 
        return 1;
    }
}
