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
        String s="sadf的说法发阿萨德";
        for(int i=0;i<s.length();i++)      {
            System.out.print(s.charAt(i)+" ");
        }
        Pattern p = Pattern.compile("[^0&&\\d]");
        System.out.println(p.matcher("a").find());
        System.out.println(p.matcher("4").find());
        System.out.println(p.matcher("0").find());

        for (int i=0;i<100;i++)      {
            long a = System.currentTimeMillis();
            //System.out.println(getNowDate());
            getNowDate();
            System.out.println(System.currentTimeMillis() -a);
        }
    }
}
