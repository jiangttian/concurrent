package jun.testA1B2;

import java.util.LinkedList;

public class Syncwano {
    private static volatile LinkedList<Object> list=new LinkedList<Object>();
    public Syncwano() {
        char s='A';
        int j=1;
        for (int i =0; i < 10; i++) {
            list.add(s++);
            list.add(j++);
        }
    }

    public static void main(String[] args) {
        Syncwano syncwano=new Syncwano();
        Object object=new Object();
        new Thread(()->{
            synchronized(object){
                System.out.println("123获得锁");
                while (list.size()>0){
                    try {
                        object.wait();
                        System.out.println(list.poll());
                        object.notify();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
//                object.notify();
            }
        }).start();
        new Thread(()->{
            synchronized(object){
                System.out.println("AB获得锁");
                while (list.size()>0){
                    System.out.println(list.poll());
                    object.notify();
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
//                object.notify();
            }
        }).start();
    }
}
