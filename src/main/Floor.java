package main;

import java.awt.*;

public class Floor {
    Floor(){
        int d = 2;
        for(int i = 0 ; i < 50 ; i += d) {
            for(int j = -20  ; j < 40 ; j += d) {
                if((i+j)/2%2 == 0) {
                    Screen.DPolygons.add(new DPolygon(new double[]{i, i, i+d, i+d} , new double[] {j, j+d, j+d, j},new double[] {0,0,0,0}, new Color(255 ,179 ,219), false));
                }else {
                    Screen.DPolygons.add(new DPolygon(new double[]{i, i, i+d, i+d} , new double[] {j, j+d, j+d, j},new double[] {0,0,0,0}, new Color(179 ,236 ,255), false));
                }
            }
        }
    }
}
