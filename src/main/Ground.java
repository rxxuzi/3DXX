package main;

import java.awt.*;
import java.util.Random;

public class Ground {

    public static final int SIZE = 2; //ïù

    public static final int MAP_SIZE = 50;
    public static final int Maximum_Height = 2;

    public static boolean Debug = false;

    public static int RATIO = 10; //max 100

    static double ROUGHNESS = 3; //ó≤ãNó¶

    Random r;
    static Color G = new Color(155, 155, 155);
//	static Color G = new Color(120, 100, 80);

    public Ground() {
        r = new Random();
        double[] values1 = new double[MAP_SIZE];
        double[] values2 = new double[values1.length];

        for (int y = 0; y < MAP_SIZE/2; y+=2){
            for(int x = 0; x < MAP_SIZE; x++){
                values1[x] = values2[x];
                if(r.nextInt(100) > (100 - RATIO)){
                    values2[x] = r.nextInt(Maximum_Height) + 3;
                }else {
                    values2[x] = r.nextDouble() * ROUGHNESS;
                }
            }

            if(y != 0){
                //ó≤ãNÇµÇΩÉ|ÉäÉSÉì
                for (int x = 0; x < MAP_SIZE/2; x++) {
                    Screen.DPolygons.add(new DPolygon(new double[]{(SIZE * x), (SIZE * x), SIZE + (SIZE * x)}, new double[]{(SIZE * y), SIZE + (SIZE * y), SIZE + (SIZE * y)}, new double[]{values1[x], values2[x], values2[x+1]}, G, false));
                    Screen.DPolygons.add(new DPolygon(new double[]{(SIZE * x), SIZE + (SIZE * x), SIZE + (SIZE * x)}, new double[]{(SIZE * y), SIZE + (SIZE * y), (SIZE * y)}, new double[]{values1[x], values2[x+1], values1[x+1]}, G, false));
                    G = new Color(200, 255, 250);
                }
            }

            for(int i = 0; i < MAP_SIZE; i++){
                values1[i] = values2[i];
                values2[i] = r.nextDouble()* ROUGHNESS;
            }

            if(y != 0){
                for (int x = 0; x < values1.length/2; x++) {
                    Screen.DPolygons.add(new DPolygon(new double[]{(SIZE * x), (SIZE * x), SIZE + (SIZE * x)}, new double[]{(SIZE * (y+1)), SIZE + (SIZE * (y+1)), SIZE + (SIZE * (y+1))}, new double[]{values1[x], values2[x],  values2[x+1]}, G, false));
                    G = new Color(200, 255, 50);
                    Screen.DPolygons.add(new DPolygon(new double[]{(SIZE * x), SIZE + (SIZE * x), SIZE + (SIZE * x)}, new double[]{(SIZE * (y+1)), SIZE + (SIZE * (y+1)), (SIZE * (y+1))}, new double[]{values1[x], values2[x+1],  values1[x+1]}, G, false));
                }
            }
        }
    }

}
