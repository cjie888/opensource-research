package netty.chapter6;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-8
 * Time: 下午3:36
 * To change this template use File | Settings | File Templates.
 */
public class WriteHandler extends ChannelHandlerAdapter {

    private ChannelHandlerContext ctx;
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        this.ctx = ctx; //#A
    }
    public void send(String msg) { //#B
        ctx.write(msg);
    }
}
