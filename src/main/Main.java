package main;
import shot.Json;
import write.Error;
import write.Saves;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * Main class of the program.
 * @implSpec This class is used to initialize the program.
 * @author Rxxuzi
 * @version 4.3.4
 * @since 1.0
 *
 */
public class Main {
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static final long StartUpTime = System.currentTimeMillis();
	public static Saves saves = new Saves();
	private static final boolean Debug = false;
	public static Error error = new Error();
	public static boolean displayGround = false;

	public static void main(String[] args){
		JFrame jf = new JFrame();
		Screen Core = new Screen();
		Json json = new Json();
		if(Debug){
			jf.setSize(1000,1000);
			jf.add(new Menu());
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}else {
			jf.setUndecorated(true);
			jf.setSize(screenSize);
			jf.getJMenuBar();
			jf.add(Core);
		}
		jf.setVisible(true);
		System.out.println("It is working properly.");
		System.out.println("property  : \n"  + Core);
	}
}
