package path_finding;

import java.util.Random;
import java.util.Scanner;

public class Map {
    private int length_x;
    private int length_y;
    private int source_x;
    private int source_y;
    private int target_x;
    private int target_y;
    private int[][]originmap;
    private int[][]curmap;
    private A_Star a_star;
    private Dijkstra dijkstra;
    public Map(){}
    public void init() {
        Scanner in = new Scanner(System.in);
        System.out.println("请输入地图长度：");
        length_x = in.nextInt();
        System.out.println("请输入地图宽度：");
        length_y = in.nextInt();
        System.out.println("请输入起点坐标：");
        source_x = in.nextInt();
        source_y = in.nextInt();
        System.out.println("请输入终点坐标：");
        target_x = in.nextInt();
        target_y = in.nextInt();
        originmap = new int[length_x + 1][length_y + 1];
        a_star = new A_Star(source_x, source_y, target_x, target_y);
        dijkstra = new Dijkstra(source_x, source_y, target_x, target_y);
        a_star.initgragh(length_x, length_y);
        dijkstra.initgragh(length_x, length_y);
    }

    public void randcreatemap() {
        //随机生成

        while (true) {
            randmap(length_x, length_y, source_x, source_y, target_x, target_y, originmap);

            loadmap_Astar(length_x, length_y, source_x, source_y, target_x, target_y, a_star, originmap);
            if (a_star.search() != false) {
                break;
            }
        }
    }
    public void loadcurmap(int[][]map)
    {
        Scanner in = new Scanner(System.in);

        length_x=map.length-1;
        length_y=map[0].length-1;
        System.out.println("请输入起点坐标：");
        source_x = in.nextInt();
        source_y = in.nextInt();
        System.out.println("请输入终点坐标：");
        target_x = in.nextInt();
        target_y = in.nextInt();
        a_star = new A_Star(source_x, source_y, target_x, target_y);
        originmap = map;
        //dijkstra = new Dijkstra(source_x, source_y, target_x, target_y);
        a_star.initgragh(length_x, length_y);
        while (true) {
            loadmap_Astar(length_x, length_y, source_x, source_y, target_x, target_y, a_star, originmap);
            if (a_star.search() != false) {
                break;
            }
            else
            {
                System.out.println("不存在该路径！");
                return;
            }
        }
    }
    public void runwithAStar(){
        long start, end;
        start = System.currentTimeMillis();
        loadmap_Astar(length_x,length_y,source_x,source_y,target_x,target_y,a_star,originmap);
        a_star.setCurmap(originmap);
        a_star.search();
        System.out.println("A_Star算法的路径显示：");
        a_star.printfpath();
        end = System.currentTimeMillis();
        System.out.println("A_Star算法路径显示如下：");
        a_star.showmap();
        curmap=a_star.getCurmap();
        System.out.println("A_Star算法运行时长：");
        System.out.println("start time:" + start+ "; end time:" + end+ "; Run Time:" + (end - start) + "(ms)");

}
    public void runwithDijkstra() {
        long start, end;
        start = System.currentTimeMillis();
        loadmap_D(length_x,length_y,source_x,source_y,target_x,target_y,dijkstra,originmap);
        dijkstra.setCurmap(originmap);
        dijkstra.search();
        end = System.currentTimeMillis();
        System.out.println("dijkstra算法路径显示如下：");
        dijkstra.showmap();
        curmap=dijkstra.getCurmap();
        System.out.println("dijkstra算法运行时长：");
        System.out.println("start time:" + start+ "; end time:" + end+ "; Run Time:" + (end - start) + "(ms)");
    }
        //随机生成一个map
    public static void  randmap(int length_x,int length_y,int source_x,int source_y,int target_x,int target_y,int[][]map)
    {
        Random rand = new Random( );
        for(int i=1;i<=length_x;i++)
            for(int j=1;j<=length_y;j++)
            {
                map[i][j]=rand.nextInt(2);
            }
        map[source_x][source_y]=3;map[target_x][target_y]=4;

    }
    //将map装载到Astar算法中
    public static void loadmap_Astar(int length_x,int length_y,int source_x,int source_y,int target_x,int target_y,A_Star a_star,int[][]map)
    {
        for(int i=1;i<=length_x;i++)
            for(int j=1;j<=length_y;j++)
            {
                a_star.setVertex(i,j,map[i][j]);
            }
    }
    //将map装载到D算法中
    public  void loadmap_D(int length_x,int length_y,int source_x,int source_y,int target_x,int target_y,Dijkstra dijkstra,int[][]map)
    {
        for(int i=1;i<=length_x;i++)
            for(int j=1;j<=length_y;j++)
            {
                dijkstra.setVertex(i,j,map[i][j]);
            }
        //dijkstra.setVertex(source_x,source_y,1);
        //dijkstra.setVertex(target_x,target_y,1);
    }
    public  void showmap()
    {
        System.out.println("原地图显示如下：");
        for(int i=1;i<length_x+1;i++){
            for(int j=1;j<length_y+1;j++) {
                System.out.print(originmap[i][j] + " ");
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
    public int[][] getOriginmap() {
        return originmap;
    }
}
