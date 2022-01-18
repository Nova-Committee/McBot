package cn.evolvefield.mods.botapi.core.service;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.core.network.WebSocket.WebSocketClient;

public class ClientThreadService {
    public static WebSocketClient client;
    public static void runWebSocketClient() {
        int delay = 0;
        if (client != null) {
            client.interrupt();
        }
        client = new WebSocketClient(
                BotApi.config.getCommon().getWsHost(),
                BotApi.config.getCommon().getWsPort(),
                BotApi.config.getCommon().getWsKey());
        client.start();
    }

    public static void runWebSocketClient(String host, int port, String key) {
        int delay = 0;
        if (client != null) {
            client.interrupt();
        }
        client = new WebSocketClient(
                host,
                port,
                key);
        client.start();
    }
    /**
     * @return {@code true}: 已存在客户端; {@code false}: 不存在客户端
     * */
    public static boolean stopWebSocketClient() {
        boolean isStopSuccessfully = false;
        if (client != null) {
            client.interrupt();
            isStopSuccessfully = true;
        }
        client = null;
        return isStopSuccessfully;
    }
}
