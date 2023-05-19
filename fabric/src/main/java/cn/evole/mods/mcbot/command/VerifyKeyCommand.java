package cn.evole.mods.mcbot.command;

import cn.evole.mods.mcbot.init.handler.ConfigHandler;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.val;
import net.minecraft.commands.CommandSourceStack;
//#if MC >= 11900
import net.minecraft.network.chat.Component;
//#else
//$$ import net.minecraft.network.chat.TextComponent;
//#endif
public class VerifyKeyCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val id = context.getArgument("VerifyKey", String.class);
        ConfigHandler.cached().getBotConfig().setToken(id);
        //#if MC >= 11900
        context.getSource().sendSuccess(Component.literal("已设置Mirai框架的VerifyKey为:" + id), true);
        //#else
        //$$ context.getSource().sendSuccess(new TextComponent("已设置Mirai框架的VerifyKey为:" + id), true);
        //#endif
        ConfigHandler.save();
        return 1;
    }


}
