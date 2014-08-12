package netty.chapter4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-2
 * Time: 上午9:58
 * To change this template use File | Settings | File Templates.
 */
public class PlainNioServer {
    public void serve(int port) throws IOException {
        System.out.println("Listening for connections on port " + port);
        ServerSocketChannel serverChannel;
        Selector selector;
        serverChannel = ServerSocketChannel.open();
        ServerSocket ss = serverChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        ss.bind(address); //#1
        serverChannel.configureBlocking(false);
        selector = Selector.open(); //#2
        serverChannel.register(selector, SelectionKey.OP_ACCEPT); //#3
        final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());
        while (true) {
            try {
                selector.select(); //#4
            } catch (IOException ex) {
                ex.printStackTrace();
// handle in a proper way
                break;
            }
            Set<SelectionKey> readyKeys = selector.selectedKeys(); //#5
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if (key.isAcceptable()) { //#6
                        ServerSocketChannel server = (ServerSocketChannel)
                                key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " +
                                client);
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_WRITE |
                                SelectionKey.OP_READ, msg.duplicate());// #7
                    }if (key.isWritable()) {// #8
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        while (buffer.hasRemaining()) {
                            if (client.write(buffer) == 0) { //#9
                                break;
                            }
                        }
                        client.close();// #10
                    }
                } catch (IOException ex) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException cex) {
                    }
                }
            }
        }
    }
}
