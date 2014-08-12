package netty.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-18
 * Time: 上午11:24
 * To change this template use File | Settings | File Templates.
 */
public class FrameChunkDecoder extends ByteToMessageDecoder { //#1
    private final int maxFrameSize;
    public FrameChunkDecoder(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in,
                          List<Object> out) throws Exception {
        int readableBytes = in.readableBytes(); //#2
        if (readableBytes > maxFrameSize) {
// discard the bytes #3
            in.clear();
            throw new TooLongFrameException();
        }
        ByteBuf buf = in.readBytes(readableBytes); //#4
        out.add(buf);// #5
    }
}
