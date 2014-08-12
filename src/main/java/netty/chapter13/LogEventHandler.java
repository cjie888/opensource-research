package netty.chapter13;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-28
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.
 */
public class LogEventHandler
        extends SimpleChannelInboundHandler<LogEvent> {// #1
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) throws Exception {
        cause.printStackTrace(); //#2
        ctx.close();
    }
    @Override
    public void channelRead0(ChannelHandlerContext ctx,
                             LogEvent event) throws Exception {
        StringBuilder builder = new StringBuilder();// #3
        builder.append(event.getReceivedTimestamp());
        builder.append(" [");
        builder.append(event.getSource().toString());
        builder.append("] [");
        builder.append(event.getLogfile());
        builder.append("] : ");
        builder.append(event.getMsg());
        System.out.println(builder.toString()); //#4
    }
}
