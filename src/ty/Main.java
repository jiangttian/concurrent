package ty;
import java.util.HashSet;
import java.util.Scanner;
public class Main {
    public static int len(String []q,String p,int i){
        int n=p.length();
        switch (n){
            case 1://判断命令合法性
                char c=p.charAt(0);
                if(c<'a'||c>'z')return 0;
                else break;
            case 2:
                if(!p.equals("OR")) {
                    System.out.println("不是OR");
                    return 0;
                }
                if(q[0].equals("OR")) {
                    System.out.println("OR不能在开头");
                    return 0;
                }
                else break;
            case 3:
                if(q[0].equals("AND")) return 0;
                if(p.equals("AND")||p.equals("NOT")){
                    if(q[i].equals(p)&&q[i+1].equals(p)&&i<q.length-1)//连续AND NOT
                        return 0;
                    if(i==(q.length-1))return 0;//位于最后
                    if(p.equals("NOT")&&(q[i+1].length()!=1))
                        return 0;
                }else return 0;
            default:
                break;
        }
        return 1;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String s=in.nextLine();
            String []q=s.split(" ");
            for(int i=0;i<q.length;i++){
                int f=len(q,q[i],i);
                if(f==0) {
                    System.out.println(0);
                    break;
                }
                if((i==q.length-1)&&f==1)
                    System.out.println(1);

            }
        }
        in.close();
    }
}
/*000
a AND AND b
A AND b
a and b
*/
/*11
a AND b
a AND b OR c OR NOT d
*/
