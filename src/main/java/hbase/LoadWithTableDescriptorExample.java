package hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.Coprocessor;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.coprocessor.RegionObserver;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-5-15
 * Time: 上午10:06
 * To change this template use File | Settings | File Templates.
 */
public class LoadWithTableDescriptorExample {
    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        FileSystem fs = FileSystem.get(conf);

        Path path = new Path(fs.getUri() + Path.SEPARATOR + "test.jar");

        HTableDescriptor tableDescriptor = new HTableDescriptor("testtable");

        tableDescriptor.addFamily(new HColumnDescriptor("colfam1"));
        tableDescriptor.setValue("COPROCES$1", path.toString() + "|" + RegionObserver.class.getCanonicalName()
            + "|" + Coprocessor.PRIORITY_USER);

        HBaseAdmin admin = new HBaseAdmin(conf);

        admin.createTable(tableDescriptor);

        System.out.println(admin.getTableDescriptor(Bytes.toBytes("testtable"))) ;

    }
}
