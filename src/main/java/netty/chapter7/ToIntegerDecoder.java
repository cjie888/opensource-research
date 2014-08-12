package netty.chapter7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-9
 * Time: 下午2:25
 * To change this template use File | Settings | File Templates.
 */
public class ToIntegerDecoder extends ByteToMessageDecoder { //#1
    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in,
                       List<Object> out) throws Exception {
        if (in.readableBytes() >= 4) { //#2
            out.add(in.readInt()); //#3
        }
    }
}
