package netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件传输接收端，没有处理文件发送结束关闭流的情景
 *
 * @author lihzh
 * @alia OneCoder
 * @blog http://www.coderli.com
 */
public class FileServerHandler extends SimpleChannelHandler {

    private File file = new File("F:/2.txt");
    private FileOutputStream fos;

    public FileServerHandler() {
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception {
        ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
        int length = buffer.readableBytes();
        buffer.readBytes(fos, length);
        fos.flush();
        buffer.clear();
    }

}
