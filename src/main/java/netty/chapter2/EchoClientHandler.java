package netty.chapter2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-6-30
 * Time: 下午3:28
 * To change this template use File | Settings | File Templates.
 */
@ChannelHandler.Sharable //#1
public class EchoClientHandler extends
        SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.write(Unpooled.copiedBuffer("Netty rocks!",
                CharsetUtil.UTF_8));  //#2
    }
    @Override
    public void channelRead0(ChannelHandlerContext ctx,
                             ByteBuf in) {
        System.out.println("Client received: " + ByteBufUtil
                .hexDump(in.readBytes(in.readableBytes()))); //#4
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, //#5
                                Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
