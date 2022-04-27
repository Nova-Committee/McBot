package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.common.command.*;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
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
							.requires(source -> source.hasPermission(2))
							.then(Commands.literal("connect")
									.then(Commands.literal("cqhttp")
											.executes(ConnectCommand::cqhttpCommonExecute)
											.then(Commands.argument("parameter", StringArgumentType.greedyString())
													.executes(ConnectCommand::cqhttpExecute)
											)
									)
									.then(Commands.literal("mirai")
											.executes(ConnectCommand::miraiCommonExecute)
											.then(Commands.argument("parameter", StringArgumentType.greedyString())
													.executes(ConnectCommand::miraiExecute)
											)
									)

							)
							.then(Commands.literal("disconnect").executes(DisconnectCommand::execute))
							.then(Commands.literal("setGuild")
									.then(Commands.argument("GuildId", StringArgumentType.greedyString())
											.executes(GuildIDCommand::execute)))
							.then(Commands.literal("addChannelId")
									.then(Commands.argument("ChannelId", StringArgumentType.greedyString())
											.executes(AddChannelIDCommand::execute)))
							.then(Commands.literal("delChannelId")
									.then(Commands.argument("ChannelId", StringArgumentType.greedyString())
											.executes(RemoveChannelIDCommand::execute)))
							.then(Commands.literal("setGuild")
									.then(Commands.argument("GuildId", StringArgumentType.greedyString())
											.executes(GuildIDCommand::execute)))
							.then(Commands.literal("help").executes(HelpCommand::execute))
							.then(Commands.literal("debug")
									.then(Commands.argument("enabled", BoolArgumentType.bool())
											.executes(DebugCommand::execute)))
							.then(Commands.literal("setGroup")
									.then(Commands.argument("GroupId", LongArgumentType.longArg())
											.executes(GroupIDCommand::execute)))
							.then(Commands.literal("setBot")
									.then(Commands.argument("BotId", LongArgumentType.longArg())
											.executes(BotIDCommand::execute)))
							.then(Commands.literal("setFrame")
									.then(Commands.argument("Frame", StringArgumentType.string())
											.executes(FrameCommand::execute)))
							.then(Commands.literal("setVerifyKey")
									.then(Commands.argument("VerifyKey", StringArgumentType.string())
											.executes(VerifyKeyCommand::execute)))
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
									.then(Commands.literal("welcome")
											.then(Commands.argument("enabled", BoolArgumentType.bool())
													.executes(SendCommand::welcomeExecute)))
							)
			);


		});
	}
}
