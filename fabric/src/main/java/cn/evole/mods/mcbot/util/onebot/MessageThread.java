package cn.evole.mods.mcbot.util.onebot;

import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.McBot;
import cn.evole.mods.mcbot.api.McBotChatEvents;
import cn.evole.onebot.sdk.enums.ActionType;
import com.google.gson.JsonObject;
import lombok.val;
import net.minecraft.server.level.ServerPlayer;

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
        Const.LOGGER.debug("转发游戏消息: {}", msg);
        executor.submit(() -> McBot.onebot.getBot().sendGroupMsg(groupId, msg, autoEscape));
    }

    public void submit(long groupId, Callable<String> msg, boolean autoEscape) {
        executor.submit(() -> {
            try {
                val message = msg.call();
                Const.LOGGER.debug("转发游戏消息: {}", message);
                McBot.onebot.getBot().sendGroupMsg(groupId, message, autoEscape);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void submit(long groupId, Callable<String> msg, boolean autoEscape, ServerPlayer player) {
        executor.submit(() -> {
            try {
                val message = msg.call();
                Const.LOGGER.debug("转发游戏消息: {}", message);
                McBotChatEvents.ON_CHAT.invoker().onChat(player,
                        McBot.onebot.getBot().sendGroupMsg(groupId, message, autoEscape).getData().getMessageId()
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void submit(ActionType action, JsonObject params) {
        Const.LOGGER.info("执行自定义操作：{}", action);
        executor.submit(() -> McBot.onebot.getBot().customRequest(action, params));
    }

    public void stop() {
        executor.shutdownNow();
    }
}
