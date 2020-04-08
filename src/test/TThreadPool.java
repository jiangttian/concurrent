package test;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TThreadPool {
    static class Task implements Runnable{
        private int i;
        public Task(int i){
            this.i=i;
        }

        @Override
        public String toString() {
            return "Task{" +
                    "i=" + i +
                    '}';
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"  Task"+i);
            try {
                System.in.read();//使任务阻塞在这
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor=new ThreadPoolExecutor(2,4,60,
                TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(4),
                new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 8; i++) {
            poolExecutor.execute(new Task(i));
        }
        System.out.println(poolExecutor.getQueue());
        poolExecutor.execute(new Task(10));
        System.out.println(poolExecutor.getQueue());
        poolExecutor.shutdown();
    }
}
