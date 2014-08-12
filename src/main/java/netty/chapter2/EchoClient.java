package netty.chapter2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-6-30
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
public class EchoClient {
    private final String host;
    private final int port;
    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap(); //#1
            b.group(group) //#2
                    .channel(NioSocketChannel.class) //#3
                    .remoteAddress(new InetSocketAddress(host, port)) //#4
                    .handler(new ChannelInitializer<SocketChannel>() { //#5
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(
                                    new EchoClientHandler()); //#6
                        }
                    });
            ChannelFuture f = b.connect().sync(); //#7
            f.channel().closeFuture().sync(); //#8
        } finally {
            group.shutdownGracefully().sync(); //#9
        }
    }
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println(
                    "Usage: " + EchoClient.class.getSimpleName() +
                            " <host> <port>");
            return;
        }
// Parse options.
        final String host = args[0];
        final int port = Integer.parseInt(args[1]);new EchoClient(host, port).start();
    }
}
