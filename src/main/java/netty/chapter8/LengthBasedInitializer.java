package netty.chapter8;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-14
 * Time: 下午5:54
 * To change this template use File | Settings | File Templates.
 */
public class LengthBasedInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(
                new LengthFieldBasedFrameDecoder(65 * 1024, 0, 8)); //#1
        pipeline.addLast(new FrameHandler());// #2
    }
    public static final class FrameHandler
            extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx,
                                 ByteBuf msg) throws Exception {
// Do something with the frame #3
        }
    }
}