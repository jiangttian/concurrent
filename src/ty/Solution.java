package ty;
import java.util.ArrayList;
import java.util.HashSet;

class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}
class Person1{
    String name;
    int age;

    public Person1(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person1{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
public class Solution {
    public  ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        ListNode tmp = listNode;
        while(tmp!=null){
            list.add(0,tmp.val);
            tmp = tmp.next;
        }
        return list;
    }
    public ListNode FindKthToTail(ListNode head,int k) {
        ListNode s=head,f=head;
        int kk=k;
        while(f!=null){
            while(kk>0&&f!=null){
                f=f.next;
                kk--;
            }
            if(f==null&&kk==0)return s;
            if(f==null&&kk>0)return null;
            s=s.next;
            if(f!=null)
            f=f.next;
            kk=k;
        }
        return s;
    }
    public static void main(String[] args) {
        ListNode list1=new ListNode(1);
        list1.next=new ListNode(2);
        list1.next.next=new ListNode(3);
        Solution s = new Solution();
        ListNode t=s.FindKthToTail(list1,3);
            System.out.println(t.val);
/*        HashSet<Person1> hashSet = new HashSet<>();
        hashSet.add(new Person1("吕布", 25));
        hashSet.add(new Person1("吕布", 25));
        hashSet.add(new Person1("关羽", 26));
        hashSet.add(new Person1("关羽", 26));
        hashSet.add(new Person1("赵云", 23));
        hashSet.add(new Person1("赵云", 23));
        for (Person1 person1 : hashSet) {
            System.out.println(person1.hashCode());
       }*/
    }
}