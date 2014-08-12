package netty.chapter12;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-22
 * Time: 下午6:13
 * To change this template use File | Settings | File Templates.
 */
public class SpdyRequestHandler extends HttpRequestHandler { //#1
    @Override
    protected String getContent() {
        return "This content is transmitted via SPDY\r\n"; //#2
    }
}
