package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

public class AddChannelIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var id = context.getArgument("ChannelID", String.class);
        BotApi.config.getCommon().setGuildOn(true);
        if (BotApi.config.getCommon().getChannelIdList().contains(id)) {
            context.getSource().sendSuccess(new TextComponent("子频道号:" + id + "已经出现了！"), true);
        } else {
            BotApi.config.getCommon().addChannelId(id);
        }
        ConfigManger.saveBotConfig(BotApi.config);
        return 0;
    }


}
