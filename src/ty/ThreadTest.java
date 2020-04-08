package ty;

import java.util.concurrent.locks.ReentrantLock;

class MyThread extends Thread{
    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println("线程一开始");
        for(int i=0;i<6;i++) {
            System.out.println(this.getName()+i);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class MyRunnable implements Runnable{
    ReentrantLock lock=new ReentrantLock();
    @Override
    public void run() {
            while (Ticket.t > 0) {
               lock.lock();
               if(Ticket.t<0)
                   return;
                    System.out.println(Thread.currentThread().getName() + "卖出一张，剩余" + --Ticket.t);
               lock.unlock();
            }
        System.out.println(Thread.currentThread().getName()+"线程结束");
        }
}
class Ticket{
    public static int t=100;
}
public class ThreadTest {
    public static void main(String[] args) {
    Runnable run=new MyRunnable();
    Thread p1=new Thread(run,"售票1");
    Thread p2 = new Thread(run, "售票2");
    Thread p3 = new Thread(run, "售票3");
    Thread p4 = new Thread(run, "售票4");
    p1.start();
    p2.start();
    p3.start();
    p4.start();
    }
}
