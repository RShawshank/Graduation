package path_finding;

import java.util.Random;
import java.util.Scanner;

public class Map_3D {
    private int length_x;
    private int length_y;
    private int length_z;
    private int source_x;
    private int source_y;
    private int source_z;
    private int target_x;
    private int target_y;
    private int target_z;
    private int[][][]originmap;
    private A_Star_3D a_star_3D;
    public Map_3D(){}
    public void init() {
        Scanner in = new Scanner(System.in);
        System.out.println("请输入地图长度：");
        length_x = in.nextInt();
        System.out.println("请输入地图宽度：");
        length_y = in.nextInt();
        System.out.println("请输入地图高度：");
        length_z = in.nextInt();
        System.out.println("请输入起点坐标：");
        source_x = in.nextInt();
        source_y = in.nextInt();
        source_z = in.nextInt();
        System.out.println("请输入终点坐标：");
        target_x = in.nextInt();
        target_y = in.nextInt();
        target_z = in.nextInt();
        originmap = new int[length_x + 1][length_y + 1][length_z+1];
        a_star_3D = new A_Star_3D(source_x, source_y,source_z,target_x,target_y,target_z);
        a_star_3D.initgragh(length_x, length_y,length_z);
    }

    public void createmap() {
        //随机生成
        /*
        System.out.println("输入地图：0表示障碍，1表示空位");
        for(int i=1;i<=length_x;i++)
            for(int j=1;j<=length_y;j++)
                a_star.setVertex(i,j,in.nextInt());
*/
        while (true) {
            randmap(length_x, length_y,length_z ,source_x, source_y,source_z, target_x, target_y,target_z, originmap);

            loadmap_Astar(length_x,length_y,length_z,source_x,source_y,source_z,target_x,target_y,target_z,a_star_3D,originmap);
            if (a_star_3D.search_3D() != false) {
                break;
            }
        }
    }
    public void runwithAStar(){
        long start, end;
        start = System.currentTimeMillis();
        loadmap_Astar(length_x,length_y,length_z,source_x,source_y,source_z,target_x,target_y,target_z,a_star_3D,originmap);
        a_star_3D.search_3D();
        System.out.println("A_Star_3D算法的路径显示：");
        a_star_3D.printfpath_3D();
        end = System.currentTimeMillis();
        System.out.println("A_Star_3D算法路径显示如下：");
        a_star_3D.showmap_3D();
        System.out.println("A_Star_3D算法运行时长：");
        System.out.println("start time:" + start+ "; end time:" + end+ "; Run Time:" + (end - start) + "(ms)");

    }

    //随机生成一个map
    public static void  randmap(int length_x,int length_y,int length_z,int source_x,int source_y,int source_z,int target_x,int target_y,int target_z,int[][][]map)
    {
        Random rand = new Random( );
        for(int i=1;i<=length_x;i++)
            for(int j=1;j<=length_y;j++)
                for(int k=1;k<=length_z;k++)
                {
                    map[i][j][k]=rand.nextInt(2);
                }
        map[source_x][source_y][source_z]=1;map[target_x][target_y][target_z]=1;

    }
    //将map装载到Astar算法中
    public static void loadmap_Astar(int length_x,int length_y,int length_z,int source_x,int source_y,int source_z,int target_x,int target_y,int target_z,A_Star_3D a_star_3D,int[][][]map)
    {
        for(int i=1;i<=length_x;i++)
            for(int j=1;j<=length_y;j++)
            for(int k=1;k<=length_z;k++)
            {
                a_star_3D.setVertex_3D(i,j,k,map[i][j][k]);
            }
        a_star_3D.setVertex_3D(source_x,source_y,source_z, 1);
        a_star_3D.setVertex_3D(target_x,target_y,target_z,1);
    }

    public static void showmap_3D(int length_x,int length_y,int length_z,int[][][]map)
    {
        System.out.println("原地图显示如下：");
        for(int i=1;i<length_x+1;i++){
            for(int j=1;j<length_y+1;j++) {
                for (int k=1;k<length_z+1;k++)
                System.out.print(map[i][j][k] + " ");
            }
            System.out.println();
        }
    }

    public int getLength_x() {
        return length_x;
    }

    public int getLength_y() {
        return length_y;
    }
    public int[][][] getOriginmap() {
        return originmap;
    }
}
