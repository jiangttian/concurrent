package disruptor;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;


import java.util.concurrent.Executors;

public class Main02 {
    public static void main(String[] args) {
        LongEventFactory factory=new LongEventFactory();
        int buffersize=1024;
        Disruptor<LongEvent> disruptor=new Disruptor<LongEvent>(factory,buffersize, DaemonThreadFactory.INSTANCE);
        //connect the handler
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();
        //used for publishing
        RingBuffer<LongEvent>buffer=disruptor.getRingBuffer();
//        long sequence=buffer.next();
        EventTranslator<LongEvent>translator=new EventTranslator<LongEvent>() {
            @Override
            public void translateTo(LongEvent longEvent, long l) {
                longEvent.setValue(888L);
            }
        };
        buffer.publishEvent(translator);

        EventTranslatorOneArg<LongEvent,Long>translator2=new EventTranslatorOneArg<LongEvent, Long>() {
            @Override
            public void translateTo(LongEvent longEvent, long l, Long aLong) {
                longEvent.setValue(aLong);
            }
        };
        buffer.publishEvent(translator2,777L);
    }
}
