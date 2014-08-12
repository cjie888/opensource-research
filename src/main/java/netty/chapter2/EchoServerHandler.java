package netty.chapter2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-6-30
 * Time: 下午3:03
 * To change this template use File | Settings | File Templates.
 */
@ChannelHandler.Sharable //#1
public class EchoServerHandler extends
        ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("Server received: " + msg);
        ctx.write(msg); //#2
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE); //#3
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,   Throwable cause) {
        cause.printStackTrace();   //#4
        ctx.close();// #5
    }
}
