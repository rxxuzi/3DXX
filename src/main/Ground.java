package main;

import java.awt.*;
import java.util.Random;

public class Ground {

    public static final int SIZE = 2; //幅

    public static final int HEIGHT = 50;
    public static final int WIDTH  = 50;
    public static final int GROUND_X = 0;
    public static final int GROUND_Y = 0;
    public static final int MAP_SIZE = 50;
    public static final int Maximum_Height = 5;

    public static boolean Debug = false;


    static double ROUGHNESS = 2d; //隆起

    Random r;
    static Color G = new Color(155, 155, 155);
//	static Color G = new Color(120, 100, 80);

    public Ground() {
        r = new Random();
        double[] values1 = new double[MAP_SIZE];
        double[] values2 = new double[values1.length];

        for (int y = 0; y < MAP_SIZE/2; y+=2){
            boolean[] arr = new boolean[MAP_SIZE]; //色を決める

            for(int x = 0; x < MAP_SIZE; x++){
                values1[x] = values2[x];
                if(r.nextInt(100) > 50){
                    values2[x] = r.nextInt(Maximum_Height) + 3;
                    arr[x] = true;
                }else {
                    values2[x] = r.nextDouble() * ROUGHNESS;
                    arr[x] = false;
                }
            }

            if(y != 0){
                //隆起したポリゴン
                for (int x = 0; x < MAP_SIZE/2; x++) {
                    Screen.DPolygons.add(new DPolygon(new double[]{(SIZE * x), (SIZE * x), SIZE + (SIZE * x)}, new double[]{(SIZE * y), SIZE + (SIZE * y), SIZE + (SIZE * y)}, new double[]{values1[x], values2[x], values2[x+1]}, G, false));
                    Screen.DPolygons.add(new DPolygon(new double[]{(SIZE * x), SIZE + (SIZE * x), SIZE + (SIZE * x)}, new double[]{(SIZE * y), SIZE + (SIZE * y), (SIZE * y)}, new double[]{values1[x], values2[x+1], values1[x+1]}, G, false));
                    if (arr[x]){

                        if(Debug){
                            G = new Color(200, 255, 50);
                        }else {
                            G = new Color(200, 255, 250);
                        }
//                        G = new Color(200, 255, 50);
                    }else {
                        G = new Color(150, 150, 150);
                    }
                }
            }

            for(int i = 0; i < MAP_SIZE; i++){
                values1[i] = values2[i];
                values2[i] = r.nextDouble()* ROUGHNESS;
            }

            if(y != 0){
                for (int x = 0; x < values1.length/2; x++) {
                    Screen.DPolygons.add(new DPolygon(new double[]{(SIZE * x), (SIZE * x), SIZE + (SIZE * x)}, new double[]{(SIZE * (y+1)), SIZE + (SIZE * (y+1)), SIZE + (SIZE * (y+1))}, new double[]{values1[x], values2[x],  values2[x+1]}, G, false));
                    if (arr[x]){
                        G = new Color(200, 255, 50);
                    }else {
                        G = new Color(150, 150, 150);
                    }
                    Screen.DPolygons.add(new DPolygon(new double[]{(SIZE * x), SIZE + (SIZE * x), SIZE + (SIZE * x)}, new double[]{(SIZE * (y+1)), SIZE + (SIZE * (y+1)), (SIZE * (y+1))}, new double[]{values1[x], values2[x+1],  values1[x+1]}, G, false));
                }
            }
        }
    }

}
