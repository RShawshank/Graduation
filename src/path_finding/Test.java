package path_finding;

import java.awt.*;

public class Test {

    //static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
        Test test = new Test();
        test.test();
    }
    public void test(){
        /*
        Map map = new Map();
        map.init();
        map.createmap();
        //int lengthx = map.getLength_x();
        //int lengthy = map.getLength_y();
        //int[][] originmap = map.getOriginmap();
        map.runwithAStar();
/*
        Map_3D map_3D = new Map_3D();
        map_3D.init();
        map_3D.createmap();
        map_3D.runwithAStar();
*/
        opencv_map opencv_map=new opencv_map();
        opencv_map.loadmap("C:\\Users\\rao\\Desktop\\1.png");
        int[][] originmap=opencv_map.createArrayMap();
        showmap(originmap);
    }
    public static void showmap(int[][]map)
    {
        System.out.println("原地图显示如下：");
        for(int i=1;i<map.length+1;i++){
            for(int j=1;j<map[0].length+1;j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
