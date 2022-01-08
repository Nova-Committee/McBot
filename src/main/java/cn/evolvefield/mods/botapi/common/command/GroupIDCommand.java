package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.config.ConfigManger;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

import static net.minecraft.commands.Commands.literal;

public class GroupIDCommand {


    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return literal("setID")
                .then(Commands.argument("QQGroupID", IntegerArgumentType.integer())
                        .executes(GroupIDCommand::execute));
    }
    public static int execute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        int id = context.getArgument("QQGroupID",Integer.class);
        BotApi.config.getCommon().setGroupId(id);
        ConfigManger.saveBotConfig(BotApi.config);
        context.getSource().sendSuccess(
                new TextComponent("已设置互通的群号为:" + id), true);
        return 0;
    }


}
