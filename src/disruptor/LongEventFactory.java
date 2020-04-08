package disruptor;

import com.lmax.disruptor.EventFactory;
//2定义Event工厂，用于填充队列
public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }

}
