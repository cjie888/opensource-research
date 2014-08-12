package netty.chapter8;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-14
 * Time: 下午5:10
 * To change this template use File | Settings | File Templates.
 */
public class SslChannelInitializer extends ChannelInitializer<Channel> {
    private final SSLContext context;
    private final boolean client;
    private final boolean startTls;
    public SslChannelInitializer(SSLContext context, boolean client,
                                 boolean startTls) {// #1
        this.context = context;
        this.client = client;this.startTls = startTls;
    }
    @Override
    protected void initChannel(Channel ch) throws Exception {
        SSLEngine engine = context.createSSLEngine(); //#2
        engine.setUseClientMode(client); //#3
        ch.pipeline().addFirst("ssl",
                new SslHandler(engine, startTls)); //#4
    }
}
