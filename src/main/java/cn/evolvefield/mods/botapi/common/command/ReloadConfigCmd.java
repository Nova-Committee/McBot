package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.mods.multi.common.ComponentWrapper;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/28 13:37
 * Version: 1.0
 */
public class ReloadConfigCmd {
    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        try {
            ConfigHandler.load(BotApi.CONFIG_FILE);
            if (ConfigHandler.cached() == null) {
                context.getSource().sendSuccess(ComponentWrapper.literal("重载配置失败"), true);
            }
            context.getSource().sendSuccess(ComponentWrapper.literal("重载配置成功"), true);
        } catch (Exception e) {
            context.getSource().sendSuccess(ComponentWrapper.literal("重载配置失败"), true);
        }
        ConfigHandler.save();
        return 1;
    }
}
