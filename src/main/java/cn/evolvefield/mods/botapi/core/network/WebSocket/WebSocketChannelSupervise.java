package cn.evolvefield.mods.botapi.core.network.WebSocket;


import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;


public class WebSocketChannelSupervise {
    private static final ChannelGroup GlobalGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static void addChannel(Channel channel) {
        GlobalGroup.add(channel);
    }
    public static void removeChannel(Channel channel) {
        GlobalGroup.remove(channel);
    }


    //发送消息
    public static void sendToAll(TextWebSocketFrame tws) {
        GlobalGroup.flushAndWrite(tws);
    }
}
