package netty.chapter8;

import com.google.protobuf.MessageLite;
import io.netty.channel.*;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-14
 * Time: 下午5:59
 * To change this template use File | Settings | File Templates.
 */
public class ProtoBufInitializer extends ChannelInitializer<Channel> {
    private final MessageLite lite;
    public ProtoBufInitializer(MessageLite lite) {
        this.lite = lite;
    }
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder()); //#1
        pipeline.addLast(new ProtobufEncoder()); //#2
        pipeline.addLast(new ProtobufDecoder(lite)); //#3
        pipeline.addLast(new ObjectHandler()); //#4
    }
    public static final class ObjectHandler
            extends SimpleChannelInboundHandler<Object> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx, Object msg)
                throws Exception {
// Do something with the object
        }
    }
}
