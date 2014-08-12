package netty.chapter13;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-28
 * Time: 下午3:26
 * To change this template use File | Settings | File Templates.
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {
    private final InetSocketAddress remoteAddress;
    public LogEventEncoder(InetSocketAddress remoteAddress) { //#1
        this.remoteAddress = remoteAddress;
    }
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          LogEvent logEvent, List<Object> out)
            throws Exception {
        ByteBuf buf = channelHandlerContext.alloc().buffer();
        buf.writeBytes(logEvent.getLogfile()
                .getBytes(CharsetUtil.UTF_8));//#2
        buf.writeByte(LogEvent.SEPARATOR); //#3
        buf.writeBytes(logEvent.getMsg().getBytes(CharsetUtil.UTF_8)); //#4
        out.add(new DatagramPacket(buf, remoteAddress)); //#5
    }
}
