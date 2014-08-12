package distuptor;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-8-1
 * Time: 上午11:58
 * To change this template use File | Settings | File Templates.
 */
import com.lmax.disruptor.EventFactory;

public class LongEventFactory implements EventFactory<LongEvent>
{
    public LongEvent newInstance()
    {
        return new LongEvent();
    }
}
