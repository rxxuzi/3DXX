package shot;

import write.Saves;

import java.io.File;

public class DirectoryCheck {
    int cnt = 4;
    private final String[] dirPath = {
            "./data/",
            "./screenshots/pic/",
            "./screenshots/json/",
            "./rsc/log/"
    };

    public static void run(String DirectoryPath){
        File dir = new File(DirectoryPath);
        if (!dir.exists()) {
            if(dir.mkdirs()){
                Saves.write("made directory : " + DirectoryPath);
            }
        }
    }
    public void run(){
        for(String dirPath : dirPath){
            //make directory
            File dir = new File(dirPath);
            if (!dir.exists()) {
                if(dir.mkdirs()){
                    Saves.write("made directory : " + dirPath);
                }
            }
        }
    }
    public static void delete(String dirPath){
        File dir = new File(dirPath);
        if (dir.exists()) {
            if(dir.delete()){
                Saves.write("deleted directory : " + dirPath);
            }
        }
    }
}
