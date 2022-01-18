package cn.evolvefield.mods.botapi.init.events;

import cn.evolvefield.mods.botapi.common.command.*;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.commands.Commands;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/12 9:12
 * Version: 1.0
 */
public class CommandEventHandler {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
                    dispatcher.register(
                            Commands.literal("mcbot")
                                    .then(Commands.literal("disconnect").executes(DisconnectCommand::execute)));
                    dispatcher.register(
                            Commands.literal("mcbot")
                                    .then(Commands.literal("debug")
                                            .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                    .executes(DebugCommand::execute))));
                    dispatcher.register(
                            Commands.literal("mcbot")
                                    .then(Commands.literal("setID")
                                            .then(Commands.argument("QQGroupID", IntegerArgumentType.integer())
                                                    .executes(GroupIDCommand::execute))));
                    dispatcher.register(
                            Commands.literal("mcbot")
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
                    );
                    dispatcher.register(
                            Commands.literal("mcbot")
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
                                            .then(Commands.literal("welcome")
                                                    .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                            .executes(SendCommand::welcomeExecute)))
                                    ));
                    dispatcher.register(
                            Commands.literal("mcbot")
                                    .then(Commands.literal("status").executes(StatusCommand::execute)));

                }
        );
    }
}
