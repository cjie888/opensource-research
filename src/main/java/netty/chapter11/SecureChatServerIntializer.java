package netty.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-21
 * Time: 下午3:51
 * To change this template use File | Settings | File Templates.
 */
public class SecureChatServerIntializer extends ChatServerInitializer {// #1
    private final SSLContext context;
    public SecureChatServerIntializer(ChannelGroup group, SSLContext
            context) {
        super(group);
        this.context = context;
    }
    @Override
    protected void initChannel(Channel ch) throws Exception {
        super.initChannel(ch);
        SSLEngine engine = context.createSSLEngine();
        engine.setUseClientMode(false);
        ch.pipeline().addFirst(new SslHandler(engine)); //#2
    }
}
