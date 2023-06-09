package main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/**
 * 3DObject のポリゴンを定義するクラス
 * @since 1.1
 * @author rxxuzi
 * @see Polygon
 */
public class Object {
	Polygon P;
	Color c;
	boolean draw = true, visible = true, seeThrough;
	double lighting = 1;
	
	public Object(double[] x, double[] y, Color c, int n, boolean seeThrough){
		//ポリゴンを定義
		P = new Polygon();
		for(int i = 0; i<x.length; i++) {
			P.addPoint( (int)x[i] , (int)y[i] );			
		}
		this.c = c;
		this.seeThrough = seeThrough;
	}
	
	void updatePolygon(double[] x, double[] y){
		//ポリゴンをリセット
		P.reset();
		
		for(int i = 0; i<x.length; i++){
			P.xpoints[i] = (int) x[i];
			P.ypoints[i] = (int) y[i];
			P.npoints = x.length;
		}
		
	}
	
	public void drawPolygon(Graphics g){
		
		if(draw && visible){
			g.setColor(new Color((int)(c.getRed() * lighting ), (int)(c.getGreen() * lighting), (int)(c.getBlue() * lighting)));
			
			if(seeThrough) {				
				g.drawPolygon(P);
			}else {
				g.fillPolygon(P);				
			}
			
			if(Screen.OutLines){
				g.setColor(new Color(0, 0, 0));
				g.drawPolygon(P);
			}

			if(Screen.PolygonOver == this){
				g.setColor(new Color(255, 255, 255, 100));
				g.fillPolygon(P);
			}
			
		}
	}
	
	public boolean MouseOver(){
		return P.contains(Main.screenSize.getWidth()/2, Main.screenSize.getHeight()/2);
	}
}