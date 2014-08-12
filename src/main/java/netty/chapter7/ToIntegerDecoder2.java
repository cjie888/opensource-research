package netty.chapter7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-9
 * Time: 下午2:28
 * To change this template use File | Settings | File Templates.
 */
public class ToIntegerDecoder2 extends ReplayingDecoder<Void> { //#1
    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in,
                       List<Object> out) throws Exception {
        out.add(in.readInt());// #2
    }
}
