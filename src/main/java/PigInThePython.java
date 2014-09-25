import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-9-25
 * Time: 下午2:14
 */
public class PigInThePython {

    static volatile List pigs = new ArrayList();

    static volatile int pigsEaten = 0;

    static final int ENOUGH_PIGS = 5000;


    public static void main(String[] args) throws InterruptedException {

        new PigEater().start();

        new PigDigester().start();

    }


    static class PigEater extends Thread {
        @Override

        public void run() {

            while (true) {
                pigs.add(new byte[32 * 1024 * 1024]); //32MB per pig
                System.out.println(pigs.size());
                if (pigsEaten > ENOUGH_PIGS) return;
                takeANap(100);
            }

        }

    }

    static class PigDigester extends Thread {

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            while (true) {
                takeANap(2000);
                pigsEaten += pigs.size();
                pigs = new ArrayList();
                if (pigsEaten > ENOUGH_PIGS) {
                    System.out.format("Digested %d pigs in %d ms.%n", pigsEaten, System.currentTimeMillis() - start);
                    return;
                }
            }
        }
    }

    static void takeANap(int ms) {
         try {
            Thread.sleep(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

