package cn.evolvefield.mods.botapi.core.bot;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.data.BindApi;
import cn.evolvefield.mods.botapi.api.events.GroupMessageEvent;
import cn.evolvefield.mods.botapi.api.message.SendMessage;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Invoke {

    public static final MinecraftServer SERVER = FMLCommonHandler.instance().getMinecraftServerInstance();
    private static final DecimalFormat TIME_FORMATTER = new DecimalFormat("########0.000");

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
        List<String> temp = new ArrayList<>();
        if (commandBody.equals("tps")) {
            String outPut = "服务器TPS";
            for (Integer dimId : DimensionManager.getIDs()) {
                double worldTickTime = mean(SERVER.worldTickTimes.get(dimId)) * 1.0E-6D;
                double worldTPS = Math.min(1000.0 / worldTickTime, 20);
                temp.add(String.format("%s : TPS: %s ", getDimensionPrefix(dimId), TIME_FORMATTER.format(worldTPS)));

            }
            BotApi.LOGGER.info(temp);
            String tpsOut = temp.stream().reduce("", (listString, tps) ->
                    listString.length() == 0 ? tps : listString + ", " + tps);
            outPut += "\n" + tpsOut;
            SendMessage.Group(BotApi.config.getCommon().getGroupId(), outPut);
        } else if (commandBody.equals("list")) {
            List<EntityPlayerMP> users = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers();
            String result = "在线玩家数量: " + users.size();

            if (users.size() > 0) {
                String userList = users.stream()
                        .map(EntityPlayer::getDisplayNameString)
                        .reduce("", (listString, user) ->
                                listString.length() == 0 ? user : listString + ", " + user
                        );
                result += "\n" + "玩家列表: " + userList;
            }

            if (BotApi.config.getCommon().isDebuggable()) {
                BotApi.LOGGER.info("处理命令list:" + result);
            }
            SendMessage.Group(BotApi.config.getCommon().getGroupId(), result);
        } else if (commandBody.startsWith(bindCommand)) {


            if (formatMsg.length == 1) {
                SendMessage.Group(BotApi.config.getCommon().getGroupId(), "请输入有效的游戏名");
                return;
            }

            String BindPlay = formatMsg[1];
            List<String> msg = new ArrayList<>();


            if (SERVER.getPlayerList().getPlayerByUsername(BindPlay) == null) {
                String m = BotApi.config.getCmd().getBindNotOnline();
                msg.add(m.replace("%Player%", BindPlay));
                SendMessage.Group(BotApi.config.getCommon().getGroupId(), msg);
                return;
            }

            if (BindApi.addBind(event.getUserId(), BindPlay)) {
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
                case "add": {
                    String playerName = formatMsg[2];
                    GameProfile gameprofile = SERVER.getPlayerProfileCache().getGameProfileForUsername(playerName);
                    if (gameprofile != null) {
                        SERVER.getPlayerList().addWhitelistedPlayer(gameprofile);
                    }
                    boolean flag = false;
                    for (String name : SERVER.getPlayerList().getWhitelistedPlayerNames()) {
                        if (name.equals(playerName)) {
                            flag = true;
                            break;
                        }
                    }

                    if (flag) {
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "添加" + playerName + "至白名单成功！");
                    } else {
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "添加" + playerName + "至白名单失败或已经添加了白名单！");
                    }

                    if (BotApi.config.getCommon().isDebuggable()) {
                        BotApi.LOGGER.info("处理命令white add " + playerName);
                    }
                    break;
                }
                case "del": {
                    String playerName = formatMsg[2];

                    GameProfile gameprofile1 = SERVER.getPlayerList().getWhitelistedPlayers().getByName(playerName);
                    SERVER.getPlayerList().removePlayerFromWhitelist(gameprofile1);

                    boolean flag = false;
                    for (String name : SERVER.getPlayerList().getWhitelistedPlayerNames()) {
                        if (name.equals(playerName)) {
                            flag = true;
                            break;
                        }
                    }

                    if (true) {
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "从白名单移除" + playerName + "失败或已经从白名单移除！");
                    } else {
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "从白名单移除" + playerName + "成功！");
                    }
                    if (BotApi.config.getCommon().isDebuggable()) {
                        BotApi.LOGGER.info("处理命令white del " + playerName);
                    }
                    break;
                }
                case "list": {
                    String[] strings = SERVER.getPlayerList().getWhitelistedPlayerNames();
                    List<String> msg = new ArrayList<>();
                    msg.add("当前服务器白名单：\n");
                    msg.addAll(Arrays.asList(strings));
                    SendMessage.Group(BotApi.config.getCommon().getGroupId(), msg);
                    if (BotApi.config.getCommon().isDebuggable()) {
                        BotApi.LOGGER.info("处理命令white list");
                    }
                    break;
                }
                case "on": {
                    PlayerList playerList = SERVER.getPlayerList();
                    if (playerList.isWhiteListEnabled()) {
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "已经打开了白名单！哼~");
                    } else {
                        playerList.setWhiteListEnabled(true);
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "打开白名单成功！");

                    }

                    if (BotApi.config.getCommon().isDebuggable()) {
                        BotApi.LOGGER.info("处理命令white on");
                    }
                    break;
                }
                case "off": {
                    PlayerList playerList = SERVER.getPlayerList();
                    if (!playerList.isWhiteListEnabled()) {
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "白名单早就关了！");
                    } else {
                        playerList.setWhiteListEnabled(false);
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "关闭白名单成功！");
                    }

                    if (BotApi.config.getCommon().isDebuggable()) {
                        BotApi.LOGGER.info("处理命令white off");
                    }
                    break;
                }
                case "reload": {
                    SERVER.getPlayerList().reloadWhitelist();
                    SendMessage.Group(BotApi.config.getCommon().getGroupId(), "刷新白名单成功！");
                    if (BotApi.config.getCommon().isDebuggable()) {
                        BotApi.LOGGER.info("处理命令white reload");
                    }
                    break;
                }
            }
        }
    }


    private static long mean(long[] values) {
        long sum = Arrays.stream(values)
                .reduce(0L, Long::sum);

        return sum / values.length;
    }

    private static String getDimensionPrefix(int dimId) {
        DimensionType providerType = DimensionManager.getProviderType(dimId);
        if (providerType == null) {
            return String.format("Dim %2d", dimId);
        } else {
            return String.format("Dim %2d (%s)", dimId, providerType.getName());
        }
    }

}
