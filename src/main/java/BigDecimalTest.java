import java.math.BigDecimal;

/**
 * Created by hucj on 2016/3/7.
 */
public class BigDecimalTest {

    public static void main(String[] args) {
        BigDecimal amount = new BigDecimal("1233.141500");
        System.out.println(amount.signum());//正负
        System.out.println(amount.scale()); //标度 (小数点后位数)
        System.out.println(amount.precision());//精度（数的长度）
        System.out.println(amount.stripTrailingZeros());
        System.out.println(amount.stripTrailingZeros().scale());//去零后的标度
    }
}
