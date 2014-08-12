package netty.chapter12;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.chapter11.BogusSslContextFactory;

import javax.net.ssl.SSLContext;
import java.net.InetSocketAddress;
import java.security.GeneralSecurityException;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-22
 * Time: 下午6:17
 * To change this template use File | Settings | File Templates.
 */
public class SpdyServer {
    private final NioEventLoopGroup group = new NioEventLoopGroup();// #1
    private final SSLContext context;
    private Channel channel;
    public SpdyServer(SSLContext context) { //#2
        this.context = context;
    }
    public ChannelFuture start(InetSocketAddress address) {
        ServerBootstrap bootstrap = new ServerBootstrap(); //#3
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new SpdyChannelInitializer(context));// #4
        ChannelFuture future = bootstrap.bind(address); //#5
        future.syncUninterruptibly();
        channel = future.channel();
        return future;
    }
    public void destroy() {// #6
        if (channel != null) {
            channel.close();
        }
        group.shutdownGracefully();
    }
    public static void main(String[] args) throws GeneralSecurityException {
        if (args.length != 1) {
            System.err.println("Please give port as argument");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);
        SSLContext context = BogusSslContextFactory.getInstance(true); //#7
        final SpdyServer endpoint = new SpdyServer(context);
        ChannelFuture future = endpoint.start(new InetSocketAddress(port));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                endpoint.destroy();
            }});
        future.channel().closeFuture().syncUninterruptibly();
    }
}
