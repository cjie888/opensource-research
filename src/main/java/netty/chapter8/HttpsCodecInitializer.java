package netty.chapter8;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-14
 * Time: 下午5:33
 * To change this template use File | Settings | File Templates.
 */
public class HttpsCodecInitializer extends ChannelInitializer<Channel> {
    private final SSLContext context;
    private final boolean client;
    public HttpsCodecInitializer(SSLContext context, boolean client) {
        this.context = context;
        this.client = client;
    }
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        SSLEngine engine = context.createSSLEngine();
        engine.setUseClientMode(client);
        pipeline.addFirst("ssl", new SslHandler(engine)); //#1
        if (client) {
            pipeline.addLast("codec", new HttpClientCodec());// #2
        } else {
            pipeline.addLast("codec", new HttpServerCodec()); //#3
        }
    }
}
