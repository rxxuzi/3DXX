package write;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Error {
    //日付取得
    private static String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    //時刻取得
    private static String time = new SimpleDateFormat("hh:mm:ss:SSS").format(new Date());
    //パス
    private static String dirPath = "./rsc/log/error/" + date + "/";
    //タイトル
    private static File file = new File(dirPath + "error.log");

    private static String className;
    private static String methodName;
    private static int lineNumber;

    private static String bc = "class";
    private static String bm = "method";
    private static int bl = -1;

    public static int ErrorCount = 0;

    private static final long interval = 10;

    private static long lastTime = 0;

    public Error() {
        System.out.println("Error Log");
        System.out.println(date+time);
        File dir = new File(dirPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        System.out.println(dirPath);
    }
    private static String getData(){
        return
                "Time : " + date +"  "+ time + "\n" +
                "Class Name : " + className + "\n" +
                "Method Name : " + methodName + "\n" +
                "Line Number : " + lineNumber + "\n";
    }
    public static void write(String data){
        //get class name
        className = Thread.currentThread().getStackTrace()[2].getClassName();
        //get method name
        methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        //get line number
        lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

        time = new SimpleDateFormat("hh:mm:ss:SSS").format(new Date());
        FileWriter fw;
        if(file != null){
            try {
                fw = new FileWriter(file , true);
                fw.write(ErrorCount + ": \n");
                fw.write(getData());
                fw.write("Error : " +data + "\n");
                fw.close();
                ErrorCount ++ ;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void write(Exception data){
        //get class name
        className = Thread.currentThread().getStackTrace()[2].getClassName();
        //get method name
        methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        //get line number
        lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

        time = new SimpleDateFormat("hh:mm:ss:SSS").format(new Date());

        long currentTime = System.currentTimeMillis();
        if(currentTime - lastTime > interval && lineNumber != bl && !Objects.equals(className, bc)){
            lastTime = currentTime;
            FileWriter fw;
            if(file != null){
                try {
                    fw = new FileWriter(file , true);
                    fw.write(ErrorCount + ": \n");
                    fw.write(getData());
                    fw.write("Error : " +data + "\n");
                    fw.close();
                    ErrorCount ++ ;
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            bc = className;
            bm = methodName;
            bl = lineNumber;
        }
    }
}
