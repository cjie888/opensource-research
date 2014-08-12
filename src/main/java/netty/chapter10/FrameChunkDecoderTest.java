package netty.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-18
 * Time: 上午11:25
 * To change this template use File | Settings | File Templates.
 */
public class FrameChunkDecoderTest {
    @Test //#1
    public void testFramesDecoded() {
        ByteBuf buf = Unpooled.buffer(); //#2
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(
                new FrameChunkDecoder(3));// #3
        Assert.assertTrue(channel.writeInbound(input.readBytes(2)));// #4
        try {
            channel.writeInbound(input.readBytes(4));// #5
            Assert.fail();
        } catch (TooLongFrameException e) {
// expected}
            Assert.assertTrue(channel.writeInbound(input.readBytes(3)));
            Assert.assertTrue(channel.finish());// #6
// Read frames #7
            Assert.assertEquals(buf.readBytes(2), channel.readInbound());
            Assert.assertEquals(buf.skipBytes(4).readBytes(3),
                    channel.readInbound());
        }
    }
}
