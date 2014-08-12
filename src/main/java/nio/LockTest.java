package nio;

import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.*;

/** * TestOptional locking with FileChannel.
  * Run one copy of this code with arguments "-w /tmp/locktest.dat"
 * and one or more copies with "-r /tmp/locktest.dat" to see the
 * interactions of exclusive and shared locks. Note how too many
 * readers can starve out the writer.
 * Note: The filename you provide will be overwritten. Substitute
 * an appropriate temp filename for your favorite OS.
 ** Created April, 2002 * @author Ron Hitchens (ron@ronsoft.com) */
public class LockTest {

    private static final int SIZEOF_INT = 4;
    private static final int INDEX_START = 0;
    private static final int INDEX_COUNT = 10;
    private static final int INDEX_SIZE = INDEX_COUNT * SIZEOF_INT;
    private ByteBuffer buffer = ByteBuffer.allocate(INDEX_SIZE);
    private IntBuffer indexBuffer = buffer.asIntBuffer( );
    private Random rand = new Random( );
    public static void main (String[] argv) throws Exception
    {
        boolean writer = false;
        String filename;
        if (argv.length != 2) {
            System.out.println ("Usage: [ -r | -w ] filename");
            return;
        }
        writer = argv [0].equals ("-w");
        filename = argv [1];
        RandomAccessFile raf = new RandomAccessFile(filename, (writer) ? "rw" : "r");
        FileChannel fc = raf.getChannel( );
        LockTest lockTest = new LockTest( );
        if (writer) {
            lockTest.doUpdates (fc);
        } else {
            lockTest.doQueries (fc);
        }
    }
    // ----------------------------------------------------------------
    // Simulate a series of read-only queries while
    // holding a shared lock on the index area
    void doQueries (FileChannel fc) throws Exception {
        while (true) {
            println ("trying for shared lock...");
            FileLock lock = fc.lock (INDEX_START, INDEX_SIZE, true);
            int reps = rand.nextInt (60) + 20;
            for (int i = 0; i < reps; i++) {
                int n = rand.nextInt (INDEX_COUNT);
                int position = INDEX_START + (n * SIZEOF_INT);
                buffer.clear( );
                fc.read (buffer, position);
                int value = indexBuffer.get (n);
                println ("Index entry " + n + "=" + value); // Pretend to be doing some work
                Thread.sleep(100);
            }
            lock.release( );
            println ("<sleeping>");
            Thread.sleep(rand.nextInt(3000) + 500);
        }
    }
    // Simulate a series of updates to the index area
    // while holding an exclusive lock
    void doUpdates (FileChannel fc) throws Exception {
        while (true) {
            println ("trying for exclusive lock...");
            FileLock lock = fc.lock (INDEX_START, INDEX_SIZE, false);
            updateIndex (fc); lock.release( );
            println ("<sleeping>");
            Thread.sleep(rand.nextInt(2000) + 500);
        }
    }
    // Write new values to the index slots
    private int idxval = 1;
    private void updateIndex (FileChannel fc) throws Exception {
       // "indexBuffer" is an int view of "buffer"
        indexBuffer.clear( );
        for (int i = 0; i < INDEX_COUNT; i++) {
            idxval++;
            println ("Updating index " + i + "=" + idxval);
            indexBuffer.put (idxval);
            // Pretend that this is really hard work
            Thread.sleep(500);
        }
        // leaves position and limit correct for whole buffer buffer.clear( );
        fc.write (buffer, INDEX_START);
    }
    // ----------------------------------------------------------------
    private int lastLineLen = 0;
    // Specialized println that repaints the current line private
    void println (String msg) {
        System.out.print ("\r ");
        System.out.print (msg);
        for (int i = msg.length( ); i < lastLineLen; i++) {
            System.out.print (" ");
        }
        System.out.print ("\r");
        System.out.flush( );
        lastLineLen = msg.length( );
    }

