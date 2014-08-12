package netty.chapter8;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-14
 * Time: 下午5:50
 * To change this template use File | Settings | File Templates.
 */
public class LineBasedHandlerInitializer extends ChannelInitializer<Channel>
{
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LineBasedFrameDecoder(65 * 1024));// #1
        pipeline.addLast(new FrameHandler()); //#2
    }
    public static final class FrameHandler
            extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx,
                                 ByteBuf msg) throws Exception { //#3
// Do something with the frame
        }
    }
}
