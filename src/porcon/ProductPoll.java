package porcon;

import java.util.LinkedList;
import java.util.List;
//进行产品管理，生产者生产，消费者取出
public class ProductPoll {
    private List<Product> list;
    private int maxsize=0;
    public ProductPoll(int maxsize){
        this.list=new LinkedList<Product>();
        this.maxsize=maxsize;
    }
    //生产者生产的产品放到池中
    public synchronized void product(Product product){
        if(this.list.size()==maxsize){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.list.add(product);
        this.notifyAll();//通知，可以消费了
    }
    public synchronized Product get(){
        if(this.list.size()==0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Product product = this.list.remove(0);
        this.notifyAll();//通知其他，取出了一件
        return product;
    }
}
