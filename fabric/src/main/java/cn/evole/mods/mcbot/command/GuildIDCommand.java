package cn.evole.mods.mcbot.command;

import cn.evole.mods.mcbot.init.config.ModConfig;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.val;
import net.minecraft.commands.CommandSourceStack;
//#if MC >= 11900
import net.minecraft.network.chat.Component;
//#else
//$$ import net.minecraft.network.chat.TextComponent;
//#endif
public class GuildIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val id = context.getArgument("GuildID", String.class);
        ModConfig.INSTANCE.getCommon().setGuildOn(true);
        ModConfig.INSTANCE.getCommon().setGuildId(id);
        //#if MC >= 12000
        context.getSource().sendSuccess(()->Component.literal("已设置互通的频道号为:" + id), true);
        //#elseif MC < 11900
        //$$ context.getSource().sendSuccess(new TextComponent("已设置互通的频道号为:" + id), true);
        //#else
        //$$ context.getSource().sendSuccess(Component.literal("已设置互通的频道号为:" + id), true);
        //#endif
        ModConfig.INSTANCE.save();
        return 1;
    }


}
