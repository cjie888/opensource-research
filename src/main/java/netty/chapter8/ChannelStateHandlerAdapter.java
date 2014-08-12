package netty.chapter8;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-14
 * Time: 下午5:48
 * To change this template use File | Settings | File Templates.
 */
public abstract class ChannelStateHandlerAdapter implements ChannelHandler {
    public abstract void userEventTriggered(ChannelHandlerContext ctx,
                                            Object evt) throws Exception;
}
