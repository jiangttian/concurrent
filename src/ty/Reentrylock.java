package ty;

import java.util.concurrent.TimeUnit;

//一个同步方法可以调用另一个同步方法，一个线程获得某对象锁后，再次申请时还可以获得该对象的锁，
public class Reentrylock {
    public synchronized void m1(){
        System.out.println("m1 start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();
        System.out.println("m1 end");
    }
    public synchronized void m2(){
        System.out.println("m2 start");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2 end");
    }

    public static void main(String[] args) {
        Reentrylock re = new Reentrylock();
        new Thread(re::m1).start();
    }
}
