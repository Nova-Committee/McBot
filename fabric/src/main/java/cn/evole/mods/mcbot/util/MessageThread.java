package cn.evole.mods.mcbot.util;

import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.McBot;
import com.google.gson.JsonArray;

/**
 * @Project: McBot-fabric
 * @Author: xia-mc
 * @CreateTime: 2024/2/12 16:11
 * @Description:
 */
public class MessageThread extends Thread {
    private long groupIDInt;
    private String guildIDString;
    private String messageString;
    private JsonArray messageArray;
    private boolean autoEscape;
    private String channelIDString;
    private final short mode;

    public MessageThread(long groupId, String msg, boolean autoEscape) {
        this.mode = 0;
        this.groupIDInt = groupId;
        this.messageString = msg;
        this.autoEscape = autoEscape;
    }

    public MessageThread(long groupId, JsonArray msg, boolean autoEscape) {
        this.mode = 1;
        this.groupIDInt = groupId;
        this.messageArray = msg;
        this.autoEscape = autoEscape;
    }

    public MessageThread(String guildID, String channelID, String message) {
        this.mode = 2;
        this.guildIDString = guildID;
        this.channelIDString = channelID;
        this.messageString = message;
    }

    public MessageThread(String guildID, String channelID, JsonArray message) {
        this.mode = 3;
        this.guildIDString = guildID;
        this.channelIDString = channelID;
        this.messageArray = message;
    }

    public void run() {
        switch (mode) {
            case 0 :{McBot.bot.sendGroupMsg(groupIDInt, messageString, autoEscape); break;}
            case 1 :{McBot.bot.sendGroupMsg(groupIDInt, messageArray, autoEscape); break;}
            case 2 :{McBot.bot.sendGuildMsg(guildIDString, channelIDString, messageString); break;}
            case 3 :{McBot.bot.sendGuildMsg(guildIDString, channelIDString, messageArray); break;}
        }
    }

    public void start() {
        Const.LOGGER.info(String.format("转发游戏消息: %s", messageString != null ? messageString : messageArray));
        Thread thread = new Thread(this, "MessageThread");
        thread.start();
    }
}
