package distuptor;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-8-1
 * Time: 上午11:59
 * To change this template use File | Settings | File Templates.
 */
import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent>
{
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch)
    {
        System.out.println("Event: " + event.get());
    }
}

