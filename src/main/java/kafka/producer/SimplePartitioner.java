package kafka.producer;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-7-8
 * Time: 下午7:07
 * To change this template use File | Settings | File Templates.
 */
import kafka.utils.VerifiableProperties;

public class SimplePartitioner implements Partitioner {
    public SimplePartitioner (VerifiableProperties props) {

    }

    public int partition(String key, int a_numPartitions) {
        int partition = 0;
        int offset = key.lastIndexOf('.');
        if (offset > 0) {
            partition = Integer.parseInt( key.substring(offset+1)) % a_numPartitions;
        }
        return partition;
    }

    @Override
    public int partition(Object key, int numPartitions) {
        int partition = 0;
        int offset = ((String)key).lastIndexOf('.');
        if (offset > 0) {
            partition = Integer.parseInt( ((String)key).substring(offset+1)) % numPartitions;
        }
        return partition;
    }
}