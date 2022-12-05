package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.Static;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class AddGroupIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var id = context.getArgument("GroupId", Long.class);
        Static.LOGGER.info(String.valueOf(id));
        if (BotApi.config.getCommon().getGroupIdList().contains(id)) {
            context.getSource().sendSuccess(Component.literal("QQ群号:" + id + "已经出现了！"), true);
        } else {
            BotApi.config.getCommon().addGroupId(id);
        }
        ConfigHandler.save(BotApi.config);
        return Command.SINGLE_SUCCESS;
    }


}
