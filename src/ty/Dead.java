package ty;

public class Dead {
    public static void main(String[] args) {
        Runnable r=() ->{
            synchronized ("A"){
                System.out.println("线程A获得A锁");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized ("B")
                {
                    System.out.println("线程A获得A锁和B锁");
                }
            }
            System.out.println(Thread.currentThread().getName()+"线程结束");

        };
        Runnable rr=() ->{
            synchronized ("B"){
                System.out.println("线程B获得B锁");
                synchronized ("A")
                {
                    System.out.println("线程B获得B锁和A锁");
                }
            }
            System.out.println(Thread.currentThread().getName()+"线程结束");

        };
        Thread a=new Thread(r,"A");
        Thread aa=new Thread(rr,"B");
        a.start();
        aa.start();
    }
}
