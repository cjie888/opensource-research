package netty.chapter8;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-14
 * Time: 下午5:27
 * To change this template use File | Settings | File Templates.
 */
public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {
    private final boolean client;
    public HttpAggregatorInitializer(boolean client) {
        this.client = client;
    }
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (client) {
            pipeline.addLast("codec", new HttpClientCodec());// #1
            pipeline.addLast("decompressor",
                    new HttpContentDecompressor());
        } else {
            pipeline.addLast("codec", new HttpServerCodec());// #2
            pipeline.addLast("decompressor",
                    new HttpContentDecompressor());
        }
        pipeline.addLast("aggegator",
                new HttpObjectAggregator(512 * 1024)); //#3
    }
}
