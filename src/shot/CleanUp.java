package shot;

import java.io.File;
import java.util.Objects;

public class CleanUp {
    private static String  json_dirPath = "./screenshots/json";
    private static String  picture_dirPath = "./screenshots/pic/";

    public static void main(String[] args) {
        File json_dir = new File(json_dirPath);
        File picture_dir = new File(picture_dirPath);
        File[] json_files = json_dir.listFiles();
        File[] picture_files = picture_dir.listFiles();
        try{
            for (File json_file : Objects.requireNonNull(json_files)) {
                if (json_file.isFile()) {
                    json_file.delete();
                }
            }
        }catch (NullPointerException e){
            System.out.println("No json files");
        }
        try{
            for (File picture_file : Objects.requireNonNull(picture_files)) {
                if (picture_file.isFile()) {
                    picture_file.delete();
                }
            }
        }catch (NullPointerException e){
            System.out.println("No picture files");
        }
    }

    public static void delete_json_files(){
        File json_dir = new File(json_dirPath);
        File[] json_files = json_dir.listFiles();
        try{
            for (File json_file : Objects.requireNonNull(json_files)) {
                if (json_file.isFile()) {
                    json_file.delete();
                }
            }
        }catch (NullPointerException e){
            System.out.println("No json files");
        }
    }

    public static void delete_picture_files(){
        File picture_dir = new File(picture_dirPath);
        File[] picture_files = picture_dir.listFiles();
        try{
            for (File picture_file : Objects.requireNonNull(picture_files)) {
                if (picture_file.isFile()) {
                    picture_file.delete();
                }
            }
        }catch (NullPointerException e){
            System.out.println("No picture files");
        }
    }
}
