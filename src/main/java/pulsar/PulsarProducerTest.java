package pulsar;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

public class PulsarProducerTest {

    public static void main(String[] args) throws PulsarClientException {
        PulsarClient client = PulsarClient.builder()
                .serviceUrl("pulsar://localhost:6650")
                .build();

        Producer<String> stringProducer = client.newProducer(Schema.STRING)
                .topic("my-topic")
                .create();
        for (int i = 0;i< 100000; i++) {
            stringProducer.send("My message" + i);
        }

        //producer.close();
        stringProducer.closeAsync()
                .thenRun(() -> System.out.println("Producer closed"))
//          .exceptionally((ex) -> {
//            System.err.println("Failed to close producer: " + ex);
//            return ex;
//        })
        ;
        //consumer.close();
        client.close();
    }
}
