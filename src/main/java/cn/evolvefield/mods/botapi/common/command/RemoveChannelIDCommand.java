package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

public class RemoveChannelIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var id = context.getArgument("ChannelID", String.class);
        if (BotApi.config.getCommon().getChannelIdList().contains(id)) {
            BotApi.config.getCommon().removeChannelId(id);

        } else {
            context.getSource().sendSuccess(new TextComponent("子频道号:" + id + "并未出现！"), true);

        }
        ConfigManger.saveBotConfig(BotApi.config);
        return 0;
    }


}
