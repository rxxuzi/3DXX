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

    public static void write(String str , boolean n){
        //method name
        String className ;
        String methodName ;
        //line number
        int lineNumber ;
        //file name
        String fileName;
        //date
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //time
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        //stack trace
        String stackTrace ;



        StackTraceElement[] ste = new Throwable().getStackTrace();
        for (StackTraceElement stackTraceElement : ste) {
            className  = stackTraceElement.getClassName();
            methodName = stackTraceElement.getMethodName();
            lineNumber = stackTraceElement.getLineNumber();
            fileName = stackTraceElement.getFileName();
            stackTrace = stackTraceElement.toString();

            Saves.write("Date: " + date + " Time: " + time);
            Saves.write("Class: " + className);
            Saves.write("Method: " + methodName);
            Saves.write("File: " + fileName);
            Saves.write("Line: " + lineNumber);
            Saves.write("Stack Trace: " + stackTrace);
        }

        try {
            fw = new FileWriter(file, true);
            fw.write(str + ": " + n + "\n");
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
