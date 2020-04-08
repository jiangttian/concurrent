package porcon;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 写一个固定容量的同步容器，有put,get,getCount方法，支持2个生产线程和10个消费线程阻塞使用
 * reentryLock和condition实现选择性通知
 */
public class Syncon1<T> {
   final private int msize=10;
    private List<T> list=new LinkedList<T>();
    ReentrantLock lock=new ReentrantLock();
    Condition pro=lock.newCondition();
    Condition con=lock.newCondition();
    public  T get(){
        lock.lock();
        try{
            T t=null;
            //用while是因为还要继续判断容量大小
            while (list.size()==0){//容器为空，消费者等待，容器不为空由生产者唤醒消费者
                try {
                    con.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            t=list.remove(0);
            System.out.println(Thread.currentThread().getName()+"消费"+t);
            pro.signalAll();//容器不为空，唤醒生产者
            return t;
        }finally {
            lock.unlock();
        }

    }
    public void put(T t){//同步存
        try {
            lock.lock();
            while (list.size()==msize){//容器满了，不能再往里加，生产者线程等待,由消费线程唤醒
                try {
                    pro.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(t);
            System.out.println(Thread.currentThread().getName()+"添加："+t.toString());
            con.signalAll();
        }finally {
            lock.unlock();
        }
    }
    public int getCount() {

        return list.size();
    }

    public static void main(String[] args) {
        Syncon1<String> container=new Syncon1<String>();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 5; j++) {
                    String t=container.get();
                }
            },"c"+(i+1)).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                for (int j = 0; j < 25; j++) {
                    container.put("number"+j);
                }
            },"pro"+i).start();
        }

    }
}
