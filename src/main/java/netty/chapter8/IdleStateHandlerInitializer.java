package netty.chapter8;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-14
 * Time: 下午5:40
 * To change this template use File | Settings | File Templates.
 */
public class IdleStateHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(
                new IdleStateHandler(0, 0, 60, TimeUnit.SECONDS)); //#1
        pipeline.addLast(new HeartbeatHandler());
    }
    public static final class HeartbeatHandler extends
            ChannelStateHandlerAdapter {
        private static final ByteBuf HEARTBEAT_SEQUENCE =
                Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(
                        "HEARTBEAT", CharsetUtil.ISO_8859_1));// #2
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx,
                                       Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                        .addListener(
                                ChannelFutureListener.CLOSE_ON_FAILURE); //#3
            } else {
                //super.userEventTriggered(ctx, evt); //#4
            }
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
