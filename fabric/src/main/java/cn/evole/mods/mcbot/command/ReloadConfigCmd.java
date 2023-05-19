package cn.evole.mods.mcbot.command;

import cn.evole.mods.mcbot.McBot;
import cn.evole.mods.mcbot.init.handler.ConfigHandler;
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
            ConfigHandler.load(McBot.CONFIG_FILE);
            if (ConfigHandler.cached() == null) {
                //#if MC >= 11900
                context.getSource().sendSuccess(Component.literal("重载配置失败"), true);
                //#else
                //$$ context.getSource().sendSuccess(new TextComponent("重载配置失败"), true);
                //#endif
            }
            //#if MC >= 11900
            context.getSource().sendSuccess(Component.literal("重载配置成功"), true);
            //#else
            //$$ context.getSource().sendSuccess(new TextComponent("重载配置成功"), true);
            //#endif
        } catch (Exception e) {
            //#if MC >= 11900
            context.getSource().sendSuccess(Component.literal("重载配置失败"), true);
            //#else
            //$$ context.getSource().sendSuccess(new TextComponent("重载配置失败"), true);
            //#endif
        }
        ConfigHandler.save();
        return 1;
    }
}
