package cn.evolvefield.mods.botapi.service;


import cn.evolvefield.mods.botapi.command.Invoke;
import cn.evolvefield.mods.botapi.config.ModConfig;
import cn.evolvefield.mods.botapi.event.TickEventHandler;
import cn.evolvefield.mods.botapi.message.MessageJson;
import cn.evolvefield.mods.botapi.message.SendMessage;
import net.minecraftforge.event.ServerChatEvent;

public class MessageService {
    /**
     * 向已连接的服务端发送消息
     * @param event 需要处理的事件
     */
    public static void sendMessage(ServerChatEvent event) {

        SendMessage.Group(ModConfig.GROUP_ID.get(),String.format("[MC]<%s> %s", event.getPlayer().getDisplayName().getString(), event.getMessage()));
        //sendToAll(new TextWebSocketFrame("/send_group_msg?group_id=" + ModConfig.GROUP_ID.get() + "&message=" + event.getMessage()));
    }

    /**
     * 处理服务器数据包并于本地服务端发送聊天信息
     * @param msg 服务器来源数据包
     */
    public static void receiveMessage(String msg) {
        MessageJson serverMessage;
        String text;
        String name;
        long sourceId;
        long groupId;
        //try{
        serverMessage = new MessageJson(msg);


        text = serverMessage.getRaw_message();

        sourceId = serverMessage.getUser_id();
        groupId = serverMessage.getGroup_id();
        name = serverMessage.getNickname();
        if(groupId == ModConfig.GROUP_ID.get()){
            if(text.startsWith("!")){
                Invoke.invokeCommand(text);
            }
            else if(!text.startsWith("[CQ:")){
                String toSend = String.format("§b[§lQQ§r§b]§a<%s>§f %s", name, text);
                TickEventHandler.getToSendQueue().add(toSend);
            }
        }

        //} catch (NullPointerException e) {
        //BotApi.LOGGER.error("接收到非法包", e);
        //e.printStackTrace();
        //}

    }
}
