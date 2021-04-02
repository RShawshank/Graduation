package path_finding;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.*;

//二值化处理图像，将地图处理成01数组
public class opencv_map {
    private String binarymapPath="C:\\Users\\rao\\Desktop\\binarymap.png";
    private int[][]Arraymap;
    //载入原地图，将原地图进行黑白处理
    public void loadmap(String string)
    {
        opencv_map opencv_map = new opencv_map();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //加载时灰度
        Mat src = Imgcodecs.imread(string,Imgcodecs.IMREAD_GRAYSCALE);
        Mat target = new Mat();
        //二值化处理
        /*
        src 原图
        dst 输入图像
        thresh 当前阈值
        maxval 最大阈值一般为255
        type 阈值类型
        * */
        Imgproc.threshold(src,target,0,255,Imgproc.THRESH_BINARY|Imgproc.THRESH_OTSU);
        //对二值化后的图片进行保存
        Imgcodecs.imwrite(opencv_map.binarymapPath,target);
    }
    public int[][] createArrayMap()
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat mat = Imgcodecs.imread(this.binarymapPath);
        if (mat.empty()){
            return null;
        }
        int row = mat.rows();
        int col = mat.cols();
        this.Arraymap= new int[row+1][col+1];
        double[] temp = new double[3];
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                temp =mat.get(i, j);
                if (temp[0] == 255)
                    this.Arraymap[i+1][j+1] = 1;
                else this.Arraymap[i+1][j+1] = 0;
            }
        }
        return this.Arraymap;
    }
}
