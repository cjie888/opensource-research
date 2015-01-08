import org.jruby.RubyProcess;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-5-8
 * Time: 下午5:14
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static String getNowDate() {
        Calendar rightNow = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddhhmmss");
        long a = System.currentTimeMillis();
        String sysDatetime = fmt.format(rightNow.getTime());
       // System.out.println(System.currentTimeMillis() -a);
        return sysDatetime;
    }
    public static void main(String[] args) {

        Double abc = null;
        //System.out.println(0.0!=abc); NullPointException
        Timestamp t1 = new Timestamp(System.currentTimeMillis() -3600000);
        System.out.println(t1.after(new Timestamp(System.currentTimeMillis())));
        String s="sadf的说法发阿萨德";
        for(int i=0;i<s.length();i++)      {
            System.out.print(s.charAt(i)+" ");
        }
        Pattern p = Pattern.compile("[^0&&\\d]");
        System.out.println(p.matcher("a").find());
        System.out.println(p.matcher("4").find());
        System.out.println(p.matcher("0").find());
        String s2="123";
        s2+=5;
        System.out.println(s2);
        for (int i=0;i<100;i++)      {
            long a = System.currentTimeMillis();
            //System.out.println(getNowDate());
            getNowDate();
            System.out.println(System.currentTimeMillis() -a);
        }

        int b=5;
        int c = 1;
        int d = 2;
        b=c+=d+=b+++b;  //d=2+5+6 = 13  c=1+13 b = c=14
        d+=b; //d=27

        int expr = -2 >> 5;
        System.out.println("exp:"+expr);
    }
}

abstract class Actor {
    abstract void draw();
    Actor() {
        System.out.println("Actor() before draw()");
        draw();
        System.out.println("Actor() after draw()");
    }
}

class Hero extends Actor {
    int hp = 1;
    Hero(int i) {
        hp = i;
        System.out.println("Hero.Hero(), hp = " + hp);
    }
    void draw() {
        System.out.println("Hero.draw(), hp = " + hp);
    }
}

class PolyConstructors {
   //父类静态块 --> 然后是子类静态块 --> 父类自由块 --> 父类构造函数块 --> 子类自由块 --> 子类构造函数块
    public static void main(String[] args) {
        new Hero(5);
    }
}
