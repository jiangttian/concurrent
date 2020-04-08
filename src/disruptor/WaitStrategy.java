package disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.*;

public class WaitStrategy {
    public static void main(String[] args) throws InterruptedException {
        LongEventFactory factory=new LongEventFactory();
        int buffersize=1024;
        Disruptor<LongEvent> disruptor=new Disruptor<LongEvent>(factory,buffersize, Executors.defaultThreadFactory(),
                ProducerType.MULTI,new SleepingWaitStrategy());
        //connect the handler
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();
        //used for publishing
        RingBuffer<LongEvent> buffer=disruptor.getRingBuffer();
        final int threadcount=50;
        CyclicBarrier barrier=new CyclicBarrier(threadcount);
        ExecutorService service=Executors.newCachedThreadPool();
        for (int i = 0; i <threadcount; i++) {
            final long thnum=i;
            service.submit(()->{
                System.out.printf("Thread %s ready to start!\n",thnum);
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < 100; j++) {
                    buffer.publishEvent((longEvent, l) -> {
                        longEvent.setValue(thnum);
                        System.out.println("生产了"+thnum);
                    });
                }
            });
        }
        service.shutdown();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(LongEventHandler.count);
    }
}
