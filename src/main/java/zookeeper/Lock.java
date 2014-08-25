package zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-8-25
 * Time: 下午3:15
 * To change this template use File | Settings | File Templates.
 */
public class Lock {
    private String path;
    private ZooKeeper zooKeeper;
    public Lock(String path){
        this.path = path;
    }

    /**
     * <p>
     * 方法描述: 上锁 lock it
     * </p>
     * @throws Exception
     */
    public synchronized void lock() throws Exception{
        Stat stat = zooKeeper.exists(path, true);
        String data = InetAddress.getLocalHost().getHostAddress()+":lock";
        zooKeeper.setData(path, data.getBytes(), stat.getVersion());
    }

    /**
     * <p>
     * 方法描述：开锁 unlock it
     * </p>
     * @throws Exception
     */
    public synchronized void unLock() throws Exception{
        Stat stat = zooKeeper.exists(path, true);
        String data = InetAddress.getLocalHost().getHostAddress()+":unlock";
        zooKeeper.setData(path, data.getBytes(), stat.getVersion());
    }

    /**
     * <p>
     * 方法描述：是否锁住了, isLocked?
     * </p>
     * @return
     */
    public synchronized boolean isLock(){
        try {
            Stat stat = zooKeeper.exists(path, true);
            String data = InetAddress.getLocalHost().getHostAddress()+":lock";
            String nodeData = new String(zooKeeper.getData(path, true, stat));
            if(data.equals(nodeData)){
//              lock = true;
                return true;
            }
        } catch (UnknownHostException e) {
            // ignore it
        } catch (KeeperException e) {
            //TODO use log system and throw a new exception
        } catch (InterruptedException e) {
            // TODO use log system and throw a new exception
        }
        return false;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }
}
