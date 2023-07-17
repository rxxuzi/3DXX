package shot;

import write.Saves;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Json�t�@�C���ɃL���[�u�̏���ۑ�
 * @since 4.3d
 */
public class Json {
    private static final String dirPath = "./screenshots/json/";
    String filename = "test.json";
    String filepath = dirPath + filename;
    FileWriter fw = null;
    File file;

    public Json(String filename){
        this.filename = filename;
        this.filepath = dirPath + filename + ".json";
        file = new File(filepath);
    }

    public void write(String[] data , int indent) {
        try {
            fw = new FileWriter(file, true);
            fw.write("{\n");
            for (String datum : data) {
                fw.write(indentString(indent) + datum);
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

    public void write(String data, int count, int indent) {
        try {
            fw = new FileWriter(file, true);
            fw.write("\n" + indentString(indent) + "\"" + data + count + " \" :");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public void write(ArrayList<String> E) {
        try {
            fw = new FileWriter(file, true);
            for (int i = 0; i < E.size(); i++) {
                fw.write(E.get(i));
            }
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unused")
    public void write(ArrayList<String> E, boolean b, int is) {
        try {
            fw = new FileWriter(file, true);
            for (int i = 0; i < E.size(); i++) {
                if (b) {
                    fw.write(indentString(is) + E.get(i));
                } else {
                    fw.write(indentString(is) + E.get(i));
                }
            }
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String indentString(int j) {
        StringBuilder tab = new StringBuilder();
        tab.append("    ".repeat(Math.max(0, j)));
        return tab.toString();
    }
}
