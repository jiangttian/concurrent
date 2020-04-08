package jun;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 线程一：向容器中加10个元素，线程2在容器中有5个元素时，结束。
 */

public class CountDown { //wait notify

    //添加volatile，使t2能够得到通知
    volatile List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        CountDown c = new CountDown();
        CountDownLatch latch=new CountDownLatch(5);
        CountDownLatch latch2=new CountDownLatch(1);
        new Thread(() -> {
                System.out.println("t2启动");
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2 结束");
                latch2.countDown();
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("t1启动");
                for(int i=0; i<10; i++) {
                    c.add(new Object());
                    System.out.println("add " + i);
                    latch.countDown();
                    if(c.size()==5){
                        try {
                            latch2.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
        }, "t1").start();


    }
}