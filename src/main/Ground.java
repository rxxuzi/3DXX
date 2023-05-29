package main;

import java.awt.*;
import java.util.Random;

public class Ground {
    public static final int SIZE = 2; //ïù

    public static final int HEIGHT = 20;
    public static final int WIDTH = 100;
    public static final int GROUND_X = 0;
    public static final int GROUND_Y = Ground.HEIGHT - 1;
    public static final int MAP_SIZE = 50;
    public static final int Maximum_Height = 10;

    public static final int GROUND_WIDTH = Ground.WIDTH * Ground.SIZE;
    public static final int GROUND_HEIGHT = Ground.HEIGHT * Ground.SIZE;

    public static final int GROUND_X_START = Ground.GROUND_X * Ground.SIZE;
    public static final int GROUND_Y_START = Ground.GROUND_Y * Ground.SIZE;

    public static final int GROUND_X_END = Ground.GROUND_X_START + Ground.GROUND_WIDTH;
    public static final int GROUND_Y_END = Ground.GROUND_Y_START + Ground.GROUND_HEIGHT;

    public static final int GROUND_X_CENTER = Ground.GROUND_X_START + (Ground.GROUND_WIDTH / 2);
    public static final int GROUND_Y_CENTER = Ground.GROUND_Y_START + (Ground.GROUND_HEIGHT / 2);

    static double ROUGHNESS = 2d; //ó≤ãN

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
                if(r.nextInt(15) > 13){
//                    values2[x] = r.nextDouble() * ROUGHNESS * 5;
                    values2[x] = Maximum_Height;
                    System.out.println(y +"," + x);
                }else {
                    values2[x] = r.nextDouble() * ROUGHNESS;
                }
            }

            if(y != 0){
                for (int x = 0; x < MAP_SIZE/2; x++) {
                    Screen.DPolygons.add(new DPolygon(new double[]{(SIZE * x), (SIZE * x), SIZE + (SIZE * x)}, new double[]{(SIZE * y), SIZE + (SIZE * y), SIZE + (SIZE * y)}, new double[]{values1[x], values2[x], values2[x+1]}, G, false));
                    Screen.DPolygons.add(new DPolygon(new double[]{(SIZE * x), SIZE + (SIZE * x), SIZE + (SIZE * x)}, new double[]{(SIZE * y), SIZE + (SIZE * y), (SIZE * y)}, new double[]{values1[x], values2[x+1], values1[x+1]}, G, false));
                }
            }

            for(int i = 0; i < values1.length; i++){
                values1[i] = values2[i];
                values2[i] = r.nextDouble()* ROUGHNESS;
            }

            if(y != 0){
                for (int x = 0; x < values1.length/2; x++) {
                    Screen.DPolygons.add(new DPolygon(new double[]{(SIZE * x), (SIZE * x), SIZE + (SIZE * x)}, new double[]{(SIZE * (y+1)), SIZE + (SIZE * (y+1)), SIZE + (SIZE * (y+1))}, new double[]{values1[x], values2[x],  values2[x+1]}, G, false));
                    Screen.DPolygons.add(new DPolygon(new double[]{(SIZE * x), SIZE + (SIZE * x), SIZE + (SIZE * x)}, new double[]{(SIZE * (y+1)), SIZE + (SIZE * (y+1)), (SIZE * (y+1))}, new double[]{values1[x], values2[x+1],  values1[x+1]}, G, false));
                }
            }
        }
    }

}
