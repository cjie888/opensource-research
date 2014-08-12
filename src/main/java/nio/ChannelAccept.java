package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/** * TestOptional nonblocking accept( ) using ServerSocketChannel.
 *  Start this program, then "telnet localhost 1234" to
 *  connect to it. ** @author Ron Hitchens (ron@ronsoft.com) */
public class ChannelAccept {
    public static final String GREETING = "Hello I must be going.\r\n";
    public static void main (String[] argv) throws Exception {
        int port = 1234; // default
        if (argv.length > 0) {
            port = Integer.parseInt(argv[0]);
        }
        ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket( ).bind (new InetSocketAddress(port));
        ssc.configureBlocking (false);
        while (true) {
            System.out.println ("Waiting for connections");
            SocketChannel sc = ssc.accept( );
            if (sc == null) {
            // no connections, snooze a while
                Thread.sleep(2000);
            } else {
                System.out.println ("Incoming connection from: " + sc.socket().getRemoteSocketAddress( ));
                buffer.rewind( );
                sc.write (buffer);
                sc.close( );
            }
        }
    }
}
