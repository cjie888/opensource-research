package netty.chapter8;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-14
 * Time: 下午5:17
 * To change this template use File | Settings | File Templates.
 */
public class HttpDecoderEncoderInitializer
        extends ChannelInitializer<Channel> {

    private final boolean client;
    public HttpDecoderEncoderInitializer(boolean client) {
        this.client = client;
    }
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (client) {
            pipeline.addLast("decoder", new HttpResponseDecoder()); //#1
            pipeline.addLast("encoder", new HttpRequestEncoder()); //#2
        } else {
            pipeline.addLast("decoder", new HttpRequestDecoder());// #3
            pipeline.addLast("encoder", new HttpResponseEncoder());// #4
        }
    }
}
