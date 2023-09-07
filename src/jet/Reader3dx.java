package jet;

import main.Cube;
import main.Screen;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 <h1>Read the file and add the cube to the screen.</h1>
 <h2>The file should be in the format: {@code  .3dx}</h2>
 <p><strong>Parameters:</strong></p>
 <ul>
 <li><strong>x, y, z, dx, dy, dz:</strong> Coordinates of the center of the cube.</li>
 <li><strong>r, g, b:</strong> RGB values of the cube.</li>
 <li><strong>move:</strong> A boolean value indicating whether the cube should be moved or not.</li>
 <li><strong>delete:</strong> A boolean value indicating whether the cube should be deleted or not.</li>
 </ul>
 @author rxxuzi
 @since 4.0.2
 */
public class Reader3dx {

    public static int overlapCnt = 0;
    public static String path = "./data/test.3dx";
    public static void run(){


        if(path.endsWith(".3dx")){
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if(line.startsWith("#")){
                        continue;
                    }
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
}