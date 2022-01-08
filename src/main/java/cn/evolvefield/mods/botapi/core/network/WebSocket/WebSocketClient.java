package cn.evolvefield.mods.botapi.core.network.WebSocket;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.core.service.ClientThreadService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.util.concurrent.GenericFutureListener;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketClient extends Thread{
    private URI wsURI;
    private String key;
    private final Logger logger = BotApi.LOGGER;

    public String getKey() {
        return key;
    }

    public WebSocketClient(String host, int port, String key) {
        try {
            wsURI = new URI("ws://" + host + ":" + port );
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        }
        this.key = key;
        setUncaughtExceptionHandler((thread, throwable) -> {
            logger.warn("连接出错，将结束客户端线程:", throwable);
            ClientThreadService.stopWebSocketClient();
        });
    }
    @Override
    public void run(){
        EventLoopGroup client = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(client);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new WebSocketChannelInitializer());

            logger.info(String.format("尝试连接webSocket服务器: %s", wsURI));
            ChannelFuture cf = bootstrap.connect(wsURI.getHost(), wsURI.getPort()).sync();
            cf.addListener((GenericFutureListener<ChannelFuture>) channelFuture -> {
                logger.info(String.format("连接webSocket服务器: %s isSuccess=%s", wsURI, channelFuture.isSuccess()));
                if(channelFuture.isSuccess()){
                    //进行握手
                    Channel channel = channelFuture.channel();
                    HttpHeaders httpHeaders = new DefaultHttpHeaders();
                    WebSocketClientHandler handler = (WebSocketClientHandler) channel.pipeline().get("handler");
                    WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(wsURI,
                            WebSocketVersion.V13, null, true, httpHeaders);
                    handler.setHandshaker(handshaker);
                    handshaker.handshake(channel);

                }
            });
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e){
            logger.info("webSocketClient线程中断");
        }
        finally {
            client.shutdownGracefully();
        }
    }
}
