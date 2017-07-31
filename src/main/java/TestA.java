/**
 * Created by hucj on 2017/7/31.
 */
public class TestA {

    static { a = 1; }
    static int a=0;  // 0  1)如果不赋值呢？ 1 2)如果这一句与上一句位置互换？1

    public static void main(String[] args){
        System.out.println(TestA.a);
    }

}
