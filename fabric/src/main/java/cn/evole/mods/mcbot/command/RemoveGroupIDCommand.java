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
public class RemoveGroupIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val id = context.getArgument("GroupID", Long.class);
        if (ModConfig.INSTANCE.getCommon().getGroupIdList().contains(id)) {
            ModConfig.INSTANCE.getCommon().removeGroupId(id);
        } else {
            //#if MC >= 12000
            context.getSource().sendSuccess(()->Component.literal("QQ群号:" + id + "并未出现！"), true);
            //#elseif MC < 11900
            //$$ context.getSource().sendSuccess(new TextComponent("QQ群号:" + id + "并未出现！"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("QQ群号:" + id + "并未出现！"), true);
            //#endif
        }

        return 1;
    }


}
