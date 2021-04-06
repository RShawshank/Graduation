package path_finding;

import java.awt.*;
import java.beans.Visibility;

public class Test {

    //static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
        Test test = new Test();
        test.test();
    }
    public void test(){
        visionablePathing_map();
        //array_map();
    }
    public void visionablePathing_map()
    {
        visionablePathing visionablePathing = new visionablePathing();
        visionablePathing.startrun();
    }
    public void opencvTest()
    {
        opencv_map opencv_map=new opencv_map();
        opencv_map.loadmap("C:\\Users\\rao\\Desktop\\1.png");
        int[][] originmap=opencv_map.createArrayMap();
        showmap(originmap);
        Map map = new Map();
        map.loadcurmap(originmap);
        map.runwithAStar();
    }
    public void array_map_3D()
    {
        Map_3D map_3D = new Map_3D();
        map_3D.init();
        map_3D.createmap();
        map_3D.runwithAStar();
    }
    public void array_map()
    {
        Map map = new Map();
        map.init();
        map.randcreatemap();
        //map.showmap();
        //int lengthx = map.getLength_x();
        //int lengthy = map.getLength_y();
        //int[][] originmap = map.getOriginmap();
        map.runwithAStar();
    }
    public static void showmap(int[][]map)
    {
        System.out.println("原地图长为："+(map.length-1)+",宽为："+(map[0].length-1)+",显示如下：");
        for(int i=1;i<map.length;i++){
            for(int j=1;j<map[0].length;j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
