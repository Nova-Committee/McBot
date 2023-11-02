package cn.evole.mods.mcbot.init.event;

import cn.evole.mods.mcbot.command.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/12 9:12
 * Version: 1.0
 */
public class ICmdEvent {
    public static LiteralArgumentBuilder<CommandSourceStack> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    public static <T> RequiredArgumentBuilder<CommandSourceStack, T> argument(String arg, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(arg, type);
    }
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(
                literal("mcbot")
                        .requires(source -> source.hasPermission(2))
                        .then(literal("connect")
                                .then(literal("cqhttp")
                                        .executes(ConnectCommand::cqhttpCommonExecute)
                                        .then(argument("parameter", StringArgumentType.greedyString())
                                                .executes(ConnectCommand::cqhttpExecute)
                                        )
                                )
                                .then(literal("mirai")
                                        .executes(ConnectCommand::miraiCommonExecute)
                                        .then(argument("parameter", StringArgumentType.greedyString())
                                                .executes(ConnectCommand::miraiExecute)
                                        )
                                )
                        )
                        .then(literal("customs").executes(ListCustomCommand::execute))
                        .then(literal("reload").executes(ReloadConfigCmd::execute))
                        .then(literal("disconnect").executes(DisconnectCommand::execute))
                        .then(literal("addChannelId")
                                .then(argument("ChannelId", StringArgumentType.greedyString())
                                        .executes(AddChannelIDCommand::execute)))
                        .then(literal("delChannelId")
                                .then(argument("ChannelId", StringArgumentType.greedyString())
                                        .executes(RemoveChannelIDCommand::execute)))
                        .then(literal("setGuild")
                                .then(argument("GuildId", StringArgumentType.greedyString())
                                        .executes(GuildIDCommand::execute)))
                        .then(literal("help").executes(HelpCommand::execute))
                        .then(literal("debug")
                                .then(argument("enabled", BoolArgumentType.bool())
                                        .executes(DebugCommand::execute)))
                        .then(literal("addGroup")
                                .then(argument("GroupId", LongArgumentType.longArg())
                                        .executes(AddGroupIDCommand::execute)))
                        .then(literal("delGroup")
                                .then(argument("GroupId", LongArgumentType.longArg())
                                        .executes(RemoveGroupIDCommand::execute)))
                        .then(literal("setBot")
                                .then(argument("BotId", LongArgumentType.longArg())
                                        .executes(BotIDCommand::execute)))
                        .then(literal("setAuthKey")
                                .then(argument("AuthKey", StringArgumentType.string())
                                        .executes(AuthKeyCommand::execute)))
                        .then(literal("status").executes(StatusCommand::execute))

                        .then(literal("receive")
                                .then(literal("all")
                                        .then(argument("enabled", BoolArgumentType.bool())
                                                .executes(ReceiveCommand::allExecute)))
                                .then(literal("chat")
                                        .then(argument("enabled", BoolArgumentType.bool())
                                                .executes(ReceiveCommand::chatExecute)))
                                .then(literal("cmd")
                                        .then(argument("enabled", BoolArgumentType.bool())
                                                .executes(ReceiveCommand::cmdExecute))))

                        .then(literal("send")
                                .then(literal("all")
                                        .then(argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCommand::allExecute)))
                                .then(literal("join")
                                        .then(argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCommand::joinExecute)))
                                .then(literal("leave")
                                        .then(argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCommand::leaveExecute)))
                                .then(literal("death")
                                        .then(argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCommand::deathExecute)))
                                .then(literal("chat")
                                        .then(argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCommand::chatExecute)))
                                .then(literal("achievements")
                                        .then(argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCommand::achievementsExecute)))
                                .then(literal("qqWelcome")
                                        .then(argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCommand::qqWelcomeExecute)))
                                .then(literal("qqLeave")
                                        .then(argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCommand::qqLeaveExecute)))
                        )
        );


    }
}
