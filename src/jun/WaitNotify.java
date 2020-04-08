package jun;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 线程一：向容器中加10个元素，线程2在容器中有5个元素时，结束。
 */
//这里使用wait和notify做到，wait会释放锁，而notify不会释放锁
// 需要注意的是，运用这种方法，必须要保证t2先执行，也就是首先让t2监听才可以

public class WaitNotify { //wait notify

    //添加volatile，使t2能够得到通知
    volatile List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        WaitNotify c = new WaitNotify();

        final Object lock = new Object();

        new Thread(() -> {
            synchronized(lock) {//获得锁
                System.out.println("t2启动");
                if(c.size() != 5) {
                    try {
                        lock.wait();//释放锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 结束");
                lock.notify();
            }
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("t1启动");
            synchronized(lock) { //获得锁
                for(int i=0; i<10; i++) {
                    c.add(new Object());
                    System.out.println("add " + i);

                    if(c.size() == 5) {
                        lock.notify();//此时不会释放锁，执行完后才释放
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1").start();


    }
}