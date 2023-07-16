package jet;

import main.Cube;
import main.Screen;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader3dx {
    public static int overlapCnt = 0;
    public static int cubeCnt = 0;
    public static ArrayList<Obj3D> obj = new ArrayList<>();
    public static List<Cube> cubeList = new ArrayList<>();

    public static void run(){
        String path = "./data/test.3dx";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 11) {
                    double x = Double.parseDouble(tokens[0]);
                    double y = Double.parseDouble(tokens[1]);
                    double z = Double.parseDouble(tokens[2]);
                    double dx = Double.parseDouble(tokens[3]);
                    double dy = Double.parseDouble(tokens[4]);
                    double dz = Double.parseDouble(tokens[5]);
                    int r = Integer.parseInt(tokens[6]);
                    int g = Integer.parseInt(tokens[7]);
                    int b = Integer.parseInt(tokens[8]);
                    Color color = new Color(r, g, b);
                    boolean boolValue = Boolean.parseBoolean(tokens[9]); // move
                    boolean boolValue2 = Boolean.parseBoolean(tokens[10]);

                    boolean overlap = false;

                    for (Cube cube : Screen.Cubes) {
                        if(cube.overlap(x,y,z,dx,dy,dz)){
                            overlapCnt ++ ;
                            overlap = true;
                            break;
                        }
                    }

                    if(!overlap){
                        if(!boolValue && boolValue2){
                            Screen.Cubes.add(new Cube(x,y,z,dx,dy,dz,color,boolValue,boolValue2));
                        }
                    }
                }else {
                    System.out.println("Invalid input format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Obj3D {
    double x,y,z;
    double dx,dy,dz;
    boolean fixation, delete;
    Color color;
    Obj3D(double x, double y, double z, double dx, double dy, double dz, Color color, boolean fixation , boolean delete) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
        this.color = color;
        this.fixation = fixation;
        this.delete = delete;
    }

    @Override
    public String toString() {
        return "Obj3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", dx=" + dx +
                ", dy=" + dy +
                ", dz=" + dz +
                ", color=" + color +
                ", fixation=" + fixation +
                ", delete=" + delete +
                '}';

    }
}
