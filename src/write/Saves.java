package write;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public final class Saves {
    private int x;
    private int y;
    private int z;

    private static File file;
    private static FileWriter fw;

    static final String dirPath = "./rsc/log/data/";
    static final String fileType = ".log";
    private String fileName;
    private String filePath;

    public Saves(){
        fileName =  new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        filePath = dirPath + fileName + fileType;
        this.file = new File(filePath);

    }

    public static void write(String str){
        try {
            fw = new FileWriter(file, true);
            fw.write(str + "\n");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(String str , String str2){
        try {
            fw = new FileWriter(file, true);
            fw.write(str + ": " + str2 + "\n");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
