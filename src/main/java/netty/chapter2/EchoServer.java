package netty.chapter2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-6-30
 * Time: 下午2:49
 * To change this template use File | Settings | File Templates.
 */
public class EchoServer {
    private final int port;
    public EchoServer(int port) {
        this.port = port;
    }
    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();  //#1
            b.group(group) // #2
                    .channel(NioServerSocketChannel.class) // #2
                    .localAddress(new InetSocketAddress(port)) // #2
                    .childHandler(new ChannelInitializer<SocketChannel>() { //#3
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(
                                    new EchoServerHandler()); //#4
                        }
                    });
            ChannelFuture f = b.bind().sync(); //#5
            System.out.println(EchoServer.class.getName() + //#6
                    " started and listen on " + f.channel().localAddress());//#7
            f.channel().closeFuture().sync(); //#8
        } finally { //#9
            group.shutdownGracefully().sync(); //#10  }
        }
    }
    public static void main(String[] args) throws Exception {
        if (args.length !=1) {
            System.err.println(
                    "Usage: " + EchoServer.class.getSimpleName() +
                            " <port>");
        }
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }
}
