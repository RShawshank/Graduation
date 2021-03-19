package A_Star;

import java.util.Random;
import java.util.Scanner;

public class A_StarTest {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.println("请输入地图长度：");
        int length_x = in.nextInt();
        System.out.println("请输入地图宽度：");
        int length_y = in.nextInt();
        System.out.println("请输入起点坐标：");
        int source_x=in.nextInt();
        int source_y=in.nextInt();
        System.out.println("请输入终点坐标：");
        int target_x=in.nextInt();
        int target_y=in.nextInt();
        int[][]mapshow=new int[length_x+1][length_y+1];
        A_Star a_star=new A_Star(source_x,source_y,target_x,target_y);

        a_star.initgragh(length_x,length_y);
        /*
        System.out.println("输入地图：0表示障碍，1表示空位");
        for(int i=1;i<=length_x;i++)
            for(int j=1;j<=length_y;j++)
                a_star.setVertex(i,j,in.nextInt());
*/
        //随机生成

        long start,end;
        start = System.currentTimeMillis();
            do {
                randmap(length_x,length_y,source_x,source_y,target_x,target_y,a_star,mapshow);
            }while (a_star.search()==false);
        System.out.println("原地图显示如下：");
        showmap(length_x,length_y,mapshow);
        end = System.currentTimeMillis();
        System.out.println("路径显示如下：");
           a_star.showmap();
            System.out.println("运行时长：");
        System.out.println("start time:" + start+ "; end time:" + end+ "; Run Time:" + (end - start) + "(ms)");

    }
    public static void  randmap(int length_x,int length_y,int source_x,int source_y,int target_x,int target_y,A_Star a_star,int[][]map)
    {
        Random rand = new Random( );
        for(int i=1;i<=length_x;i++)
            for(int j=1;j<=length_y;j++)
            {
                a_star.setVertex(i,j,map[i][j]);
                map[i][j]=rand.nextInt(2);
            }
        map[source_x][source_y]=1;map[target_x][target_y]=1;
        a_star.setVertex(source_x,source_y, 1);
        a_star.setVertex(target_x,target_y,1);
    }
    public static void showmap(int length_x,int length_y,int[][]map)
    {
        for(int i=1;i<length_x+1;i++){
            for(int j=1;j<length_y+1;j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

}
