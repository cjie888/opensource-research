/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-10-13
 * Time: 上午11:29
 * To change this template use File | Settings | File Templates.
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SDFThreadsafeThreadLocalDemo {

    private final static ThreadLocal threadLocal = new ThreadLocal() {
        protected Object initialValue() {
            return new SimpleDateFormat("dd-MM-yyyy");
        };
    };

    private static String[] stringDates = { "21-12-2012", "10-10-2013", "23-02-2014" };

    public static void main(String[] args) {

        Thread t1 = new Thread(new MyThread());
        Thread t2 = new Thread(new MyThread());
        Thread t3 = new Thread(new MyThread());
        t1.start();
        t2.start();
        t3.start();
    }

    private static class MyThread implements Runnable {

        @Override
        public void run() {
            for (String strDate : stringDates) {
                try {
                    SimpleDateFormat sdf = (SimpleDateFormat) threadLocal.get();
                    System.out.println(sdf.parse(strDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
