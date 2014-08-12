package netty.chapter12;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-22
 * Time: 下午6:11
 * To change this template use File | Settings | File Templates.
 */
@ChannelHandler.Sharable
public class HttpRequestHandler
        extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    public void channelRead0(ChannelHandlerContext ctx,
                             FullHttpRequest request) throws Exception {// #1
        if (HttpHeaders.is100ContinueExpected(request)) {
            send100Continue(ctx); //#2
        }
        FullHttpResponse response = new DefaultFullHttpResponse(
                request.getProtocolVersion(), HttpResponseStatus.OK); //#3
        response.content().writeBytes(getContent()
                .getBytes(CharsetUtil.UTF_8)); //#4
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE,
                "text/plain; charset=UTF-8"); //#5
        boolean keepAlive = HttpHeaders.isKeepAlive(request);
        if (keepAlive) { //#6
            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,
                    response.content().readableBytes());
            response.headers().set(HttpHeaders.Names.CONNECTION,
                    HttpHeaders.Values.KEEP_ALIVE);
        }
        ChannelFuture future = ctx.writeAndFlush(response); //#7
        if (!keepAlive) {
            future.addListener(ChannelFutureListener.CLOSE); //#8
        }
    }
    protected String getContent() {// #9
        return "This content is transmitted via HTTP\r\n";
    }
    private static void send100Continue(ChannelHandlerContext ctx) {// #10
        FullHttpResponse response = new DefaultFullHttpResponse(
             HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause)throws Exception {// #11
        cause.printStackTrace();
        ctx.close();
    }
}
