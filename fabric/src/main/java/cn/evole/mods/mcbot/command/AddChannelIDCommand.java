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

public class AddChannelIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val id = context.getArgument("ChannelID", String.class);
        ModConfig.INSTANCE.getCommon().setGuildOn(true);
        if (ModConfig.INSTANCE.getCommon().getChannelIdList().contains(id)) {

            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("子频道号:" + id + "已经出现了！"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("子频道号:" + id + "已经出现了！"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("子频道号:" + id + "已经出现了！"), true);
            //#endif

        } else {
            ModConfig.INSTANCE.getCommon().addChannelId(id);
        }
        return 1;
    }


}
