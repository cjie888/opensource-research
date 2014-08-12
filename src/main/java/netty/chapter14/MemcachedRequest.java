package netty.chapter14;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-29
 * Time: 下午2:22
 * To change this template use File | Settings | File Templates.
 */
public class MemcachedRequest {
    private static final Random rand = new Random();
    private int magic = 0x80;//fixed so hard coded
    private byte opCode; //the operation e.g. set or get
    private String key; //the key to delete, get or set
    private int flags = 0xdeadbeef; //random
    private int expires; //0 = item never expires
    private String body; //if opCode is set, the value
    private int id = rand.nextInt(); //Opaque
    private long cas; //data version check...not used
    private boolean hasExtras; //not all ops have extras
    public MemcachedRequest(byte opcode, String key, String value) {
        this.opCode = opcode;
        this.key = key;
        this.body = value == null ? "" : value;
//only set command has extras in our example
        hasExtras = opcode == Opcode.SET;
    }
    public MemcachedRequest(byte opCode, String key) {
        this(opCode, key, null);
    }
    public int magic() { //#2
        return magic;
    }
    public int opCode() { //#3
        return opCode;
    }
    public String key() { //#4
        return key;
    }
    public int flags() { //#5
        return flags;
    }
    public int expires() {// #6
        return expires;
    }
    public String body() { //#7
        return body;
    }
    public int id() { //#8
        return id;
    }
    public long cas() {// #9
        return cas;
    }
    public boolean hasExtras() {// #10
        return hasExtras;
    }
}
