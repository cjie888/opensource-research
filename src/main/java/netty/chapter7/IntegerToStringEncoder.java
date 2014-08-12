package netty.chapter7;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-9
 * Time: 下午2:45
 * To change this template use File | Settings | File Templates.
 */
public class IntegerToStringEncoder extends
        MessageToMessageEncoder<Integer> { //#1
    @Override
    public void encode(ChannelHandlerContext ctx, Integer msg,
                       List<Object>out) throws Exception {
        out.add(String.valueOf(msg)); //#2
    }
}
