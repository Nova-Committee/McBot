package cn.evole.mods.mcbot.util.onebot;

import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.IMcBot;
import cn.evole.mods.mcbot.McBot;
import cn.evole.mods.mcbot.command.ConnectCommand;
import cn.evole.mods.mcbot.config.ModConfig;
import cn.evole.onebot.sdk.event.meta.HeartbeatMetaEvent;
import lombok.Getter;
import lombok.val;
import net.minecraft.ChatFormatting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeoutException;

@Getter
public class KeepAlive {
    private final Queue<HeartbeatMetaEvent> heartBeatQueue = new LinkedBlockingQueue<>(5);
//    private long timeoutMillis = 100000;

    public KeepAlive() {
    }

    public void onHeartbeat(@NotNull HeartbeatMetaEvent event) {
        try {
            heartBeatQueue.add(event);
        } catch (IllegalStateException ignored) {
            heartBeatQueue.poll();
            onHeartbeat(event);
        }
    }

    public void register() {
        while (true) {
            val limit = ModConfig.INSTANCE.getBotConfig().getMaxReconnectAttempts();
            if (IMcBot.connected && ModConfig.INSTANCE.getBotConfig().isReconnect() && limit >= 1) {
//                try {
//                    timeoutMillis = getHeartbeat(timeoutMillis + ModConfig.INSTANCE.getBotConfig().getTimeoutCompensation()).getInterval();
//                } catch (TimeoutException e) {
//                    reconnect(limit);
//                }
                if (IMcBot.onebot.getWs().isClosed()) {  // 当你写完复杂的机制后突然发现有现成的api时 be like
                    reconnect(limit);
                }
            }
            try {
                Thread.sleep(ModConfig.INSTANCE.getBotConfig().getTimeoutCompensation());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void reconnect(final int limit) {
        int hasReconnect = 0;
        while (hasReconnect <= limit) {
            Const.LOGGER.info("正在尝试重连...第{}次", hasReconnect + 1);
            ConnectCommand.doConnect();

//            try {
//                getHeartbeat(100000);
//                return;
//            } catch (TimeoutException ignored) {}
            try {
                Thread.sleep(ModConfig.INSTANCE.getBotConfig().getTimeoutCompensation());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (IMcBot.onebot.getWs().isClosed()) hasReconnect++;
            else return;
        }
        Const.sendAllPlayerMsg("▌ " + ChatFormatting.RED + "群服互联意外断开，请联系服务器管理者。");
        IMcBot.onebot.getWs().close();
        IMcBot.connected = false;
    }

    /**
     * 尝试获取心跳包
     * @param timeout 超时时间（毫秒）
     * @return 心跳包
     */
    private HeartbeatMetaEvent getHeartbeat(final long timeout) throws TimeoutException {
        val startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < timeout) {
            @Nullable HeartbeatMetaEvent event = heartBeatQueue.poll();
            if (event == null && timeout > 100) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                return event;
            }
        }
        throw new TimeoutException("未在指定时间内收到心跳包");
    }
}
