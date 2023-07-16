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
    public Objct3DX() {
        file = new File(filepath);
        if (file.exists()) {
            if(file.delete()){
                System.out.println("file deleted");
            }
        }
        //make directory
        File dir = new File(dirPath);
        if (!dir.exists()) {
            if(dir.mkdirs()){
                System.out.println("make directory");
            }
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
