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

    public Saves(){
        String fileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String filePath = dirPath + fileName + fileType;
        file = new File(filePath);
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

    public static void write(String str , String str2){
        try {
            fw = new FileWriter(file, true);
            fw.write(str + ": " + str2 + "\n");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(String str , Color c){
        String nc = c.toString();
        nc = nc.replaceAll("java.awt.Color" , "");
        try {
            fw = new FileWriter(file, true);
            fw.write(str + " Color: " + nc + "\n");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
