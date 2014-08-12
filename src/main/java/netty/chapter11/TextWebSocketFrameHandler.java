package netty.chapter11;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-21
 * Time: 下午2:38
 * To change this template use File | Settings | File Templates.
 */
public class TextWebSocketFrameHandler extends
        SimpleChannelInboundHandler<TextWebSocketFrame> { //#1
    private final ChannelGroup group;
    public TextWebSocketFrameHandler(ChannelGroup group) {
        this.group = group;
    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx,
                                   Object evt) throws Exception {// #2
        if (evt == WebSocketServerProtocolHandler
                .ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            ctx.pipeline().remove(HttpRequestHandler.class);// #3
            group.writeAndFlush(new TextWebSocketFrame("Client " +
                    ctx.channel() + " joined")); //#4
            group.add(ctx.channel()); //#5
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
    @Override
    public void channelRead0(ChannelHandlerContext ctx,
                             TextWebSocketFrame msg) throws Exception {
        group.writeAndFlush(msg.retain()); //#6
    }
}
