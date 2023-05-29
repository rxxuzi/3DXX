package main;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JTextField;


/**
 * Project 3DX
 * @author Rxxuzi
 * @version 3.0
 *
 */
public class Main{	
	static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static final long StartUpTime = System.currentTimeMillis();
	public static Saves saves = new Saves("./rsc/log/data.log");
	private static final boolean Debug = false;

	public static void main(String[] args){
		JFrame jf = new JFrame();
		Screen Core = new Screen();
		if(Debug){
			jf.setSize(1000,1000);
			jf.add(new Menu());
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}else {
			jf.setUndecorated(true);
			jf.setSize(ScreenSize);
			jf.getJMenuBar();
			jf.add(Core);
		}
		jf.setVisible(true);
		System.out.println("It is working properly.");
		System.out.println("property  : \n"  + Core);
	}
}
