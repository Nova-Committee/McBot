package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class RemoveGroupIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var id = context.getArgument("GroupID", Long.class);
        if (BotApi.config.getCommon().getGroupIdList().contains(id)) {
            BotApi.config.getCommon().removeGroupId(id);

        } else {
            context.getSource().sendSuccess(Component.literal("QQ群号:" + id + "并未出现！"), true);
        }
        ConfigHandler.save(BotApi.config);
        return 0;
    }


}
