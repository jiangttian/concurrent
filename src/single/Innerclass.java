package single;

public class Innerclass {
    private static class InnerclassHandle{
        private static Innerclass single=new Innerclass();
    }
    private Innerclass(){}
    public static Innerclass get(){
        return InnerclassHandle.single;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                System.out.println(Innerclass.get());
            }).start();
        }
    }
//    Innerclass c=
}
