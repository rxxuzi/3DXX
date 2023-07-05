package shot;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Json {
    String dirPath = "./pic/";
    String filename = "test.json";
    String filepath = dirPath + filename;

    public Json() {
        File file = new File(filepath);
        if (file.exists()) {
            if(file.delete()){
                System.out.println("file deleted");
            }
        }
    }

    public void write(String[] data , int indent) {
        File file = new File("./pic/test.json");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, true);
            fw.write("{\n");
            for (int i = 0; i < data.length; i++) {
                fw.write(indentString(indent) + data[i]);
            }
            fw.write( "\n    }");
            fw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void write(String data) {
        File file = new File("./pic/test.json");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, true);
            fw.write(data);
            fw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String data, int count, int indent){
        File file = new File("./pic/test.json");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, true);
            fw.write("\n" + indentString(indent) + "\"" + data +  count + " \" :");
            fw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String indentString(int j) {
        String tab = "";
        for (int i = 0; i < j; i++) {
            tab += "    ";
        }
        return tab;
    }
}
