import java.util.*;

/**
 * @author z
 */
public class WineQuestion {

    private List<Node> workingList = new ArrayList<>();//用队列实现广度优先遍历
    private List<Node> visitedNode = new ArrayList<>();//存储已访问过的状态
    private int[] contains=new int[]{12,8,5};
    private Node head = new Node(12, 0, 0);//定义初始状态
    private int key=6;//定义最终状态

    //构造（题目要求）
    public WineQuestion(){ }
    //构造（自定义情况）
    public WineQuestion(int contains1,int contains2,int contains3,int A,int B,int C,int key){
        contains[0]=contains1;
        contains[1]=contains2;
        contains[2]=contains3;
        head.setA(A);
        head.setB(B);
        head.setC(C);
        this.key=key;
    }

    public void solve() {
        workingList.add(head);            //最先开始在队列中加入头结点

        //循环“出-入”直至找到目标或队列元素全部取出
        while (true) {
            if (workingList.isEmpty()) {        //队列空则退出，表示未找到解
                System.out.println("无解");
                return;
            }

            Node node = workingList.remove(0);   //弹出并接受工作队列的第一个元素

            if (node==null) {                  // 如果节点为空,就输出错误信息
                System.out.println("FAIL");
                return;
            }

            if (node.contain(key)) {         //如果得到目标，就输出序列
                printRoute(node);
                break;
            }

            if (visitedNode.contains(node)) {    //如果这个节点已经访问过，就跳过此点，直接下一次循环
                continue;
            }

            visitedNode.add(node);              //未在访问序列出现过的状态加入到已访问节点序列

            //产生新的状态
            //A->B
            if(node.getA()>0&&node.getB()<contains[1]){
                if(node.getA()+node.getB()<=contains[1])
                    workingList.add(new Node(0,node.getA()+node.getB(),node.getC(),node));
                if(node.getA()+node.getB()>contains[1])
                    workingList.add(new Node((node.getA()-(contains[1]-node.getB())),contains[1],node.getC(),node));
            }
            //A->C
            if(node.getA()>0&&node.getC()<contains[2]){
                if(node.getA()+node.getC()<=contains[2])
                    workingList.add(new Node(0,node.getB(),node.getA()+node.getC(),node));
                if(node.getA()+node.getC()>contains[2])
                    workingList.add(new Node((node.getA()-(contains[2]-node.getC())),node.getB(),contains[2],node));
            }
            //B-C
            if(node.getB()>0&&node.getC()<contains[2]){
                if(node.getB()+node.getC()<=contains[2])
                    workingList.add(new Node(node.getA(),0,node.getB()+node.getC(),node));
                if(node.getB()+node.getC()>contains[2])
                    workingList.add(new Node(node.getA(),node.getB()-(contains[2]-node.getC()),contains[2],node));
            }
            //B-A
            if(node.getB()>0&&node.getA()<contains[0]){
                if(node.getB()+node.getA()<=contains[0])
                    workingList.add(new Node(node.getA()+node.getB(),0,node.getC(),node));
                if(node.getB()+node.getA()>contains[0])
                    workingList.add(new Node(contains[0],node.getB()-(contains[0]-node.getA()),node.getC(),node));
            }
            //C-A
            if(node.getC()>0&&node.getA()<contains[0]){
                if(node.getC()+node.getA()<=contains[0])
                    workingList.add(new Node(node.getA()+node.getC(),node.getB(),0,node));
                if(node.getC()+node.getA()>contains[0])
                    workingList.add(new Node(contains[0],node.getB(),node.getC()-(contains[0]-node.getA()),node));
            }
            //C-B
            if(node.getC()>0&&node.getB()<contains[1]){
                if(node.getC()+node.getB()<=contains[1])
                    workingList.add(new Node(node.getA(),node.getB()+node.getC(),0,node));
                if(node.getC()+node.getA()>contains[0])
                    workingList.add(new Node(node.getA(),contains[2],node.getC()-(contains[1]-node.getB()),node));
            }
        }
    }
    //用递归实现对路径的输出
    private void printRoute(Node node) {
        if (node.getParent() != null) {  //如果此节点的父节点为空,那么就可以输出该点,否则就对他的父节点执行输出操作
            printRoute(node.getParent());
            System.out.println(node);
        }
        else {
            System.out.println(node);
        }
    }
}

/**
 * @author z
 */
class Node {
    private int A;
    private int B;
    private int C;
    private Node parent;

    Node(int A, int B, int C) {    //构造函数
        this.A = A;
        this.B = B;
        this.C = C;
    }

    Node(int A, int B, int C, Node parent) { //构造函数
        this.A = A;
        this.B = B;
        this.C = C;
        this.parent = parent;
    }

    //复写equal，判断是否相等
    @Override
    public boolean equals(Object obj) {
        boolean flag=false;
        if (obj instanceof Node) {
            if (A == ((Node) obj).A && B == ((Node) obj).B && C == ((Node) obj).C)
                flag=true;
        }
        return flag;
    }

    @Override                    //便于输出node
    public String toString() {
        return ""+A+","+B+","+C;
    }

    boolean contain(int a){
            return A==a||B==a||C==a;
    }

    int getA() {
        return A;
    }

    void setA(int A){
        this.A=A;
    }

    int getB() {
        return B;
    }

    void setB(int B){
        this.B=B;
    }

    int getC() {
        return C;
    }

    void setC(int C){
        this.C=C;
    }

    Node getParent() {
        return parent;
    }

}
