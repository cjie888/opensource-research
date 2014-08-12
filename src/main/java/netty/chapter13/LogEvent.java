package netty.chapter13;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-28
 * Time: 下午3:24
 * To change this template use File | Settings | File Templates.
 */
public final class LogEvent {
    public static final byte SEPARATOR = (byte) ':'; //#1
    private final InetSocketAddress source;
    private final String logfile;
    private final String msg;
    private final long received;
    public LogEvent(String logfile, String msg) { //#1
        this(null, -1, logfile, msg);
    }
    public LogEvent(InetSocketAddress source, long received,
                    String logfile, String msg) { //#2
        this.source = source;
        this.logfile = logfile;
        this.msg = msg;
        this.received = received;
    }
    public InetSocketAddress getSource() { //#3
        return source;
    }
    public String getLogfile() {// #4
        return logfile;
    }
    public String getMsg() { //#5
        return msg;
    }
    public long getReceivedTimestamp() { //#6
        return received;
    }
}
