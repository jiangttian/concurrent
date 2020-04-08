package jun.testA1B2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
/**
 * 要求线程交替打印A，1，B,2,....
 */
public class Locksupport { //wait notify
    //添加volatile，使t2能够得到通知
    private volatile LinkedList<Object>list=new LinkedList<Object>();
    private volatile static boolean  firstA=true;
    static Thread t1=null,t2=null;
    public Locksupport() {
        char s='A';
        int j=1;
        for (int i =0; i < 10; i++) {
            list.add(s++);
            list.add(j++);
        }
    }
    public static void main(String[] args) {
        Locksupport c = new Locksupport();
        LockSupport support;
        t2=new Thread(() -> {//打印ABC..
            System.out.println("tA启动");
            while (c.list.size()>0) {
                while (!firstA) {
                    LockSupport.park();
                }
                Object s=c.list.poll();
                if(s==null)
                    break;
                System.out.println(s);
                firstA = false;
                LockSupport.unpark(t1);
            }
        }, "t2");
        t2.start();

        t1=new Thread(() -> {
            System.out.println("t1启动");
            while (c.list.size()>0) {
                while (firstA) {
                    LockSupport.park();
                }
                Object s= c.list.poll();
                if(s==null)
                    break;
                System.out.println(s);
                firstA = true;
                LockSupport.unpark(t2);
            }
        }, "t1");
        t1.start();

    }
}