/**
 * Created by hucj on 2016/5/18.
 */
public class TestPrinter {
    public static void main(String[] args) {
        Printer p = new Printer();
        Thread t1 = new NumberPrinter(p);
        Thread t2 = new LetterPrinter(p);
        t1.start();
        t2.start();
    }

}

class NumberPrinter extends Thread{

    private Printer p;
    public NumberPrinter(Printer p){this.p = p;}

    public void run(){
        for(int i = 1; i<=52; i++){
            p.print(i);
        }
    }
}

class LetterPrinter extends Thread{

    private Printer p;
    public LetterPrinter(Printer p){this.p = p;}

    public void run(){
        for(char c='A'; c<='Z'; c++){
            p.print(c);
        }
    }
}

class Printer{

    int flag = 1;

    public synchronized void print(int i ) {
        while(flag%3==0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.print(i);
        flag++;
        notifyAll();
    }

    public synchronized void print(char i) {
        while(flag%3!=0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.print(i);
        flag++;
        notifyAll();
    }

}