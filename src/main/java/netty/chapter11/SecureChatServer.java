package netty.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;

import javax.net.ssl.SSLContext;
import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-21
 * Time: 下午3:53
 * To change this template use File | Settings | File Templates.
 */
public class SecureChatServer extends ChatServer {// #1
    private final SSLContext context;
    public SecureChatServer(SSLContext context) {
        this.context = context;
    }
    @Override
    protected ChannelInitializer<Channel> createInitializer(
            ChannelGroup group) {
        return new SecureChatServerIntializer(group, context); //#2
    }
    public static void main(String[] args)  throws Exception {
        if (args.length != 1) {
            System.err.println("Please give port as argument");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);
        SSLContext context = BogusSslContextFactory.getInstance(true);
        final SecureChatServer endpoint = new SecureChatServer(context);
        ChannelFuture future = endpoint.start(new InetSocketAddress(port));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                endpoint.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }
}
