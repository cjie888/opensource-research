package mq;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.activemq.transport.stomp.Stomp;
import org.apache.activemq.transport.stomp.StompConnection;

import com.mongodb.util.JSON;

/**
 * @description 胡成杰
 * 2016/7/24 10:01
 */
public class STOMPTest {
    /**
     * 基于stomp协议发送JMS消息
     * @param args 参数
     */
    public static void main(String[] args) throws Exception
    {
        StompConnection conn = new StompConnection();
        conn.open("127.0.0.1", 61613);
        conn.connect("user1", "password1");

        // send text message
        HashMap<String, String> txtHeaders = new HashMap<String,String>();
        txtHeaders.put(Stomp.Headers.Send.PERSISTENT, "true");
        String text = "stomp text message 1.";
        sendMessage(conn, text, txtHeaders);
        System.out.println("send: " + text);

        // send map message
        HashMap<String, String> mapHeaders = new HashMap<String,String>();
        mapHeaders.put(Stomp.Headers.Connect.CLIENT_ID, "client001");
        mapHeaders.put(Stomp.Headers.Send.PERSISTENT, "true");
        mapHeaders.put(Stomp.Headers.AMQ_MESSAGE_TYPE, "map");
        mapHeaders.put(Stomp.Headers.TRANSFORMATION, "jms-stomp-map-json");
        Map<String, String> map = new HashMap<String,String>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        String json = JSON.serialize(map);
        sendMessage(conn, json, mapHeaders);
        System.out.println("send: " + json);

        // disconnect
        conn.disconnect();
    }

    /**
     * 发送JMS消息
     * @param conn       jms connection
     * @param message    message content
     * @param headers    message headers
     * @throws Exception exception
     */
    private static void sendMessage(
            StompConnection conn, String message, HashMap<String, String> headers) throws Exception
    {
        String tx = UUID.randomUUID().toString().replaceAll("-", "");
        conn.begin(tx);
        conn.send("test_queue", message, tx, headers);
        conn.commit(tx);
    }
}
