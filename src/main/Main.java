package main;
import write.Error;
import write.Saves;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * Project 3DXX
 * (3D snake)
 * @author Rxxuzi
 * @version 4.2.1
 * @since 2023-07-02
 *
 */
public class Main {
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static final long StartUpTime = System.currentTimeMillis();
	public static Saves saves = new Saves("./rsc/log/data.log");
	private static final boolean Debug = false;
	public static Error error = new Error();
	public static boolean displayGround = false;

	public static void main(String[] args){
		JFrame jf = new JFrame();
		Screen Core = new Screen();
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
