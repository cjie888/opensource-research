import org.junit.*;

/**
 * Created by huchengjie on 2015/1/7.
 */
public class EnumSingletonTest {


    @org.junit.Test
    public void test() {
        Singleton.INSTANCE.read();
        Singleton.INSTANCE.write();
    }
    public enum Singleton {
        INSTANCE {
            @Override
            protected  void read() {
                System.out.println("read");
            }
            @Override
            protected  void write() {
                System.out.println("write");
            }
        };
        protected abstract  void read();
        protected  abstract  void write();
    }
}
