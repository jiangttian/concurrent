package single;
//饿汉式，双重检查，由于 single = new Singleton();会有指令重排序，加volatile
class Singleton{
    private static volatile Singleton single;
    private Singleton(){
        System.out.println("单例实例化一次");
    }

    public static Singleton getSingle() {
        if (single==null)
            synchronized (Singleton.class) {
                if (single==null)
                single = new Singleton();
            }
        return single;
    }
}
public class DC {
    public static void main(String[] args){
        Runnable r=()->{
            Singleton.getSingle();
        };
        for (int i = 0; i < 100; i++) {
            new Thread(r).start();
        }
    }
}