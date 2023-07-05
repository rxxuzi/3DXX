package shot;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import main.Cube;
import main.Screen;

public class Log4Cube {
    String dirPath = "./pic/";
    String filename = "test.log";
    String filepath = dirPath + filename;
    public static void write() {
        File file = new File("./pic/test.log");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, true);
            fw.write("test");
            fw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void write(String[] data) {
        File file = new File("./pic/test.log");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, true);
            fw.write("test");
            fw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
