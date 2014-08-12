package netty.chapter13;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-28
 * Time: 下午3:34
 * To change this template use File | Settings | File Templates.
 */
public class LogEventMonitor {
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    public LogEventMonitor(InetSocketAddress address) {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel)
                            throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new LogEventDecoder());
                        pipeline.addLast(new LogEventHandler());
                    }
                }).localAddress(address); //#1
    }
    public Channel bind() {
        return bootstrap.bind().syncUninterruptibly().channel(); //#2
    }
    public void stop() {
        group.shutdown();
    }
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException(
                    "Usage: LogEventMonitor <port>");
        }
        LogEventMonitor monitor = new LogEventMonitor(
                new InetSocketAddress(Integer.parseInt(args[0])));// #3
        try {
            Channel channel = monitor.bind();
            System.out.println("LogEventMonitor running");
            channel.closeFuture().await();
        } finally {
            monitor.stop();
        }
    }
}
