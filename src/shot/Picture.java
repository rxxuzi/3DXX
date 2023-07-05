package shot;

import java.awt.image.BufferedImage;
import java.util.List;

import main.Main;
import main.Screen;
import java.awt.Color;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.awt.Graphics;

public class Picture {
    private static final int WIDTH = Main.screenSize.width;
    private static final int HEIGHT = Main.screenSize.height;
    private static final int BPP = 16;
    private static final int FPS = 60;
    private static final int DELAY = 1000 / FPS;
    private static final int QUALITY = 100;
    private static final int DELAY_INTERVAL = 100;
    private static final int WIDTH_INTERVAL = 100;
    private static final int HEIGHT_INTERVAL = 100;
    private Graphics g;

    /**
     * スクリーンショットを撮る
     */
    public void take() {
        String dirPath = "./pic/";
        String fileName = "test.png";
        String fileType = "png";

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
        }
        System.gc();
    }

    public void takeScreenShot() {

    }

    public void takeScreenShot(String fileName) {

    }

    public void takeScreenShot(String fileName, String filePath) {

    }

    public void takeScreenShot(String fileName, String filePath, String fileType) {

    }

    public void takeScreenShot(String fileName, String filePath, String fileType, int width, int height) {

    }

    public void takeScreenShot(String fileName, String filePath, String fileType, int width, int height, int quality) {

    }

    public void takeScreenShot(String fileName, String filePath, String fileType, int width, int height, int quality, int delay) {

    }

    public void takeScreenShot(String fileName, String filePath, String fileType, int width, int height, int quality, int delay, int interval) {

    }
}
