package porcon;

/**
 * 生产者：负责生产产品
 * 生产者逻辑：通过一个生产标记判断是否生产：需要，生产并通知生产者消费；不需要则等待（食品满了）
 */

/**
 * 消费者：消费产品
 * 消费逻辑：可以消费则消费产品，产品不足则等待
 */

public class PCTest {
    public static void main(String[] args) {
        ProductPoll poll=new ProductPoll(10);
        Runnable p=()->{
            while (true){
                String name=(int)(Math.random()*100)+"号产品";
                System.out.println("生产了一件产品"+name);
                poll.product(new Product(name));
            }
        };
        Runnable c=()->{
            while (true){
                Product pro=poll.get();
                System.out.println("消耗了一件产品"+pro.getName());
            }

        };
        Thread tp=new Thread(p);
        Thread tc=new Thread(c);
        tp.start();
        tc.start();
    }
}
