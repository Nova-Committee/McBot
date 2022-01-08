package cn.evolvefield.mods.botapi.service;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.command.Invoke;
import cn.evolvefield.mods.botapi.event.TickEventHandler;
import cn.evolvefield.mods.botapi.api.MessageJson;
import cn.evolvefield.mods.botapi.api.SendMessage;
import net.minecraftforge.event.ServerChatEvent;

public class MessageHandlerService {

    /**
     * 向已连接的服务端发送消息
     * @param event 需要处理的聊天事件
     */

    public static void sendMessage(ServerChatEvent event) {


        SendMessage.Group(BotApi.config.getCommon().getGroupId(),String.format("[MC]<%s> %s", event.getUsername(), event.getMessage()));


    }

    /**
     * 处理服务器数据并于本地服务端发送聊天信息
     * @param msg 服务器来源数据
     */
    public static void receiveMessage(String msg) {
        MessageJson serverMessage;
        String text;
        String name;
        long sourceId;
        long groupId;

        String msgType;
        String postType;
        String noticeType;


        if(!msg.isEmpty() ){

            serverMessage = new MessageJson(msg);
            postType = serverMessage.getPost_type();
            msgType = serverMessage.getMessage_type();

            noticeType = serverMessage.getNotice_type();

            text = serverMessage.getMessage();
            sourceId = serverMessage.getUser_id();
            groupId = serverMessage.getGroup_id();
            name = serverMessage.getNickname();

            if(postType != null){
                switch (postType) {
                    case "message":
                        if (msgType.equals("group")) {
                            if (groupId == BotApi.config.getCommon().getGroupId() && BotApi.config.getCommon().isRECEIVE_ENABLED()) {
                                if (BotApi.config.getCommon().isDebuggable()) {
                                    BotApi.LOGGER.info("收到群" + groupId + "发送消息" + text);
                                }
                                if (text.startsWith("!") && BotApi.config.getCommon().isR_COMMAND_ENABLED()) {
                                    Invoke.invokeCommand(text);
                                } else if (!text.startsWith("[CQ:") && BotApi.config.getCommon().isR_CHAT_ENABLE()) {
                                    String toSend = String.format("§b[§lQQ§r§b]§a<%s>§f %s", name, text);
                                    TickEventHandler.getToSendQueue().add(toSend);
                                }
                            }
                        }
                        break;
                    case "notice":
                        if (noticeType.equals("group_increase")) {
                            SendMessage.Group(BotApi.config.getCommon().getGroupId(), BotApi.config.getCommon().getWelcomeNotice());
                        } else if (noticeType.equals("group_decrease")) {
                            SendMessage.Group(BotApi.config.getCommon().getGroupId(), BotApi.config.getCommon().getLeaveNotice());
                        }
                }
            }

        }






    }
}
