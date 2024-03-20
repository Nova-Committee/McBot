package cn.evole.mods.mcbot.core.event;

import cn.evole.mods.mcbot.command.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;


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
                Commands.literal("mcbot")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.literal("connect")
                                .executes(ConnectCommand::commonExecute)
                                    .then(Commands.argument("parameter", StringArgumentType.greedyString())
                                            .executes(ConnectCommand::execute)
                                    )


                        )
                        .then(Commands.literal("customs").executes(ListCustomCommand::execute))
                        .then(Commands.literal("reload").executes(ReloadConfigCmd::execute))
                        .then(Commands.literal("disconnect").executes(DisconnectCommand::execute))

                        .then(Commands.literal("help").executes(HelpCommand::execute))
                        .then(Commands.literal("recall")
                                .then(Commands.argument("MessageId", IntegerArgumentType.integer())
                                        .executes(RecallCommand::execute)))
                        .then(Commands.literal("addBind")
                                .then(Commands.argument("GroupName", StringArgumentType.greedyString())
                                        .then(Commands.argument("QQId", StringArgumentType.greedyString())
                                                .then(Commands.argument("GameName", StringArgumentType.greedyString())
                                                        .executes(AddBindCommand::execute)
                                                ))))
                        .then(Commands.literal("delBind")
                                .then(Commands.argument("QQId", StringArgumentType.greedyString())
                                        .executes(DelBindCommand::execute)))


                        .then(Commands.literal("debug")
                                .then(Commands.argument("enabled", BoolArgumentType.bool())
                                        .executes(DebugCommand::execute)))
                        .then(Commands.literal("addGroup")
                                .then(Commands.argument("GroupId", LongArgumentType.longArg())
                                        .executes(AddGroupIDCommand::execute)))
                        .then(Commands.literal("delGroup")
                                .then(Commands.argument("GroupId", LongArgumentType.longArg())
                                        .executes(DelGroupIDCommand::execute)))
                        .then(Commands.literal("setBot")
                                .then(Commands.argument("BotId", LongArgumentType.longArg())
                                        .executes(BotIDCommand::execute)))
                        .then(Commands.literal("setAuthKey")
                                .then(Commands.argument("AuthKey", StringArgumentType.string())
                                        .executes(AuthKeyCommand::execute)))
                        .then(Commands.literal("status").executes(StatusCommand::execute))

                        .then(Commands.literal("receive")
                                .then(Commands.literal("all")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(ReceiveCommand::allExecute)))
                                .then(Commands.literal("chat")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(ReceiveCommand::chatExecute)))
                                .then(Commands.literal("cmd")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(ReceiveCommand::cmdExecute))))

                        .then(Commands.literal("send")
                                .then(Commands.literal("all")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCommand::allExecute)))
                                .then(Commands.literal("join")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCommand::joinExecute)))
                                .then(Commands.literal("leave")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCommand::leaveExecute)))
                                .then(Commands.literal("death")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCommand::deathExecute)))
                                .then(Commands.literal("chat")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCommand::chatExecute)))
                                .then(Commands.literal("achievements")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCommand::achievementsExecute)))
                                .then(Commands.literal("qqWelcome")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCommand::qqWelcomeExecute)))
                                .then(Commands.literal("qqLeave")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCommand::qqLeaveExecute)))
                        )
        );


    }
}
