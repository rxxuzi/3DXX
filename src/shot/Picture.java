package shot;

import main.Main;
import main.Screen;
import write.Error;
import write.Saves;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * スクリーンショットを撮るclass
 * main.Screenのstatic ArrayListを参照
 *
 * @author rxxuzi
 * @since 4.2
 */
public class Picture {
    private static final int WIDTH = Main.screenSize.width;
    private static final int HEIGHT = Main.screenSize.height;
    private static final String dirPath = "./screenshots/pic/";
    String fileName = "test.png";
    String fileType = "png";

    public Picture() {
        //make directory
        File dir = new File(dirPath);
        if (!dir.exists()) {

            if(dir.mkdirs()){
                Saves.write("make directory");
            }
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName  + "." + fileType;
    }

    /**
     * スクリーンショットを撮る
     */
    public void take() {
        BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();

        for (int j : Screen.NewOrder) {
            Screen.DPolygons.get(j).DrawablePolygon.drawPolygon(g);
        }
        g.dispose();
        try {
            ImageIO.write(img, fileType, new File(dirPath + fileName));
            System.out.println("done");
            Screen.condition = "take picture";
        }catch (Exception e) {
            e.printStackTrace();
            Error.write(e);
        }
        System.gc();
    }
}
