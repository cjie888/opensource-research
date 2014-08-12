package netty.chapter8;

import io.netty.channel.*;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-14
 * Time: 下午5:56
 * To change this template use File | Settings | File Templates.
 */
public class ChunkedWriteHandlerInitializer
        extends ChannelInitializer<Channel> {
    private final File file;
    public ChunkedWriteHandlerInitializer(File file) {
        this.file = file;
    }
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ChunkedWriteHandler());// #1
        pipeline.addLast(new WriteStreamHandler()); //#2
    }
    public final class WriteStreamHandler
            extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx)
                throws Exception {
            super.channelActive(ctx);
            ctx.writeAndFlush(
                    new ChunkedStream(new FileInputStream(file))); //#3
        }}
}
