package netty.chapter14;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-29
 * Time: 下午3:20
 * To change this template use File | Settings | File Templates.
 */
public class MemcachedResponse {
    private byte magic;
    private byte opCode;
    private byte dataType;
    private short status;
    private int id;
    private long cas;
    private int flags;
    private int expires;
    private String key;
    private String data;
    public MemcachedResponse(byte magic, byte opCode,
                             byte dataType, short status, int id, long cas,
                             int flags, int expires, String key, String data) {
        this.magic = magic;
        this.opCode = opCode;
        this.dataType = dataType;
        this.status = status;
        this.id = id;this.cas = cas;
        this.flags = flags;
        this.expires = expires;
        this.key = key;
        this.data = data;
    }
    public byte magic() { //#2
        return magic;
    }
    public byte opCode() { //#3
        return opCode;
    }
    public byte dataType() {// #4
        return dataType;
    }
    public short status() { //#5
        return status;
    }
    public int id() { //#6
        return id;
    }
    public long cas() { //#7
        return cas;
    }
    public int flags() { //#8
        return flags;
    }
    public int expires() { //#9
        return expires;
    }
    public String key() { //#10
        return key;
    }
    public String data() { //#11
        return data;
    }
}
