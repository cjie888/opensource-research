package netty.chapter12;

import org.eclipse.jetty.npn.NextProtoNego;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-22
 * Time: 下午5:49
 * To change this template use File | Settings | File Templates.
 */
public class DefaultServerProvider implements NextProtoNego.ServerProvider {
    private static final List<String> PROTOCOLS =
            Collections.unmodifiableList(
                    Arrays.asList("spdy/2", "spdy/3", "http/1.1")); //#1
    private String protocol;
    @Override
    public void unsupported() {
        protocol = "http/1.1"; //#2
    }
    @Override
    public List<String> protocols() {
        return PROTOCOLS; //#3
    }
    @Override
    public void protocolSelected(String protocol) {
        this.protocol = protocol; //#4
    }
    public String getSelectedProtocol() {
        return protocol; //#5
    }
}
