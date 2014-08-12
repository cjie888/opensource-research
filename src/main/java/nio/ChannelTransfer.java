package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/** * TestOptional channel transfer. This is a very simplistic concatenation
 * program. It takes a list of file names as arguments, opens each
 * in turn and transfers (copies) their content to the given
 * WritableByteChannel (in this case, stdout).
 *
 * Created April 2002 */
public class ChannelTransfer {
    public static void main (String[] argv) throws Exception {
        if (argv.length == 0) {
            System.err.println ("Usage: filename ...");
            return;
        }
        catFiles (Channels.newChannel (System.out), argv);
    }
    // Concatenate the content of each of the named files to
    // the given channel. A very dumb version of 'cat'.
    private static void catFiles (WritableByteChannel target, String[] files) throws Exception {
        for (int i = 0; i < files.length; i++) {
            FileInputStream fis = new FileInputStream(files [i]);
            FileChannel channel = fis.getChannel( );
            channel.transferTo (0, channel.size( ), target);
            channel.close( );
            fis.close( );
        }
    }

    /** * Dummy HTTP server using MappedByteBuffers.
     * Given a filename on the command line, pretend to be
     * a web server and generate an HTTP response containing
     * the file content preceded by appropriate headers. The
     * data is sent with a gathering write.
     ** @author Ron Hitchens (ron@ronsoft.com) */
    public static class MappedHttp {
        private static final String OUTPUT_FILE = "MappedHttp.out";
        private static final String LINE_SEP = "\r\n";
        private static final String SERVER_ID = "Server: Ronsoft Dummy Server";
        private static final String HTTP_HDR = "HTTP/1.0 200 OK" + LINE_SEP + SERVER_ID + LINE_SEP;
        private static final String HTTP_404_HDR = "HTTP/1.0 404 Not Found" + LINE_SEP + SERVER_ID + LINE_SEP;
        private static final String MSG_404 = "Could not open file: ";
        public static void main (String[] argv) throws Exception {
            if (argv.length < 1) {
                System.err.println ("Usage: filename");
                return;
            }
            String file = argv [0];
            ByteBuffer header = ByteBuffer.wrap(bytes(HTTP_HDR));
            ByteBuffer dynhdrs = ByteBuffer.allocate(128);
            ByteBuffer[] gather = { header, dynhdrs, null };
            String contentType = "unknown/unknown";
            long contentLength = -1;
            try {
                FileInputStream fis = new FileInputStream(file);
                FileChannel fc = fis.getChannel( );
                MappedByteBuffer filedata = fc.map (FileChannel.MapMode.READ_ONLY, 0, fc.size( ));
                gather [2] = filedata;
                contentLength = fc.size( );
                contentType = URLConnection.guessContentTypeFromName(file);
            } catch (IOException e) {
                // file could not be opened; report problem
                ByteBuffer buf = ByteBuffer.allocate(128);
                String msg = MSG_404 + e + LINE_SEP;
                buf.put (bytes (msg));
                buf.flip( );
                // Use the HTTP error response
                gather [0] = ByteBuffer.wrap(bytes(HTTP_404_HDR));
                gather [2] = buf; contentLength = msg.length( );
                contentType = "text/plain";
            }
            StringBuffer sb = new StringBuffer( );
            sb.append ("Content-Length: " + contentLength);
            sb.append (LINE_SEP);
            sb.append ("Content-Type: ").append (contentType);
            sb.append (LINE_SEP).append (LINE_SEP);
            dynhdrs.put (bytes (sb.toString( )));
            dynhdrs.flip( );
            FileOutputStream fos = new FileOutputStream(OUTPUT_FILE);
            FileChannel out = fos.getChannel( );
            // All the buffers have been prepared; write 'em out
            while (out.write (gather) > 0) {
            // Empty body; loop until all buffers are empty
            }
            out.close( );
            System.out.println ("output written to " + OUTPUT_FILE);
        }
        // Convert a string to its constituent bytes
        // from the ASCII character set
        private static byte [] bytes (String string) throws Exception {
            return (string.getBytes ("US-ASCII"));
        }
    }

