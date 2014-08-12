package netty.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-18
 * Time: 上午11:19
 * To change this template use File | Settings | File Templates.
 */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder { //#1
    private final int frameLength;
    public FixedLengthFrameDecoder(int frameLength) { //#2
        if (frameLength <= 0) {
            throw new IllegalArgumentException(
                    "frameLength must be a positive integer: " +
                            frameLength);
        }
        this.frameLength = frameLength;
    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object>
            out) throws Exception {
        while(in.readableBytes() >= frameLength) { //#3
            ByteBuf buf = in.readBytes(frameLength);// #4
            out.add(buf); //#5
        }
    }
}
