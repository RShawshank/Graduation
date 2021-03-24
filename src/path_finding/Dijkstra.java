package path_finding;

import java.util.ArrayList;
import java.util.Stack;

public class Dijkstra {
    private int source_x;//起点x坐标
    private int source_y;
    private int target_x;
    private int target_y;
    private int length_x;
    private int length_y;
    private Vertex[][]map;//0表示障碍。1表示路径
    private ArrayList<Vertex>openlist;
    public Dijkstra(int source_x,int source_y,int target_x,int target_y)
    {
        this.source_x=source_x;
        this.source_y=source_y;
        this.target_x=target_x;
        this.target_y=target_y;
        this.openlist=new ArrayList<Vertex>();
    }
    public void initgragh(int lengthx,int lengthy)
    {
        this.map = new Vertex[lengthx+1][lengthy+1];
        this.length_x=lengthx;
        this.length_y=lengthy;
    }
    public void setVertex(int x,int y,int id)
    {
        map[x][y]=new Vertex(x,y,id);
    }
    public void initdist(int x,int y) {
        //对X,Y点周边的点进行初始化，其他点的G值保持MAX_VALUE
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++) {
                if (x + i > 0 && x + i <=length_x && y + j > 0 && y + j <= length_y && map[x + i][y + j].id == 1) {
                    if (Math.abs(i) + Math.abs(j) == 2&&map[x + i][y + j].Gdistance > map[x][y].Gdistance+14)
                    {
                        map[x + i][y + j].Gdistance = map[x][y].Gdistance + 14;
                        map[x + i][y + j].path_father = map[x][y];
                    }
                    else if(Math.abs(i) + Math.abs(j) == 1&&map[x + i][y + j].Gdistance > map[x][y].Gdistance+10){
                        map[x + i][y + j].Gdistance = map[x][y].Gdistance + 10;
                        map[x + i][y + j].path_father = map[x][y];
                    }

                }
            }
        }
    }
    public void DshowMap()
    {
        Stack<Vertex> path = new Stack<Vertex>();
        Vertex curnow = map[target_x][target_y];
        while(curnow.path_father!=null) {
            path.push(curnow);
            curnow = curnow.path_father;
        }
        Vertex outputVertex = null;
        System.out.print("("+source_x+","+source_y+")->");
        map[source_x][source_y].id=2;
        while(!path.isEmpty())
        {
            outputVertex=path.pop();
            System.out.print("("+outputVertex.x+","+outputVertex.y+")->");
            map[outputVertex.x][outputVertex.y].id=2;
        }
        System.out.println("("+target_x+","+target_y+")");
        map[target_x][target_y].id=2;
    }
    public void showmap()
    {
        for(int i=1;i<length_x+1;i++){
            for(int j=1;j<length_y+1;j++) {
                System.out.print(map[i][j].id + " ");
            }
            System.out.println();
        }
    }
    public boolean search()
    {

        int []temp = new int[2];
        map[source_x][source_y].Gdistance=0;
        map[source_x][source_y].isBelong=false;
        initdist(source_x,source_y);
        for (int m = 1; m <= length_x*length_y; m++){
            long min = Long.MAX_VALUE;
            //从closelist中找到距离起点最近的节点
            for (int i = 1; i <= length_x; i++) {
                for (int j = 1; j <= length_y; j++) {
                    if (map[i][j].isBelong && map[i][j].id == 1) {
                        if (map[i][j].Gdistance < min) {
                            min = map[i][j].Gdistance;
                            temp[0] = i;
                            temp[1] = j;
                        }
                    }
                }
            }
            map[temp[0]][temp[1]].isBelong=false;
            //对最近路径和父节点进行更新
                initdist(temp[0], temp[1]);
                if(temp[0]==target_x&&temp[1]==target_y)
                {
                    System.out.println("dijkstra算法的路径显示：");
                    DshowMap();
                    return true;
                }

        }
        return true;
    }

}
