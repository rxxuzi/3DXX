package main;

import java.awt.*;

public class Floor {
    int x;
    int y;
    int width;
    int height;
    Floor(int x, int y, int width , int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        int d = 2;
        for(int i = x ; i < width ; i += d) {
            for(int j = y  ; j < height ; j += d) {
                if((i+j)/2%2 == 0) {
                    Screen.DPolygons.add(new DPolygon(new double[]{i, i, i+d, i+d} , new double[] {j, j+d, j+d, j},new double[] {0,0,0,0}, new Color(255 ,179 ,219), false));
                }else {
                    Screen.DPolygons.add(new DPolygon(new double[]{i, i, i+d, i+d} , new double[] {j, j+d, j+d, j},new double[] {0,0,0,0}, new Color(179 ,236 ,255), false));
                }
            }
        }
    }
}