    /** * Request time service, per RFC 868. RFC 868
    *(http://www.ietf.org/rfc/rfc0868.txt) is a very simple time protocol
     * whereby one system can request the current time from another system.
     * Most Linux, BSD and Solaris systems provide RFC 868 time service
     * on port 37. This simple program will inter-operate with those.
     * The National Institute of Standards and Technology (NIST) operates
     * a public time server at time.nist.gov.
     *
     * **
     * The RFC 868 protocol specifies a 32 bit unsigned value be sent,
     * representing the number of seconds since Jan 1, 1900. The Java
     * epoch begins on Jan 1, 1970 (same as unix) so an adjustment is
     * made by adding or subtracting 2,208,988,800 as appropriate. To
     * avoid shifting and masking, a four-byte slice of an
     * eight-byte buffer is used to send/recieve. But getLong( )
     * is done on the full eight bytes to get a long value.
     *
     * When run, this program will issue time requests to each hostname
     * given on the command line, then enter a loop to receive packets.
     * Note that some requests or replies may be lost, which means
     * this code could block forever. ** @author Ron Hitchens (ron@ronsoft.com)
     */
    public static class TimeClient {
        private static final int DEFAULT_TIME_PORT = 37;
        private static final long DIFF_1900 = 2208988800L;
        protected int port = DEFAULT_TIME_PORT;
        protected List remoteHosts;
        protected DatagramChannel channel;
        public TimeClient (String[] argv) throws Exception {
            if (argv.length == 0) {
                throw new Exception("Usage: [ -p port ] host ...");
            }
            parseArgs (argv);
            this.channel = DatagramChannel.open();
        }
        protected InetSocketAddress receivePacket (DatagramChannel channel, ByteBuffer buffer) throws Exception {
            buffer.clear( );
            // Receive an unsigned 32-bit, big-endian value
            return ((InetSocketAddress) channel.receive (buffer));
        }
        // Send time requests to all the supplied hosts
        protected void sendRequests( ) throws Exception {
            ByteBuffer buffer = ByteBuffer.allocate(1);
            Iterator it = remoteHosts.iterator( );
            while (it.hasNext( )) {
                InetSocketAddress sa = (InetSocketAddress) it.next( );
                System.out.println ("Requesting time from " + sa.getHostName() + ":" + sa.getPort( ));
                 // Make it empty (see RFC868)
                buffer.clear().flip( ); // Fire and forget
                channel.send (buffer, sa);
            }
        }
        // Receive any replies that arrive
        public void getReplies( ) throws Exception {
        // Allocate a buffer to hold a long value
            ByteBuffer longBuffer = ByteBuffer.allocate(8);
            // Assure big-endian (network) byte order
            longBuffer.order (ByteOrder.BIG_ENDIAN);
            // Zero the whole buffer to be sure
            longBuffer.putLong (0, 0);
            // Position to first byte of the low-order 32 bits
            longBuffer.position (4);
            // Slice the buffer; gives view of the low-order 32 bits
            ByteBuffer buffer = longBuffer.slice( );
            int expect = remoteHosts.size( );
            int replies = 0;
            System.out.println ("");
            System.out.println ("Waiting for replies...");
            while (true) {
                InetSocketAddress sa;
                sa = receivePacket (channel, buffer);
                buffer.flip( );
                replies++;
                printTime (longBuffer.getLong (0), sa);
                if (replies == expect) {
                    System.out.println ("All packets answered");
                    break;
                }
                // Some replies haven't shown up yet
                System.out.println ("Received " + replies + " of " + expect + " replies");
            }
        }
        // Print info about a received time reply
        protected void printTime (long remote1900, InetSocketAddress sa) {
            // local time as seconds since Jan 1, 1970
            long local = System.currentTimeMillis() / 1000;
            // remote time as seconds since Jan 1, 1970
            long remote = remote1900 - DIFF_1900;
            Date remoteDate = new Date(remote * 1000);
            Date localDate = new Date(local * 1000);
            long skew = remote - local;
            System.out.println ("Reply from " + sa.getHostName() + ":" + sa.getPort( ));
            System.out.println (" there: " + remoteDate);
            System.out.println (" here: " + localDate);
            System.out.print (" skew: ");
            if (skew == 0) {
                System.out.println ("none");
            } else if (skew > 0) {
                System.out.println (skew + " seconds ahead");
            }  else {
                System.out.println ((-skew) + " seconds behind");
            }
        }
        protected void parseArgs (String[] argv) {
            remoteHosts = new LinkedList( );
            for (int i = 0; i < argv.length; i++) {
                String arg = argv [i];
                // Send client requests to the given port
                if (arg.equals ("-p")) {
                    i++;
                    this.port = Integer.parseInt(argv[i]);
                    continue;
                }
                // Create an address object for the hostname
                InetSocketAddress sa = new InetSocketAddress(arg, port);
                // Validate that it has an address
                if (sa.getAddress( ) == null) {
                    System.out.println ("Cannot resolve address: " + arg);
                    continue;
                }
                remoteHosts.add (sa);
            }
        }
    }
}
