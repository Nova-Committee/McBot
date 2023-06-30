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
public class RemoveChannelIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val id = context.getArgument("ChannelID", String.class);
        if (ConfigHandler.cached().getCommon().getChannelIdList().contains(id)) {
            ConfigHandler.cached().getCommon().removeChannelId(id);
        } else {
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("子频道号:" + id + "并未出现！"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("子频道号:" + id + "并未出现！"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("子频道号:" + id + "并未出现！"), true);
            //#endif
        }
        ConfigHandler.save();
        return 1;
    }


}
