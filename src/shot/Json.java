package shot;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Json {
    private static final String dirPath = "./screenshots/json/";
    String filename = "test.json";
    String filepath = dirPath + filename;
    FileWriter fw = null;
    File file;

    public Json() {
        file = new File(filepath);
        if (file.exists()) {
            if(file.delete()){
                System.out.println("file deleted");
            }
        }
        //make directory
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public Json(String filename){
        this.filename = filename;
        this.filepath = dirPath + filename + ".json";

        file = new File(filepath);
        if (file.exists()) {
            if(file.delete()){
                System.out.println("file deleted : " + filepath);
            }
        }
        //make directory
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

    }

    public void write(String[] data , int indent) {
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
        try {
            fw = new FileWriter(file, true);
            fw.write(data);
            fw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String data, int count, int indent){
        try {
            fw = new FileWriter(file, true);
            fw.write("\n" + indentString(indent) + "\"" + data +  count + " \" :");
            fw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(ArrayList<Class> E){
        try {
            fw = new FileWriter(file, true);
            for(int i = 0; i < E.size(); i++){
                fw.write(E.get(i).toString());
            }
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
