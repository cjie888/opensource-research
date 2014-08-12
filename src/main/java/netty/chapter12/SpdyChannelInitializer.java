package netty.chapter12;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;
import org.eclipse.jetty.npn.NextProtoNego;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-22
 * Time: 下午6:16
 * To change this template use File | Settings | File Templates.
 */
public class SpdyChannelInitializer extends
        ChannelInitializer<SocketChannel> { //#1
    private final SSLContext context;
    public SpdyChannelInitializer(SSLContext context) {// #2
        this.context = context;
    }
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        SSLEngine engine = context.createSSLEngine(); //#3
        engine.setUseClientMode(false); //#4
        NextProtoNego.put(engine, new DefaultServerProvider());// #5
        NextProtoNego.debug = true;
        pipeline.addLast("sslHandler", new SslHandler(engine));// #6
        pipeline.addLast("chooser",
                new DefaultSpdyOrHttpChooser(1024 * 1024, 1024 * 1024));//#7
    }
}

