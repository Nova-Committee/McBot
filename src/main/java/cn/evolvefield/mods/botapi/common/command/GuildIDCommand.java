package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.bot.BotData;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

public class GuildIDCommand {


	public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
		var id = context.getArgument("GuildID", String.class);
		BotApi.config.getCommon().setGuildOn(true);
		BotApi.config.getCommon().setGuildId(id);
		BotData.setGuildId(id);
		ConfigManger.saveBotConfig(BotApi.config);
		context.getSource().sendSuccess(
				new TextComponent("已设置互通的频道号为:" + id), true);
		return 0;
	}


}
