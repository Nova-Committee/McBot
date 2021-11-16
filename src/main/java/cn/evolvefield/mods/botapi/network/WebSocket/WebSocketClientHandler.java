package cn.evolvefield.mods.botapi.network.WebSocket;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.service.ClientThreadService;
import cn.evolvefield.mods.botapi.service.MessageHandlerService;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import org.apache.logging.log4j.Logger;

public class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {
    private WebSocketClientHandshaker handshaker = null;
    private ChannelPromise handshakeFuture = null;
    private final Logger logger = BotApi.LOGGER;

    public void handlerAdded(ChannelHandlerContext ctx) {
        this.handshakeFuture = ctx.newPromise();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        //握手协议返回，设置结束握手
        if (!this.handshaker.isHandshakeComplete()) {
            try {
                FullHttpResponse response = (FullHttpResponse) msg;
                this.handshaker.finishHandshake(ctx.channel(), response);
                this.handshakeFuture.setSuccess();
                logger.info("Go-cqhttp connected!");
            } catch (WebSocketHandshakeException e) {
                logger.error("Go-cqhttp failed to connect, Token authentication failed!");
                Runtime.getRuntime().exit(0);
            }
            return;
        }

        //处理文本请求
        if (msg instanceof TextWebSocketFrame) {

                TextWebSocketFrame textFrame = (TextWebSocketFrame) msg;
                MessageHandlerService.receiveMessage(textFrame.text());

        }
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        WebSocketChannelSupervise.addChannel(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        WebSocketChannelSupervise.removeChannel(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.channel().close();
        ClientThreadService.stopWebSocketClient();
    }

    public void setHandshaker(WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }

    public ChannelFuture handshakeFuture() {
        return this.handshakeFuture;
    }

    public ChannelPromise getHandshakeFuture() {
        return handshakeFuture;
    }

    public void setHandshakeFuture(ChannelPromise handshakeFuture) {
        this.handshakeFuture = handshakeFuture;
    }
}
