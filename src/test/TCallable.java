package test;

import java.util.concurrent.*;

public class TCallable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> c=new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "hello";
            }
        };
        ExecutorService service= Executors.newCachedThreadPool();
        Future<String> future = service.submit(c);
        System.out.println(future.get());//等待计算结果并返回
        service.shutdown();
    }
}
