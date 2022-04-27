package cn.evolvefield.mods.botapi.core.bot;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.data.BindApi;
import cn.evolvefield.mods.botapi.api.events.ChannelGroupMessageEvent;
import cn.evolvefield.mods.botapi.api.events.GroupMessageEvent;
import cn.evolvefield.mods.botapi.api.message.SendMessage;
import com.google.common.collect.Lists;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Invoke {

	public static void invokeChannelCmd(ChannelGroupMessageEvent event) {
		String message = "";
		String bindCommand = BotApi.config.getCmd().getBindCommand();
		String whiteListCommand = BotApi.config.getCmd().getWhiteListCommand();
		if (BotData.getBotFrame().equalsIgnoreCase("cqhttp")) {
			message = event.getMessage();
			String[] formatMsg = message.split(" ");
			String commandBody = formatMsg[0].substring(1);

			if (commandBody.equals("tps")) {
				SendMessage.ChannelGroup(event.getGuild_id(), event.getChannel_id(), tpsCmd());

			} else if (commandBody.equals("list")) {
				SendMessage.ChannelGroup(event.getGuild_id(), event.getChannel_id(), listCmd());
			} else if (commandBody.startsWith(bindCommand)) {
				if (formatMsg.length == 1) {
					SendMessage.ChannelGroup(event.getGuild_id(), event.getChannel_id(), "请输入有效的游戏名");
					return;
				}

				String bindPlayName = formatMsg[1];
				List<String> msg = new ArrayList<>();


				if (BotApi.SERVER.getPlayerList().getPlayerByName(bindPlayName) == null) {
					String m = BotApi.config.getCmd().getBindNotOnline();
					msg.add(m.replace("%Player%", bindPlayName));
					SendMessage.ChannelGroup(event.getGuild_id(), event.getChannel_id(), msg);
					return;
				}

				if (BindApi.addGuidBind(event.getTiny_id(), bindPlayName)) {
					String m = BotApi.config.getCmd().getBindSuccess();
					msg.add(m.replace("%Player%", bindPlayName));

				} else {
					String m = BotApi.config.getCmd().getBindFail();
					msg.add(m.replace("%Player%", bindPlayName));
				}

				if (BotApi.config.getCommon().isDebuggable()) {
					BotApi.LOGGER.info("处理命令bind:" + msg + "PlayerName:" + bindPlayName);
				}

				SendMessage.ChannelGroup(event.getGuild_id(), event.getChannel_id(), msg);

			}
		}
	}

	public static void invokeCommand(GroupMessageEvent event) {
		String message = "";
		String bindCommand = BotApi.config.getCmd().getBindCommand();
		String whiteListCommand = BotApi.config.getCmd().getWhiteListCommand();

		if (BotData.getBotFrame().equalsIgnoreCase("cqhttp")) {
			message = event.getMessage();
			String[] formatMsg = message.split(" ");
			String commandBody = formatMsg[0].substring(1);

			if (!event.getRole().equals("member")) {
				masterMsgParse(whiteListCommand, formatMsg, commandBody);
			}
			memberMsgParse(event, message, bindCommand, commandBody, formatMsg);

		} else if (BotData.getBotFrame().equalsIgnoreCase("mirai")) {
			message = event.getMiraiMessage().get(1).getMessage();
			String[] formatMsg = message.split(" ");
			String commandBody = formatMsg[0].substring(1);

			if (!event.getPermission().equals("MEMBER")) {
				masterMsgParse(whiteListCommand, formatMsg, commandBody);
			}
			memberMsgParse(event, message, bindCommand, commandBody, formatMsg);

		}

	}

	private static void memberMsgParse(GroupMessageEvent event, String message, String bindCommand, String commandBody, String[] formatMsg) {
		if (commandBody.equals("tps")) {
			SendMessage.Group(BotApi.config.getCommon().getGroupId(), tpsCmd());
		} else if (commandBody.equals("list")) {
			SendMessage.Group(BotApi.config.getCommon().getGroupId(), listCmd());
		} else if (commandBody.startsWith(bindCommand)) {

			if (formatMsg.length == 1) {
				SendMessage.Group(BotApi.config.getCommon().getGroupId(), "请输入有效的游戏名");
				return;
			}

			String BindPlay = formatMsg[1];
			List<String> msg = new ArrayList<>();


			if (BotApi.SERVER.getPlayerList().getPlayerByName(BindPlay) == null) {
				String m = BotApi.config.getCmd().getBindNotOnline();
				msg.add(m.replace("%Player%", BindPlay));
				SendMessage.Group(BotApi.config.getCommon().getGroupId(), msg);
				return;
			}

			if (BindApi.addGroupBind(event.getUserId(), BindPlay)) {
				String m = BotApi.config.getCmd().getBindSuccess();
				msg.add(m.replace("%Player%", BindPlay));

			} else {
				String m = BotApi.config.getCmd().getBindFail();
				msg.add(m.replace("%Player%", BindPlay));
			}

			if (BotApi.config.getCommon().isDebuggable()) {
				BotApi.LOGGER.info("处理命令bind:" + msg + "PlayerName:" + BindPlay);
			}

			SendMessage.Group(BotApi.config.getCommon().getGroupId(), msg);

		}
	}

	private static void masterMsgParse(String whiteListCommand, String[] formatMsg, String commandBody) {
		if (commandBody.startsWith(whiteListCommand)) {
			String subCmd = formatMsg[1];
			switch (subCmd) {
				case "add" -> {
					String playerName = formatMsg[2];
					int success = BotApi.SERVER.getCommands().performCommand(new CommandSourceStack(CommandSource.NULL, Vec3.ZERO, Vec2.ZERO, BotApi.SERVER.overworld(), 4, "",
							new TextComponent(""), Objects.requireNonNull(BotApi.SERVER), null), "whitelist add " + playerName);

					if (success == 0) {
						SendMessage.Group(BotApi.config.getCommon().getGroupId(), "添加" + playerName + "至白名单失败或已经添加了白名单！");
					} else {
						SendMessage.Group(BotApi.config.getCommon().getGroupId(), "添加" + playerName + "至白名单成功！");
					}

					if (BotApi.config.getCommon().isDebuggable()) {
						BotApi.LOGGER.info("处理命令white add " + playerName);
					}
					break;
				}
				case "del" -> {
					String playerName = formatMsg[2];
					int success = BotApi.SERVER.getCommands().performCommand(new CommandSourceStack(CommandSource.NULL, Vec3.ZERO, Vec2.ZERO, (ServerLevel) BotApi.SERVER.overworld(), 4, "",
							new TextComponent(""), Objects.requireNonNull(BotApi.SERVER), null), "whitelist remove " + playerName);

					if (success == 0) {
						SendMessage.Group(BotApi.config.getCommon().getGroupId(), "从白名单移除" + playerName + "失败或已经从白名单移除！");
					} else {
						SendMessage.Group(BotApi.config.getCommon().getGroupId(), "从白名单移除" + playerName + "成功！");
					}
					if (BotApi.config.getCommon().isDebuggable()) {
						BotApi.LOGGER.info("处理命令white del " + playerName);
					}
					break;
				}
				case "list" -> {
					String[] strings = BotApi.SERVER.getPlayerList().getWhiteListNames();
					List<String> msg = new ArrayList<>();
					msg.add("当前服务器白名单：\n");
					msg.addAll(Arrays.asList(strings));
					SendMessage.Group(BotApi.config.getCommon().getGroupId(), msg);
					if (BotApi.config.getCommon().isDebuggable()) {
						BotApi.LOGGER.info("处理命令white list");
					}
				}
				case "on" -> {
					PlayerList playerList = BotApi.SERVER.getPlayerList();
					if (playerList.isUsingWhitelist()) {
						SendMessage.Group(BotApi.config.getCommon().getGroupId(), "已经打开了白名单！哼~");
					} else {
						playerList.setUsingWhiteList(true);
						BotApi.SERVER.setEnforceWhitelist(true);
						kickUnlistedPlayers();
						SendMessage.Group(BotApi.config.getCommon().getGroupId(), "打开白名单成功！");

					}

					if (BotApi.config.getCommon().isDebuggable()) {
						BotApi.LOGGER.info("处理命令white on");
					}
					break;
				}
				case "off" -> {
					PlayerList playerList = BotApi.SERVER.getPlayerList();
					if (!playerList.isUsingWhitelist()) {
						SendMessage.Group(BotApi.config.getCommon().getGroupId(), "白名单早就关了！");
					} else {
						playerList.setUsingWhiteList(false);
						BotApi.SERVER.setEnforceWhitelist(false);
						SendMessage.Group(BotApi.config.getCommon().getGroupId(), "关闭白名单成功！");
					}

					if (BotApi.config.getCommon().isDebuggable()) {
						BotApi.LOGGER.info("处理命令white off");
					}
					break;
				}
				case "reload" -> {
					BotApi.SERVER.getPlayerList().reloadWhiteList();
					kickUnlistedPlayers();
					SendMessage.Group(BotApi.config.getCommon().getGroupId(), "刷新白名单成功！");
					if (BotApi.config.getCommon().isDebuggable()) {
						BotApi.LOGGER.info("处理命令white reload");
					}
					break;
				}
			}
		}
	}

	public static void kickUnlistedPlayers() {
		if (BotApi.SERVER.isEnforceWhitelist()) {
			PlayerList playerList = BotApi.SERVER.getPlayerList();
			UserWhiteList userWhiteList = playerList.getWhiteList();
			List<ServerPlayer> list = Lists.newArrayList(playerList.getPlayers());

			for (ServerPlayer serverPlayer : list) {
				if (!userWhiteList.isWhiteListed(serverPlayer.getGameProfile())) {
					serverPlayer.connection.disconnect(new TranslatableComponent("multiplayer.disconnect.not_whitelisted"));
				}
			}

		}
	}

	private static String tpsCmd() {
		double overTPS = BotApi.service.recentTps()[0];
		double overMspt = BotApi.service.averageMspt();


		String outPut = String.format("主世界 TPS: %.2f", overTPS)
				+ "\n" + String.format("主世界 MSPT: %.2f", overMspt);

		if (BotApi.config.getCommon().isDebuggable()) {
			BotApi.LOGGER.info("处理命令tps:" + outPut);
		}
		return outPut;
	}

	private static String listCmd() {
		List<ServerPlayer> users = BotApi.SERVER.getPlayerList().getPlayers();

		String result = "在线玩家数量: " + users.size();

		if (users.size() > 0) {
			Component userList = users.stream()
					.map(Player::getDisplayName)
					.reduce(new TextComponent(""), (listString, user) ->
							listString.getString().length() == 0 ? user : new TextComponent(listString.getString() + ", " + user.getString())
					);
			result += "\n" + "玩家列表: " + userList.getString();
		}
		if (BotApi.config.getCommon().isDebuggable()) {
			BotApi.LOGGER.info("处理命令list:" + result);
		}
		return result;
	}

	private static long mean(long[] values) {
		long sum = Arrays.stream(values)
				.reduce(0L, Long::sum);

		return sum / values.length;
	}

}
