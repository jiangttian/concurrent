package disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

public class MainLm {
    public static void main(String[] args) {
        int buffersize=1024;
        Disruptor<LongEvent> disruptor=new Disruptor<LongEvent>(LongEvent::new,buffersize, DaemonThreadFactory.INSTANCE);
        //connect the handler
        disruptor.handleEventsWith((longEvent, l, b) -> System.out.println("Event "+longEvent+"序号"+l));
        disruptor.start();
        RingBuffer<LongEvent> buffer=disruptor.getRingBuffer();
        buffer.publishEvent((longEvent, l) ->longEvent.setValue(100L) );
        buffer.publishEvent((longEvent, l, o) -> longEvent.setValue(o),1000L);
//        System.in.read();
    }
}
