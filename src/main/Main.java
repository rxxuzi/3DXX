package main;

import shot.CleanUp;
import shot.DirectoryCheck;
import write.Saves;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Main class of the program.
 *
 * @author Rxxuzi
 * @version 4.5
 * @implSpec This class is used to initialize the program.
 * @since 1.0
 */
public final class Main {

    /*
     * 使用しているスクリーンのサイズを測定
     */
    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    public static final long StartUpTime = System.currentTimeMillis();

    private static final AtomicBoolean Debug = new AtomicBoolean(false);

    public static final boolean MINIMUM_MODE = true;

    public static final boolean MOVE2D = false;

    public static void main(String[] args) {

        JFrame jf = new JFrame();
        Screen Core = new Screen();
//        new Saves();
//        Saves.write("StartUpTime : " + StartUpTime , true);
        DirectoryCheck directoryCheck = new DirectoryCheck();
        directoryCheck.run();
        if (Debug.get()) {
            jf.setSize(1000, 1000);
            jf.add(new Menu());
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else {
            jf.setUndecorated(true);
            jf.setSize(screenSize);
            jf.getJMenuBar();
            jf.add(Core);
            CleanUp.main(args);
        }

        jf.setVisible(true);
        System.out.println("It is working properly.");
        System.out.println("property  : \n" + Core);
    }
}