    /** * Demonstrate gathering write using many buffers.
     ** @author Ron Hitchens (ron@ronsoft.com) */
    public static class Marketing {
        private static final String DEMOGRAPHIC = "blahblah.txt";
        // "Leverage frictionless methodologies"
        public static void main (String[] argv) throws Exception {
            int reps = 10;
            if (argv.length > 0) {
                reps = Integer.parseInt(argv[0]);
            }
            FileOutputStream fos = new FileOutputStream(DEMOGRAPHIC);
            GatheringByteChannel gatherChannel = fos.getChannel( );
            // Generate some brilliant marcom, er, repurposed content
            ByteBuffer[] bs = utterBS (reps);
            // Deliver the message to the waiting market
            while (gatherChannel.write (bs) > 0) {
            // Empty body // Loop until write( ) returns zero
            }
            System.out.println ("Mindshare paradigms synergized to " + DEMOGRAPHIC);
            fos.close( ); }
        // ------------------------------------------------
        // These are just representative; add your own
        private static String[] col1 = { "Aggregate", "Enable", "Leverage", "Facilitate", "Synergize", "Repurpose", "Strategize", "Reinvent", "Harness" };
        private static String[] col2 = { "cross-platform", "best-of-breed", "frictionless", "ubiquitous", "extensible", "compelling", "mission-critical", "collaborative", "integrated" };
        private static String[] col3 = { "methodologies", "infomediaries", "platforms", "schemas", "mindshare", "paradigms", "functionalities", "web services", "infrastructures" };
        private static String newline = System.getProperty("line.separator");
        // The Marcom-atic 9000
        private static ByteBuffer[] utterBS (int howMany) throws Exception {
            List list = new LinkedList( );
            for (int i = 0; i < howMany; i++) {
                list.add (pickRandom (col1, " "));
                list.add (pickRandom (col2, " "));
                list.add (pickRandom (col3, newline));
            }
            ByteBuffer[] bufs = new ByteBuffer[list.size( )];
            list.toArray (bufs);
            return (bufs);
        }
        // The communications director
        private static Random rand = new Random( );
         // Pick one, make a buffer to hold it and the suffix, load it with
    // the byte equivalent of the strings (will not work properly for
    // non-Latin characters), then flip the loaded buffer so it's ready
    // to be drained
       private static ByteBuffer pickRandom (String[] strings, String suffix) throws Exception
       {
           String string = strings [rand.nextInt (strings.length)];
           int total = string.length() + suffix.length( );
           ByteBuffer buf = ByteBuffer.allocate(total);
           buf.put (string.getBytes ("US-ASCII"));
           buf.put (suffix.getBytes ("US-ASCII"));
           buf.flip( ); return (buf);
       }
    }

    /** * TestOptional Pipe objects using a worker thread.
     ** Created April, 2002 * @author Ron Hitchens (ron@ronsoft.com) */
    public static class PipeTest {
        public static void main (String[] argv) throws Exception {
            // Wrap a channel around stdout
            WritableByteChannel out = Channels.newChannel (System.out);
            // Start worker and get read end of channel
            ReadableByteChannel workerChannel = startWorker (10);
            ByteBuffer buffer = ByteBuffer.allocate(100);
            while (workerChannel.read (buffer) >= 0) {
                buffer.flip( );
                out.write (buffer);
                buffer.clear( );
            }
        }
        // This method could return a SocketChannel or
        // FileChannel instance just as easily
        private static ReadableByteChannel startWorker (int reps) throws Exception {
            Pipe pipe = Pipe.open();
            Worker worker = new Worker(pipe.sink( ), reps);
            worker.start( );
            return (pipe.source( ));
        }
        // -----------------------------------------------------------------
        /* * A worker thread object which writes data down a channel.
        * Note: this object knows nothing about Pipe, uses only a
        * generic WritableByteChannel. */
        private static class Worker extends Thread {
            WritableByteChannel channel;
            private int reps;
            Worker (WritableByteChannel channel, int reps) {
                this.channel = channel;
                this.reps = reps;
            }
            // Thread execution begins here
            public void run( ) {
                ByteBuffer buffer = ByteBuffer.allocate(100);
                try {
                    for (int i = 0; i < this.reps; i++) {
                        doSomeWork (buffer);
                        // channel may not take it all at once
                        while (channel.write (buffer) > 0) {
                        // empty
                        }
                    }
                    this.channel.close( );
                } catch (Exception e) {
                // easy way out; this is demo code
                // e.printStackTrace( );
                }
            }
            private String[] products = { "No good deed goes unpunished", "To be, or what?",
                    "No matter where you go, there you are",
                    "Just say \"Yo\"", "My karma ran over my dogma" };
            private Random rand = new Random( );
            private void doSomeWork (ByteBuffer buffer) {
                int product = rand.nextInt (products.length);
                buffer.clear( ); buffer.put (products [product].getBytes( ));
                buffer.put ("\r\n".getBytes( ));
                buffer.flip( );
            }
        }


    }
}
