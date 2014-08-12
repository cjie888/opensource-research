package guava;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-4-17
 * Time: 下午3:07
 * To change this template use File | Settings | File Templates.
 */
public class TestEvent {
    private final int message;
    public TestEvent(int message) {
        this.message = message;
        System.out.println("event message:"+message);
    }
    public int getMessage() {
        return message;
    }
}