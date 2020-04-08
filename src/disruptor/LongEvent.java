package disruptor;
//1定义Event队列中需要处理的元素
public class LongEvent {
    private long value;

    @Override
    public String toString() {
        return "longEvent{" +
                "value=" + value +
                '}';
    }

    public void setValue(long value) {
        this.value = value;
    }
}
