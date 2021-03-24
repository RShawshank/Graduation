package path_finding;

import com.sun.deploy.security.SelectableSecurityManager;

import java.util.ArrayList;
import java.util.Stack;

/*
*使用最小堆算法实现A*算法
* A*算法是一种启发式的路径搜索算法。对于地图中的每一个节点，我们记录起点到该节点的消耗g，
* 估算该节点到终点的消耗h（并不是准确值，有多种估算方法，简单的比如欧氏距离），
* 记两者之和f=g+h
* */
public class A_Star {
    private int source_x;//起点x坐标
    private int source_y;
    private int target_x;
    private int target_y;
    private int length_x;
    private int length_y;
    private Vertex[][]map;//0表示障碍。1表示路径
    private ArrayList<Vertex>openlist;
    private Vertex finalVetex ;
    public A_Star(int source_x,int source_y,int target_x,int target_y)
    {
        this.source_x=source_x;
        this.source_y=source_y;
        this.target_x=target_x;
        this.target_y=target_y;
        this.openlist=new ArrayList<Vertex>();
    }
    //计算h()
    public  long calcdistance(int x,int y){
        return 10*(Math.abs(x-target_x)+Math.abs(y-target_y));
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

    public void calF(int index, Vertex a, Vertex b)
    {
        if(index==2){
        a.Gdistance=b.Gdistance+14;
        a.Hdistance=10*(Math.abs(a.x-target_x)+Math.abs(a.y-target_y));
        a.Fdistance=a.Gdistance+a.Hdistance;
        a.path_father=b;
        }
        else
        {
            a.Gdistance=b.Gdistance+10;
            a.Hdistance=10*(Math.abs(a.x-target_x)+Math.abs(a.y-target_y));
            a.Fdistance=a.Gdistance+a.Hdistance;
            a.path_father=b;
        }
    }
    public boolean search()
    {
        int i=0;
        if(map[source_x][source_y].id!=1)
        {
            System.out.println("开始起点设置错误！");return false;
        }
        openlist.add(map[source_x][source_y]);
        map[source_x][source_y].Hdistance = calcdistance(source_x,source_y);
        map[source_x][source_x].Gdistance=0;
        map[source_x][source_y].Fdistance=map[source_x][source_y].Hdistance+map[source_x][source_y].Gdistance;
        long min = Long.MAX_VALUE;
        //找到openlist中的f值最小的点
        while(!openlist.isEmpty())
        {
            int cur = 0;
            for(i=0;i<openlist.size();i++)
            {
                if (openlist.get(i).Fdistance<min)
                {
                    cur=i;min=openlist.get(i).Fdistance;
                }
            }
            Vertex curVertex = openlist.get(cur);
            curVertex.isBelong=false;
            openlist.remove(cur);
            //对该节点的相邻方格进行操作：
            for(i=-1;i<2;i++)
                for (int j=-1;j<2;j++)
                {
                    //到达终点
                    if(curVertex.x+i==target_x&&curVertex.y+j==target_y)
                    {
                        finalVetex=curVertex;
                        return true;
                    }
                    if (curVertex.x+i>0&&curVertex.y+j>0&&curVertex.x+i<length_x&&curVertex.y+j<length_y
                    &&map[curVertex.x+i][curVertex.y+j].id==1
                    &&map[curVertex.x+i][curVertex.y+j].isBelong)
                    {
                        Vertex nextvertex = map[curVertex.x+i][curVertex.y+j];
                        if(!openlist.contains(nextvertex))
                        {
                            //当待寻找的下一节点还不在openlist中
                            openlist.add(nextvertex);
                            nextvertex.path_father=curVertex;
                            //四周情况
                                calF(Math.abs(i)+Math.abs(j),nextvertex,curVertex);
                        }
                        else
                        {
                            //当待寻找的节点在openlist,判断出最短路径
                            if(curVertex.Gdistance+14<nextvertex.Gdistance&&Math.abs(i)+Math.abs(j)==2)
                                calF(Math.abs(i)+Math.abs(j),nextvertex,curVertex);
                            else if(curVertex.Gdistance+10<nextvertex.Gdistance&&Math.abs(i)+Math.abs(j)!=2)
                                calF(Math.abs(i)+Math.abs(j),nextvertex,curVertex);

                        }

                    }
                }

        }
       // System.out.println("不存在该路径！");
        return false;
    }
    public void printfpath( )
    {
        Vertex curnow = finalVetex;
        Stack<Vertex> path = new Stack<Vertex>();
        while(curnow.path_father!=null)
        {
            path.push(curnow);
            curnow=curnow.path_father;
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

}
