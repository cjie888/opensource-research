package netty.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-18
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
public class AbsIntegerEncoderTest {
    @Test// #1
    public void testEncoded() {
        ByteBuf buf = Unpooled.buffer();// #2
        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }
        EmbeddedChannel channel = new EmbeddedChannel(
                new AbsIntegerEncoder());// #3
        Assert.assertTrue(channel.writeOutbound(buf));// #4
        Assert.assertTrue(channel.finish());// #5
// read bytes #6
        ByteBuf output = (ByteBuf) channel.readOutbound();
        for (int i = 1; i < 10; i++) {
            Assert.assertEquals(i, output.readInt());
        }
        Assert.assertFalse(output.isReadable());
        Assert.assertNull(channel.readOutbound());
    }
}