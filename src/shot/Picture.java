package shot;

import main.Main;
import main.Screen;
import write.Saves;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.stream.IntStream;

/**
 * �X�N���[���V���b�g���B��class
 * main.Screen��static ArrayList���Q��
 *
 * @author rxxuzi
 * @since 4.2
 */
public final class Picture {
    private static final int WIDTH = Main.screenSize.width;
    private static final int HEIGHT = Main.screenSize.height;
    private static final String dirPath = "./screenshots/pic/";
    String fileName = "test";
    String fileType = "png";

    public void setFileName(String fileName) {
        this.fileName = fileName  + "." + fileType;
    }

    /**
     * �X�N���[���V���b�g���B��
     */
    public void take() {
        BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();


        //Stream API���g�p����for���̕��񏈗�
        IntStream.range(0, Screen.NewOrder.length).forEach(i -> Screen.DPolygons.get(Screen.NewOrder[i]).DrawablePolygon.drawPolygon(g));

        g.setColor(Color.WHITE);
        g.dispose();
        try {
            ImageIO.write(img, fileType, new File(dirPath + fileName));
            System.out.println("done");
            Screen.condition = "take picture";
        }catch (Exception ignored) {

        }
        System.gc();
    }
}
