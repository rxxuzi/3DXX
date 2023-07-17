package shot;

import java.io.File;
import java.io.FileWriter;

public class Objct3DX {
    private static final String dirPath = "./data/";
    String filename = "test";
    private static final String fileType = ".3dx";
    String filepath = dirPath + filename + fileType;
    FileWriter fw = null;
    File file;
    public Objct3DX(){
        file = new File(filepath);
        try {
            fw = new FileWriter(file, true);
            fw.write("# This is Object storage file. \n");
            fw.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String data ) {
        try {
            fw = new FileWriter(file, true);
            fw.write(data + "\n");
            fw.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
