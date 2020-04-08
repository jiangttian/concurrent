package ty;

import java.util.ArrayList;
public class Op {
    public static ArrayList<Integer> printMatrix(int[][] matrix) {
        ArrayList<Integer>list = new ArrayList<>();
        if(matrix.length==0||matrix[0].length==0||matrix==null)
            return list;
        int up=0,down=matrix.length-1,left=0,right=matrix[0].length-1;
        while (true){
            for (int i = left; i <=right ; i++) {
                list.add(matrix[up][i]);
            }
            up++;
            if(up>down)break;
            for (int i = up; i <=down ; i++) {
                list.add(matrix[i][right]);
            }
            right--;
            if(right<left)break;
            for (int i = right; i >=left ; i--) {
                list.add(matrix[down][i]);
            }
            down--;
            if(down<up)break;
            for (int i = down; i >=up ; i--) {
                list.add(matrix[i][left]);
            }
            left++;
            if(left>right)break;
        }
        return  list;
    }
    public static int NumberOf1Between1AndN_Solution(int n) {
        int sum=0;
        StringBuffer s=new StringBuffer();
        for(int i=1;i<=n;i++){
            s.append(i);
        }
        while(s.indexOf("1")!=-1){
            int i=s.indexOf("1");
            s.delete(i,i+1);
            sum++;
        }
        return sum;
    }
    public static void main(String[] args) {
//        int [][]array={{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
//        ArrayList<Integer> list = printMatrix(array);
//        System.out.println(list);
        int t=NumberOf1Between1AndN_Solution(1);
        System.out.println(t);
    }
}