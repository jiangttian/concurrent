package disruptor;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.Executors;

public class Main01 {
    public static void main(String[] args) {
        LongEventFactory factory=new LongEventFactory();
        int buffersize=1024;
        Disruptor<LongEvent> disruptor=new Disruptor<LongEvent>(factory,buffersize, Executors.defaultThreadFactory());
        //connect the handler
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();
        //used for publishing
        RingBuffer<LongEvent>buffer=disruptor.getRingBuffer();
        long sequence=buffer.next();
        try {
            LongEvent event = buffer.get(sequence);
            event.setValue(8888L);
        }finally {
            buffer.publish(sequence);
        }
    }
}
