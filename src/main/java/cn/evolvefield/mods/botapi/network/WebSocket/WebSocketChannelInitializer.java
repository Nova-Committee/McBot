package cn.evolvefield.mods.botapi.network.WebSocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("http-codec", new HttpClientCodec());
        pipeline.addLast("aggregator", new HttpObjectAggregator(1024 * 10));
        pipeline.addLast("handler", new WebSocketClientHandler());
    }
}
