package netty.chapter7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-9
 * Time: 下午2:42
 * To change this template use File | Settings | File Templates.
 */
public class IntegerToByteEncoder extends
        MessageToByteEncoder<Short> { //#1
    @Override
    public void encode(ChannelHandlerContext ctx, Short msg, ByteBuf out)
            throws Exception {
        out.writeShort(msg); //#2
    }
}
