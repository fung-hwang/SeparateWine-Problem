import java.util.Scanner;

public class TestWineQuestion {
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        System.out.println("从大到小输入三个容器的容量：");
        int contain1=input.nextInt();
        int contain2=input.nextInt();
        int contain3=input.nextInt();
        System.out.println("依次输入三个容器的初始状态：");
        int firstA=input.nextInt();
        int firstB=input.nextInt();
        int firstC=input.nextInt();
        System.out.println("输入目标量");
        int key=input.nextInt();
        WineQuestion wineQuestion = new WineQuestion(contain1,contain2,contain3,firstA,firstB,firstC,key);
        wineQuestion.solve();
    }
}
