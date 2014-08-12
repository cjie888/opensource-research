import static java.lang.System.out;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-5-19
 * Time: 下午4:36
 * To change this template use File | Settings | File Templates.
 */
public class BinaryIntegerLiteral {
    public void display() {
        out.println(0b001001);
        out.println(0B001101);
    }
    public static void main(String args[]) {
        BinaryIntegerLiteral test = new BinaryIntegerLiteral();
        test.display();
    }
}
