package cn.evole.mods.mcbot.connect;

import cn.evole.mods.mcbot.McBotKoishi;
import cn.evole.mods.mcbot.cmd.BotCmdRun;
import cn.evole.mods.mcbot.model.Request;
import cn.evole.mods.mcbot.model.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringEscapeUtils;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Author cnlimiter
 * CreateTime 2023/7/26 23:56
 * Name WSServer
 * Description
 */

public class WSServer extends WebSocketServer {
    private static final Logger logger = LoggerFactory.getLogger("WSServer");

    public WSServer(){
        super(new InetSocketAddress("127.0.0.1", 9090));
    }
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        logger.info("正向Websocket服务端 / 成功连接");

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        logger.info("正向Websocket服务端 / 连接被关闭");

    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        if (message.startsWith("\"") && message.endsWith("\"")){
           message = message.substring(1, message.length() - 1);
        }
        message = StringEscapeUtils.unescapeJson(message);
        Request request = gson.fromJson(message, Request.class);
        switch (request.getType()){
            case "cmd" -> {
                McBotKoishi.server.getCommands().performPrefixedCommand(BotCmdRun.CUSTOM, request.getData());
                StringBuilder result = new StringBuilder();
                for (String s :  BotCmdRun.CUSTOM.outPut) {
                    result.append(s.replaceAll("§\\S", "")).append("\n");
                }
                BotCmdRun.CUSTOM.outPut.clear();
                Response response = new Response(request.getId(), 0, request.getData(), result.toString());
                conn.send(gson.toJson(response));
            }
            case "msg" -> {}
        }


    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        logger.warn(String.format("正向Websocket服务端 / 出现错误 \n %s",  ex.getMessage()));
    }

    @Override
    public void onStart() {
        logger.info(String.format("正向WebSocket服务端 / 正在监听端口：%s",  9090));
    }
}
