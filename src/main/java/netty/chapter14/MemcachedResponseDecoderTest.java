package netty.chapter14;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-29
 * Time: 下午6:39
 * To change this template use File | Settings | File Templates.
 */
public class MemcachedResponseDecoderTest {
    @Test
    public void testMemcachedResponseDecoder() {
        EmbeddedChannel channel = new EmbeddedChannel(
                new MemcachedResponseDecoder()); //#1
        byte magic = 1;
        byte opCode = Opcode.SET;
        byte dataType = 0;
        byte[] key = "Key1".getBytes(CharsetUtil.US_ASCII);
        byte[] body = "Value".getBytes(CharsetUtil.US_ASCII);
        int id = (int) System.currentTimeMillis();
        long cas = System.currentTimeMillis();
        ByteBuf buffer = Unpooled.buffer(); //#2
        buffer.writeByte(magic);
        buffer.writeByte(opCode);
        buffer.writeShort(key.length);
        buffer.writeByte(0);
        buffer.writeByte(dataType);
        buffer.writeShort(Status.KEY_EXISTS);
        buffer.writeInt(body.length + key.length);
        buffer.writeInt(id);buffer.writeLong(cas);
        buffer.writeBytes(key);
        buffer.writeBytes(body);
        Assert.assertTrue(channel.writeInbound(buffer));// #3
        MemcachedResponse response = (MemcachedResponse)
                channel.readInbound();
        assertResponse(response, magic, opCode, dataType,
                Status.KEY_EXISTS, 0, 0, id, cas, key, body);// #4
    }
    @Test
    public void testMemcachedResponseDecoderFragments() {
        EmbeddedChannel channel = new EmbeddedChannel(
                new MemcachedResponseDecoder());// #5
        byte magic = 1;
        byte opCode = Opcode.SET;
        byte dataType = 0;
        byte[] key = "Key1".getBytes(CharsetUtil.US_ASCII);
        byte[] body = "Value".getBytes(CharsetUtil.US_ASCII);
        int id = (int) System.currentTimeMillis();
        long cas = System.currentTimeMillis();
        ByteBuf buffer = Unpooled.buffer(); //#6
        buffer.writeByte(magic);
        buffer.writeByte(opCode);
        buffer.writeShort(key.length);
        buffer.writeByte(0);
        buffer.writeByte(dataType);
        buffer.writeShort(Status.KEY_EXISTS);
        buffer.writeInt(body.length + key.length);
        buffer.writeInt(id);
        buffer.writeLong(cas);
        buffer.writeBytes(key);
        buffer.writeBytes(body);ByteBuf fragment1 = buffer.readBytes(8);//#7
        ByteBuf fragment2 = buffer.readBytes(24);
        ByteBuf fragment3 = buffer;
        Assert.assertFalse(channel.writeInbound(fragment1)); //#8
        Assert.assertFalse(channel.writeInbound(fragment2));// #9
        Assert.assertTrue(channel.writeInbound(fragment3));// #10
        MemcachedResponse response = (MemcachedResponse)
                channel.readInbound();
        assertResponse(response, magic, opCode, dataType,
                Status.KEY_EXISTS, 0, 0, id, cas, key, body); //#11
    }
    private static void assertResponse(MemcachedResponse response, byte
            magic, byte opCode, byte dataType, short status, int expires, int flags, int
                                               id, long cas, byte[] key, byte[] body) {
        Assert.assertEquals(magic, response.magic());
        Assert.assertArrayEquals(key,
                response.key().getBytes(CharsetUtil.US_ASCII));
        Assert.assertEquals(opCode, response.opCode());
        Assert.assertEquals(dataType, response.dataType());
        Assert.assertEquals(status, response.status());
        Assert.assertEquals(cas, response.cas());
        Assert.assertEquals(expires, response.expires());
        Assert.assertEquals(flags, response.flags());
        Assert.assertArrayEquals(body,
                response.data().getBytes(CharsetUtil.US_ASCII));
        Assert.assertEquals(id, response.id());
    }
}
