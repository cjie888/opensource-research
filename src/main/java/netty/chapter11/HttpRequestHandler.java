package netty.chapter11;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.RandomAccessFile;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-21
 * Time: 下午2:34
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequestHandler
        extends SimpleChannelInboundHandler<FullHttpRequest> { //#1
    private final String wsUri;
    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }
    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest
            request) throws Exception {
        if (wsUri.equalsIgnoreCase(request.getUri())) {
            ctx.fireChannelRead(request.retain()); //#2
        } else {
            if (HttpHeaders.is100ContinueExpected(request)) {
                send100Continue(ctx);// #3
            }
            RandomAccessFile file = new
                    RandomAccessFile("/path/to/index.html", "r"); //#4
            HttpResponse response = new DefaultHttpResponse(
                    request.getProtocolVersion(), HttpResponseStatus.OK);
            response.headers().set(
                    HttpHeaders.Names.CONTENT_TYPE,
                    "text/plain; charset=UTF-8");
            boolean keepAlive = HttpHeaders.isKeepAlive(request);
            if (keepAlive) { //#5
                response.headers().set(
                        HttpHeaders.Names.CONTENT_LENGTH, file.length());
                response.headers().set(
                        HttpHeaders.Names.CONNECTION,
                        HttpHeaders.Values.KEEP_ALIVE);
            }
            ctx.write(response); //#6
            if (ctx.pipeline().get(SslHandler.class) == null) { //#7
                ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
            } else {
                ctx.write(new ChunkedNioFile(file.getChannel()));
            }
            ChannelFuture future = ctx.writeAndFlush(
                    LastHttpContent.EMPTY_LAST_CONTENT); //#8
            if (!keepAlive) {
                future.addListener(ChannelFutureListener.CLOSE); //#9
            }
        }
    }
    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
