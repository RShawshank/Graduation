package path_finding;

import java.util.ArrayList;
import java.util.Stack;

public class A_Star_3D {
    private int source_x;//起点x坐标
    private int source_y;
    private int source_z;
    private int target_x;
    private int target_y;
    private int target_z;
    private int length_x;
    private int length_y;
    private int length_z;
    private Vertex[][][]map;//0表示障碍。1表示路径
    private ArrayList<Vertex> openlist;
    private Vertex finalVetex ;
    public A_Star_3D(int source_x,int source_y,int source_z,int target_x,int target_y,int target_z)
    {
        this.source_x=source_x;
        this.source_y=source_y;
        this.source_z=source_z;
        this.target_x=target_x;
        this.target_y=target_y;
        this.target_z=target_z;
        this.openlist=new ArrayList<Vertex>();
    }
    //计算h()
    public  long calcdistance(int x,int y,int z){
        return 10*(Math.abs(x-target_x)+Math.abs(y-target_y)+Math.abs(z-target_z));
    }
    public void initgragh(int lengthx,int lengthy,int lengthz)
    {
        this.map = new Vertex[lengthx+1][lengthy+1][lengthz+1];
        this.length_x=lengthx;
        this.length_y=lengthy;
        this.length_z=lengthz;
    }
    public void setVertex_3D(int x,int y,int z,int id)
    {
        map[x][y][z]=new Vertex(x,y,z,id);
    }
//一共有26种情况
    public void calF(int index, Vertex a, Vertex b)
    {
        if(index==2){
            a.Gdistance=b.Gdistance+14;
            a.Hdistance=10*(Math.abs(a.x-target_x)+Math.abs(a.y-target_y)+Math.abs(a.z-target_z));
            a.Fdistance=a.Gdistance+a.Hdistance;
            a.path_father=b;
        }
        else if(index==1)
        {
            a.Gdistance=b.Gdistance+10;
            a.Hdistance=10*(Math.abs(a.x-target_x)+Math.abs(a.y-target_y)+Math.abs(a.z-target_z));
            a.Fdistance=a.Gdistance+a.Hdistance;
            a.path_father=b;
        }
        else
        {
            a.Gdistance=b.Gdistance+17;
            a.Hdistance=10*(Math.abs(a.x-target_x)+Math.abs(a.y-target_y)+Math.abs(a.z-target_z));
            a.Fdistance=a.Gdistance+a.Hdistance;
            a.path_father=b;
        }
    }
    public boolean search_3D()
    {
        int i=0;
        if(map[source_x][source_y][source_z].id!=1)
        {
            System.out.println("开始起点设置错误！");return false;
        }
        openlist.add(map[source_x][source_y][source_z]);
        map[source_x][source_y][source_z].Hdistance = calcdistance(source_x,source_y,source_z);
        map[source_x][source_x][source_z].Gdistance=0;
        map[source_x][source_y][source_z].Fdistance=map[source_x][source_y][source_z].Hdistance+map[source_x][source_y][source_z].Gdistance;
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
                    for (int k=-1;k<2;k++)
                {
                    //到达终点
                    if(curVertex.x+i==target_x&&curVertex.y+j==target_y&&curVertex.z+k==target_z)
                    {
                        finalVetex=curVertex;
                        return true;
                    }
                    if (curVertex.x+i>0&&curVertex.y+j>0&&curVertex.x+i<length_x&&curVertex.y+j<length_y
                            &&curVertex.z+k>0&&curVertex.z+k<length_z
                            &&map[curVertex.x+i][curVertex.y+j][curVertex.z+k].id==1
                            &&map[curVertex.x+i][curVertex.y+j][curVertex.z+k].isBelong)
                    {
                        Vertex nextvertex = map[curVertex.x+i][curVertex.y+j][curVertex.z+k];
                        if(!openlist.contains(nextvertex))
                        {
                            //当待寻找的下一节点还不在openlist中
                            openlist.add(nextvertex);
                            nextvertex.path_father=curVertex;
                            //四周情况
                            calF(Math.abs(i)+Math.abs(j)+Math.abs(k),nextvertex,curVertex);
                        }
                        else
                        {
                            //当待寻找的节点在openlist,判断出最短路径
                            if(curVertex.Gdistance+14<nextvertex.Gdistance&&Math.abs(i)+Math.abs(j)+Math.abs(k)==2)
                                calF(Math.abs(i)+Math.abs(j)+Math.abs(k),nextvertex,curVertex);
                            else if(curVertex.Gdistance+10<nextvertex.Gdistance&&Math.abs(i)+Math.abs(j)+Math.abs(k)==1)
                                calF(Math.abs(i)+Math.abs(j)+Math.abs(k),nextvertex,curVertex);
                            else if(curVertex.Gdistance+17<nextvertex.Gdistance&&Math.abs(i)+Math.abs(j)+Math.abs(k)==3)
                                calF(Math.abs(i)+Math.abs(j)+Math.abs(k),nextvertex,curVertex);

                        }

                    }
                }

        }
        // System.out.println("不存在该路径！");
        return false;
    }
    public void printfpath_3D( )
    {
        Vertex curnow = finalVetex;
        Stack<Vertex> path = new Stack<Vertex>();
        while(curnow.path_father!=null)
        {
            path.push(curnow);
            curnow=curnow.path_father;
        }
        Vertex outputVertex = null;
        System.out.print("("+source_x+","+source_y+","+source_z+")->");
        map[source_x][source_y][source_z].id=2;
        while(!path.isEmpty())
        {
            outputVertex=path.pop();
            System.out.print("("+outputVertex.x+","+outputVertex.y+","+outputVertex.z+")->");
            map[outputVertex.x][outputVertex.y][outputVertex.z].id=2;
        }
        System.out.println("("+target_x+","+target_y+","+target_z+")");
        map[target_x][target_y][target_z].id=2;
    }

    public void showmap_3D()
    {
        for(int i=1;i<length_x+1;i++){
            for(int j=1;j<length_y+1;j++) {
                for (int k=1;k<length_z+1;k++)
                System.out.print(map[i][j][k].id + " ");
            }
            System.out.println();
        }
    }
}
