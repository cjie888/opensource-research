package netty.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-18
 * Time: 上午11:21
 * To change this template use File | Settings | File Templates.
 */
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {
    //#1
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= 4) { //#2
            int value = Math.abs(in.readInt()); //#3
            out.add(value); //#4
        }
    }
}
