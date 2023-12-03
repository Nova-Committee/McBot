package cn.evole.mods.mcbot.command;

import cn.evole.mods.mcbot.init.config.ModConfig;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
//#if MC >= 11900
import net.minecraft.network.chat.Component;
//#else
//$$ import net.minecraft.network.chat.TextComponent;
//#endif
/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/28 13:37
 * Version: 1.0
 */
public class ReloadConfigCmd {
    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        try {
            ModConfig.INSTANCE.reload();
            if (ModConfig.INSTANCE == null) {
                //#if MC >= 12000
                context.getSource().sendSuccess(()->Component.literal("重载配置失败"), true);
                //#elseif MC < 11900
                //$$ context.getSource().sendSuccess(new TextComponent("重载配置失败"), true);
                //#else
                //$$ context.getSource().sendSuccess(Component.literal("重载配置失败"), true);
                //#endif
            }
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("重载配置成功"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("重载配置成功"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("重载配置成功"), true);
            //#endif
        } catch (Exception e) {
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("重载配置失败"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("重载配置失败"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("重载配置失败"), true);
            //#endif
        }
        return 1;
    }
}
