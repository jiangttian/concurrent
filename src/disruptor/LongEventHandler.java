package disruptor;


import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent> {
    public static long count=0;

//l是序号，存在那个位置，b是否为最后一个元素
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        count++;
        System.out.println(Thread.currentThread().getName()+longEvent+"序号"+l);
    }
}
