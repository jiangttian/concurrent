package porcon;

import java.util.LinkedList;
import java.util.List;

/**
 * 写一个固定容量的同步容器，有put,get,getCount方法，支持2个生产线程和10个消费线程阻塞使用
 * notify（不释放锁，进行完后释放）和wait(释放锁)实现
 */
public class Syncon<T> {
   final private int msize=10;
    private List<T> list=new LinkedList<T>();
    public synchronized T get(){
        T t=null;
        //用while是因为还要继续判断容量大小
        while (list.size()==0){//容器为空，消费者等待，容器不为空由生产者唤醒
            try {
                this.wait();
                //this.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t=list.remove(0);
        System.out.println(Thread.currentThread().getName()+"消费"+t);
        this.notifyAll();
        return t;
    }
    public synchronized void put(T t){//同步存
        while (list.size()==msize){//容器满了，不能再往里加，生产者线程等待,由消费线程唤醒
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(t);
        System.out.println(Thread.currentThread().getName()+"添加："+t.toString());
        this.notifyAll();
    }
    public int getCount() {

        return list.size();
    }

    public static void main(String[] args) {
        Syncon<String> container=new Syncon<String>();
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
