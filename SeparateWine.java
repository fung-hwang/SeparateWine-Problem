import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SeparateWine {
    //分酒
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("从大到小输入三个容器的容量：");
        int contain_1 = input.nextInt();
        int contain_2 = input.nextInt();
        int contain_3 = input.nextInt();
        System.out.println("依次输入三个容器的初始状态：");
        int first_1 = input.nextInt();
        int first_2 = input.nextInt();
        int first_3 = input.nextInt();
        System.out.println("输入目标量");
        int key = input.nextInt();
        new solve(contain_1, contain_2, contain_3, first_1, first_2, first_3, key);
    }
}

//面向过程的解决方案
    class solve {
        //保存每一次的结果
        private int[] state=new int[3];
        //最终存在的数字
        private int key;
        //初始大小
        private int[] size=new int[3];
        //历史记录
        private List<String> history=new ArrayList<>();
        //过程
        private List<Integer> course=new ArrayList<>();
        //存储全部解
        private List<String> allResult=new ArrayList<>();

        solve(int a1, int a2, int a3, int a4, int a5, int a6, int a7){
            size[0]=a1;
            size[1]=a2;
            size[2]=a3;
            state[0]=a4;
            state[1]=a5;
            state[2]=a6;
            key =a7;
            //添加初始记录
            addHistory();
            addCourse();
            //go!!!
            handle();
            //择优输出
            output();
        }

        private void handle(){
            if(findKey()){
                storeCourse();
                return;
            }
            int origin_1,origin_2,origin_3;
            origin_1= state[0];
            origin_2= state[1];
            origin_3= state[2];
            int[] saocaozuo={1,2,0,2,0,1};
            for(int i=0;i<6;i++){
                //六种情况分别为：A->B,A->C,B->A,B->C,C->A,C->B
                //移动
                if(!move(i/2,saocaozuo[i])){
                    //移动失败
                    continue;
                }
                //是否存在该记录（剪枝）
                if(isInHistory()){
                    //如果移动后的状态重复history，则恢复原来的状态
                    state[0]=origin_1;
                    state[1]=origin_2;
                    state[2]=origin_3;
                    continue;
                }
                else{
                    //若不重复，添加有效记录
                    addHistory();
                    addCourse();
                }

                //递归
                handle();
                //恢复到原状态
                state[0]=origin_1;
                state[1]=origin_2;
                state[2]=origin_3;
                deleteCourse();
                //deleteHistory是为了求所有情况
                //如果需要在过程中剪去重复状态，可不使用deleteHistory
                deleteHistory();
            }
        }
        //通过a杯装b杯
        private boolean move(int a,int b){
            //a酒杯是空酒杯
            if(state[a]==0) return false;
            //b酒杯是满酒杯
            if(state[b]==size[b]) return false;

            //b酒杯的剩余空间大于等于a酒杯的
            if(size[b]- state[b]>= state[a]){
                state[b]+= state[a];
                state[a]=0;
            }
            //a酒杯减去b酒杯的剩余容量
            else{
                state[a]-=(size[b]- state[b]);
                //b酒杯装满
                state[b]=size[b];
            }
            return true;
        }
        //将当前状态添加到记录中
        private boolean isInHistory(){
            String string= state[0]+","+ state[1]+","+ state[2]+",";
            return history.contains(string);
        }
        //当前状态是否存在于历史记录中
        private void addHistory(){
            String string= state[0]+","+ state[1]+","+ state[2]+",";
            history.add(string);
        }
        //删除记录
        private void deleteHistory(){
            history.remove(history.size()-1);
        }
        //添加过程
        private void addCourse(){
            for(int i = 0; i< state.length; i++){
                course.add(state[i]);
            }
        }
        //删除过程
        private void deleteCourse(){
            int length=course.size();
            for(int i=length-1;i>length-4;i--){
                course.remove(i);
            }
        }
        //当前结果是否是最终结果了
        private boolean findKey(){
            for(int i = 0; i< state.length; i++){
                if(state[i]== key){return true;}
            }
            return false;
        }
        //存储结果
        private void storeCourse(){
            allResult.add(String.join("",history));
        }

        private void output(){
            int min=0;
            for(int i=1;i<allResult.size();i++){
                if(allResult.get(i).length()<allResult.get(min).length()){
                    min=i;
                }
            }
            //按步骤输出
            System.out.println("步骤最少的移动方案：");
            String[] strings=allResult.get(min).split(",");
            for(int i=0;i<strings.length;i++){
                if(i%3==2){
                    System.out.print(strings[i]+"\n");
                }
                else {
                    System.out.print(strings[i] + ",");
                }
            }
        }
    }

