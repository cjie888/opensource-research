package netty.chapter12;

import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.codec.spdy.SpdyOrHttpChooser;
import org.eclipse.jetty.npn.NextProtoNego;

import javax.net.ssl.SSLEngine;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-22
 * Time: 下午6:14
 * To change this template use File | Settings | File Templates.
 */
public class DefaultSpdyOrHttpChooser extends SpdyOrHttpChooser {
    public DefaultSpdyOrHttpChooser(int maxSpdyContentLength,
                                    int maxHttpContentLength) {
        super(maxSpdyContentLength, maxHttpContentLength);
    }
    @Override
    protected SpdyOrHttpChooser.SelectedProtocol getProtocol(SSLEngine engine) {
        DefaultServerProvider provider =
                (DefaultServerProvider) NextProtoNego.get(engine); //#1
        String protocol = provider.getSelectedProtocol();
        if (protocol == null) {
            return SelectedProtocol.UNKNOWN; //#2
        }switch (protocol) {
            case "spdy/2":
               // return SelectedProtocol.SPDY_2;// #3
            case "spdy/3":
               // return SelectedProtocol.SPDY_3; #4
            case "http/1.1":
                return SelectedProtocol.HTTP_1_1;// #5
            default:
                return SelectedProtocol.UNKNOWN; //#6
        }
    }
    @Override
    protected ChannelInboundHandler createHttpRequestHandlerForHttp() {
        return new HttpRequestHandler(); //#7
    }
    @Override
    protected ChannelInboundHandler createHttpRequestHandlerForSpdy() {
        return new SpdyRequestHandler(); //#8
    }
}
