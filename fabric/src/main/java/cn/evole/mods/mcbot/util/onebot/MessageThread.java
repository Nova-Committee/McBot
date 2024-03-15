package cn.evole.mods.mcbot.util.onebot;

import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.McBot;
import com.google.gson.JsonArray;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Project: McBot-fabric
 * @Author: xia-mc
 * @CreateTime: 2024/2/12 16:11
 * @Description: 笑点解析：
 */
public class MessageThread {
    private final ExecutorService executor;
    public MessageThread() {
        executor = Executors.newCachedThreadPool();
    }

    public void submit(long groupId, String msg, boolean autoEscape) {
        Const.LOGGER.debug(String.format("转发游戏消息: %s", msg));
        executor.submit(() -> McBot.onebot.getBot().sendGroupMsg(groupId, msg, autoEscape));
    }

    public void submit(long groupId, JsonArray msg, boolean autoEscape) {
        Const.LOGGER.debug(String.format("转发游戏消息: %s", msg));
        executor.submit(() -> McBot.onebot.getBot().sendGroupMsg(groupId, msg, autoEscape));
    }

    public void submit(String guildID, String channelID, String message) {
        Const.LOGGER.debug(String.format("转发游戏消息: %s", message));
        executor.submit(() -> McBot.onebot.getBot().sendGuildMsg(guildID, channelID, message));
    }

    public void submit(String guildID, String channelID, JsonArray message) {
        Const.LOGGER.debug(String.format("转发游戏消息: %s", message));
        executor.submit(() -> McBot.onebot.getBot().sendGuildMsg(guildID, channelID, message));
    }

    public void submit(long groupId, Callable<String> msg, boolean autoEscape) {
        executor.submit(() -> {
            try {
                submit(groupId, msg.call(), autoEscape);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void stop() {
        executor.shutdownNow();
    }
}
