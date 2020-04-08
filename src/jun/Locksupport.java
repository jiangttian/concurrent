package jun;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程一：向容器中加10个元素，线程2在容器中有5个元素时，结束。
 */

public class Locksupport { //wait notify

    //添加volatile，使t2能够得到通知
    volatile List lists = new ArrayList();
    static Thread t1=null,t2=null;
    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        Locksupport c = new Locksupport();
        LockSupport support;
        t1=new Thread(() -> {
            System.out.println("t1启动");
                for(int i=0; i<10; i++) {
                    c.add(new Object());
                    System.out.println("add " + i);
                    if(c.size()==5){
                        LockSupport.unpark(t2);
                        LockSupport.park();
                    }
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
        }, "t1");
        t2=new Thread(()->{
            System.out.println("t2启动");
            LockSupport.park();
            System.out.println("t2 结束");
            LockSupport.unpark(t1);
        },"t2");
        t2.start();
        t1.start();

    }
}