package main;
import com.sun.xml.internal.ws.developer.Serialization;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JPanel;

import static main.Main.saves;

/*
* Main.javaのFrameにパネルとしてaddするclass
* */
public class Screen extends JPanel {
	@Serialization
	private static final long serialVersionUID = 1L;


	/**
	 * fpsModeをTrueにすると以下のことが起こります
	 * 	* 1.重力の追加(浮遊できなくなる)
	 * 	* 2.カメラの最低z座標が2となる
	 */
	public static final boolean firstPersonMode = false;
	private static final double height = 4.0;
	private static final boolean debugMode = false;
	private static final double cameraSpeed = 0.002; //default => 0.25
	private static final long moveInterval = 10; // default => 0
	private double gravity = 0.001; // default => 0.001
	public static ArrayList<DPolygon> DPolygons = new ArrayList<>();
	public static ArrayList<Cube> Cube = new ArrayList<>();
	public static ArrayList<Pyramid> Pyramid = new ArrayList<>();
	public static ArrayList<Double> Floor = new ArrayList<>();
	private static final int[] colorBox = new int[256 * 256 * 256];
	private static int counter1 = 0;
	static Object PolygonOver = null ;
	private static Object FocusPolygon = null;
	private static final long deleteInterval = 200;
	private static long LastMoveTime = 0;
	private static long LastCubeDeleteTime = 0;
	private static long LastCubeGenerateTime = 0 ;
	private int NumberOfDeleteCube = 0 ;
	private static String dCube = "NONE";
	Robot r ;
	static Random random = new Random();
	static final double[] FViewFrom = { -5 , -5 , 10 };
	static final double[] FViewTo = {  0 , 0 ,  0 };
	static double[] ViewFrom = FViewFrom.clone();
	static double[] ViewTo   = FViewTo.clone();
	static double zoom = 1000, MinZoom = 100, MaxZoom = 5000;
	static double MouseX = 0 , MouseY = 0;
	static double MovementSpeed = 0.5;
	double drawFPS = 0;
	double MaxFPS = 2000;
	double LastFPSCheck = 0 , Checks = 0 , LastRefresh = 0;
	private double VerticalLook = -0.9; //0.99 ~ -0.99まで、正の値の時は上向き。負の値の時は下向き
	private double HorizontalLook = 0; // 任意の数値をとり、ラジアン単位で一周する
	double VerticalRotationSpeed = 1000; //垂直回転の速さ
	double HorizontalRotationSpeed = 500; //水平回転の速さ
	static final double aimSight = 4;	// センタークロスの大きさ
	int[] NewOrder; //配列DPolygonの描画する順番を保持する配列
	static boolean OutLines = true;
	boolean[] Control = new boolean[15];//キー入力の情報を格納する配列
	final static int FontSize = 15;
	static String condition = "NONE";
	int Press = 10;
	public static long t ;

	public Screen(){
		this.addKeyListener(new KeyTyped());
		setFocusable(true);
		this.addMouseListener(new AboutMouse());
		this.addMouseMotionListener(new AboutMouse());
		this.addMouseWheelListener(new AboutMouse());

		invisibleMouse();


		Cube.add(new Cube(5,5,5,3,3,3,Color.green));
		int d = 2;//幅

		for(int i = -10 ; i < 50 ; i += d) {
			for(int j = -20  ; j < 40 ; j += d) {
				if((i+j)/2%2 == 0) {
					DPolygons.add(new DPolygon(new double[]{i, i, i+d, i+d} , new double[] {j, j+d, j+d, j},new double[] {0,0,0,0}, new Color(255 ,179 ,219), false));
				}else {
					DPolygons.add(new DPolygon(new double[]{i, i, i+d, i+d} , new double[] {j, j+d, j+d, j},new double[] {0,0,0,0}, new Color(179 ,236 ,255), false));
				}
			}
		}

		new Ball(3,3,3,4,4,4,Color.MAGENTA);

	}

	/*描画に関するメソッド*/
	public void paintComponent(Graphics g){
		//描画リセット
		g.clearRect(0, 0, (int)Main.ScreenSize.getWidth(), (int)Main.ScreenSize.getHeight());

		//カメラを動かす
		KeyControl();

		//フォーカスされたポリゴンを削除する
		deleteCube();

		//このカメラ位置で一般的なものをすべて計算します。
		Calculator.VectorInfo();

		// ポリゴン情報を各自アップデート
		for (DPolygon dPolygon : DPolygons) {
			dPolygon.updatePolygon();
		}

		//全てのポリゴンの距離をソート
		setOrder();

		//マウスが乗っているポリゴンを特定する
		setPolygonOver();

		//setOrder関数で設定された順序でポリゴンを描画
		for (int j : NewOrder) {
			DPolygons.get(j).DrawablePolygon.drawPolygon(g);
		}

		//画面の中央にエイムをつける
		drawMouseAim(g);

		//フォントの設定
		Font font = new Font(Font.DIALOG, Font.ITALIC,FontSize);
		//
		double VAngle =  Math.toDegrees(Math.tan(VerticalLook));

		g.setFont(font);

		snakeMove();

		g.drawString("FPS : " + (int)drawFPS , 10, 15);
		g.drawString("ELAPSED TIME : " + (System.currentTimeMillis() - Main.StartUpTime ) + "ms" , 10 , 30);
		g.drawString("OBJECT : " + Arrays.toString(ViewTo)   , 10 ,45);
		g.drawString("CAMERA : " + Arrays.toString(ViewFrom) , 10 ,60);
		g.drawString("ZOOM   : " + zoom , 10 , 75);
		g.drawString("Vertical   Look : " + VerticalLook , 10 , 90);
		g.drawString("Horizontal Look(rad) : " + HorizontalLook , 10 , 105);
		g.drawString("Vertical angle   	 : " + (int)VAngle + "°" , 10 ,120);
		g.drawString("Number Of Polygons : " + DPolygons.size() , 10 ,135);
		g.drawString("Number Of Cubes    : " + Cube.size() , 10 ,150);
		g.drawString("Focus Polys ID : " + FocusPolygon.toString() , 10 ,170);
		g.drawString(t +"s", 10,200);
		g.setFont(new Font(Font.SANS_SERIF , Font.BOLD , 20));
		g.drawString("CONDITION: " + condition , 10 ,190);

		if(Control[10]){
			Press++;
		}
		g.drawString(Press + "SIZE" , 10 , 220);

		//描画更新のインターバル
		SleepAndRefresh();

		if(firstPersonMode){
			hitJudgment();
		}
		int[] hx1 = new int[6];
		int[] hy1 = new int[6];
		for(int i = 0; i < 6; i++){
			hx1[i] = (int) (100 * Math.cos(i * Math.PI * 2 / 6) + 300);
			hy1[i] = (int) (100 * Math.sin(i * Math.PI * 2 / 6) + 500);
		}

		g.fillPolygon(hx1 , hy1 , Ball.hx1.length);

		for(main.Cube c : Cube){
			c.setDisplayCube();
		}

	}

	private void snakeMove() {
		double dx = 0.1;
		double dy = 0.1;
		for(Cube c : Cube){
            c.x += dx;
            c.y += dy;
			c.updatePoly();
        }
	}

	private void SleepAndRefresh(){
		long timeSLU = (long) (System.currentTimeMillis() - LastRefresh);

		Checks ++;

		if(Checks >= 15){
			drawFPS = Checks/((System.currentTimeMillis() - LastFPSCheck)/1000.0);
			LastFPSCheck = System.currentTimeMillis();
			Checks = 0;
		}

		if(timeSLU < 1000.0/MaxFPS){
			try {
				Thread.sleep((long) (1000.0/MaxFPS - timeSLU));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		LastRefresh = System.currentTimeMillis();
		repaint();
		t = (System.currentTimeMillis() - Main.StartUpTime) / 1000;
	}


	private void hitJudgment() {
		
		if(
				Cube.get(0).x < ViewFrom[0] && Cube.get(0).dx >ViewFrom[0] && Cube.get(0).y < ViewFrom[1] && Cube.get(0).dy > ViewFrom[1]
		){
			condition = "in the BOX";
			if (Math.abs(Cube.get(0).x - ViewFrom[0]) > Math.abs(Cube.get(0).dx - ViewFrom[0])) {
				ViewFrom[0] += 0.1;
			}else{
				ViewFrom[0] -= 0.1;
			}

			if (Math.abs(Cube.get(0).y - ViewFrom[1]) > Math.abs(Cube.get(0).dy - ViewFrom[1])) {
				ViewFrom[1] += 0.1;
			}else{
				ViewFrom[1] -= 0.1;
			}
		}else {
			condition = "NONE";
		}
		
		
	}

	@SuppressWarnings("unused")
	private void lowerLimit() {
		if(ViewFrom[2] < 3.0) {
            ViewFrom[2] = 3.0;
        }
	}

	/**
	 * Control7が押された時、フォーカスしているキューブを特定して削除する
	 *　連続で消えるのを防ぐ為、delete intervalを設けている
	 */
	int msd = 1000;
	private void deleteCube() {
		if(Control[7]) {
			if(System.currentTimeMillis() - LastCubeDeleteTime >= deleteInterval) {
				for(int i = 0 ; i < Cube.size() ; i ++) {
					for(int j = 0 ; j < Cube.get(i).Polys.length ; j ++) {
						if( Cube.get(i).Polys[j].DrawablePolygon.equals(FocusPolygon) ) {
							dCube = Cube.get(i).toString();
							Cube.get(i).removeCube();
							LastCubeDeleteTime = System.currentTimeMillis();
							NumberOfDeleteCube ++ ;
							condition = "CUBE DELETED : " + dCube;
							break;
						}
					}
				}
			}
		}

		//全削除
		if(Control[9]) {
			NumberOfDeleteCube += Cube.size();
			for(int i = 0 ; i < Cube.size() ; i ++ ) {
				Cube.get(i).removeCube();
				condition = "ALL DELETE";
			}
		}
	}

	//距離情報をソート
	private void setOrder(){
		//距離情報を格納する配列
		double[] k = new double[DPolygons.size()];
		//ソートした情報を代入
		NewOrder = new int[DPolygons.size()];

		for(int i = 0 ; i < DPolygons.size() ; i++) {
			k[i] = DPolygons.get(i).AverageDistance;
			NewOrder[i] = i ;
		}

		/*
		平均距離の短い順にソートする
		 (バブルソート)
		*/
		double dtmp ;
		int itmp;
		for(int i = 0 ; i < k.length - 1 ; i++) {
			for(int j = 0 ; j < k.length - 1 ; j++) {
				if(k[j] < k[j + 1]) {
					dtmp = k[j];
					itmp = NewOrder[j];
					NewOrder[j] = NewOrder[j + 1];
					k[j] = k[j + 1];
					NewOrder[j + 1] = itmp;
					k[j + 1] = dtmp;
				}
			}
		}
	}

	//マウスを非表示
	private void invisibleMouse(){
		 Toolkit toolkit = Toolkit.getDefaultToolkit();
		 BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
		 Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, new Point(0,0), "InvisibleCursor");
		 setCursor(invisibleCursor);
	}

	//マウスエイムを描画
	private void drawMouseAim(Graphics g){
		g.setColor(Color.black);
		g.drawLine((int)(Main.ScreenSize.getWidth()/2 - aimSight), (int)(Main.ScreenSize.getHeight()/2), (int)(Main.ScreenSize.getWidth()/2 + aimSight), (int)(Main.ScreenSize.getHeight()/2));
		g.drawLine((int)(Main.ScreenSize.getWidth()/2), (int)(Main.ScreenSize.getHeight()/2 - aimSight), (int)(Main.ScreenSize.getWidth()/2), (int)(Main.ScreenSize.getHeight()/2 + aimSight));
	}



	//キー入力を制御
	private void KeyControl(){
		Vector ViewVector = new Vector(ViewTo[0] - ViewFrom[0], ViewTo[1] - ViewFrom[1], ViewTo[2] - ViewFrom[2]);
		double xMove = 0, yMove = 0, zMove = 0;

		//(垂直)単位ベクトルを取得
		Vector VerticalVector = new Vector (0, 0, 1);
		//水平に動くベクトル
		Vector SideViewVector = ViewVector.CrossProduct(VerticalVector);
		//動く際の時間を取得
		long moveTime = System.currentTimeMillis();
		//動くスピードを計算
		if(moveTime - LastMoveTime > moveInterval){
			//前に移動
			if(Control[0]){
				xMove += ViewVector.x * cameraSpeed;
				yMove += ViewVector.y * cameraSpeed;
				zMove += ViewVector.z * cameraSpeed;
			}

			//後ろに移動
			if(Control[2]){
				xMove -= ViewVector.x * cameraSpeed;
				yMove -= ViewVector.y * cameraSpeed;
				zMove -= ViewVector.z * cameraSpeed;
			}
			//左に移動
			if(Control[1]){
				xMove += SideViewVector.x * cameraSpeed;
				yMove += SideViewVector.y * cameraSpeed;
				zMove += SideViewVector.z * cameraSpeed;
			}
			//右に移動
			if(Control[3]){
				xMove -= SideViewVector.x * cameraSpeed;
				yMove -= SideViewVector.y * cameraSpeed;
				zMove -= SideViewVector.z * cameraSpeed;
			}

            LastMoveTime = System.currentTimeMillis();
        }

		
		//移動するベクトル
		Vector MoveVector = new Vector(xMove, yMove, zMove);
		double fx = MoveVector.x * MovementSpeed;
		double fy = MoveVector.y * MovementSpeed;
		double fz = MoveVector.z * MovementSpeed;
		
		MoveTo(ViewFrom[0] + fx, ViewFrom[1] + fy, ViewFrom[2] + fz);
		
		//カメラ座標をリセット
		if(Control[4]) {
			for(int i = 0 ; i < FViewFrom.length ; i ++) {
				ViewFrom[i] = FViewFrom[i];
				ViewTo[i] = FViewTo[i];
			}
			zoom = 1000;
			condition = "View Reset";
		}
		
		//正の方向にz移動
		if(Control[5]) {
			ViewFrom[2] += 0.4;
			ViewTo[2] += 0.4;	
			condition = "FLY";
		}
		
		//負の方向にz移動
		if(Control[6]) {
			//z < 0の場合z = 0で止める
			if(ViewFrom[2] > 0.0) {
				ViewFrom[2] -= 0.4 + gravity;
				ViewTo[2] -= 0.4 + gravity;
			}else {
				ViewFrom[2] -= 0.1;
				ViewTo[2] -= 0.1;
			}
		}

		//ランダムなキューブを生成
		if(Control[8]) {
			long generate = System.currentTimeMillis();
			
			if(Math.abs(LastCubeGenerateTime - generate) > 200) {
				int rx = random.nextInt(255);
				int ry = random.nextInt(255);
				int rz = random.nextInt(255);
				int xyz = rx * 1_000_000 + ry * 1000 + rz;

				colorBox[counter1] = xyz;

				if(CoordinateCheck(xyz)) {
					Cube.add(new Cube(-rx/25d , ry/25d , rz/25d + 2, 1, 1, 1, new Color(rx , ry , rz) ));
					condition = "CUBE GENERATED : (x,y,z) = " + rx /25 +"," + ry /25 + "," + rz /25;
					saves.write(rx /25 +"," + ry /25 + "," + rz /25 , new Color(rx , ry , rz));
				}
				
				counter1 ++ ;
				LastCubeGenerateTime = System.currentTimeMillis();
			}
		}
	}

	//キューブが重複していないかチェック
	private boolean CoordinateCheck(Integer xyz) {
		for(int i = 0 ; i < counter1 ; i ++) {
			if(colorBox[i] == xyz) {
				return false;
			}
		}
		return true;
	}

	//カメラの座標を決めるメソッド
	private void MoveTo(double x, double y, double z){
		ViewFrom[0] = x;
		ViewFrom[1] = y;
		ViewFrom[2] = z;

		if(firstPersonMode){
			ViewFrom[2] = height;
		}else{
			if(ViewFrom[2] < 0.0) ViewFrom[2] = 0.0;
		}

		//描画更新
		updateView();
	}
	
	//マウスが乗っているポリゴンを特定
	private void setPolygonOver(){
		//一度nullと定義
		PolygonOver = null;
		//探索
		for(int i = NewOrder.length-1; i >= 0; i--) {
			if(DPolygons.get(NewOrder[i]).DrawablePolygon.MouseOver() && DPolygons.get(NewOrder[i]).draw && DPolygons.get(NewOrder[i]).DrawablePolygon.visible){
				FocusPolygon = DPolygons.get(NewOrder[i]).DrawablePolygon;
				PolygonOver = DPolygons.get(NewOrder[i]).DrawablePolygon;
				
				break;
			}
		}	
	}

	private void MouseMovement(double NewX, double NewY){		
		//マウスがy軸(スクリーンの中央)からどれだけはなれたか計測
		double difX = (NewX - Main.ScreenSize.getWidth()/2);
		//マウスがx軸(スクリーンの中央)からどれだけはなれたか計測
		double difY = (NewY - Main.ScreenSize.getHeight()/2);
		difY *= 6 - Math.abs(VerticalLook) * 5;
		
		VerticalLook   -= difY  / VerticalRotationSpeed;
		HorizontalLook += difX / HorizontalRotationSpeed;
		
		//VerticalLookの絶対値が1.0以上にならないようにする
		if(VerticalLook>0.999) VerticalLook = 0.999;
		if(VerticalLook<-0.999) VerticalLook = -0.999;
		
		updateView();
	}
	
	//視点をアップデート
	private void updateView(){
		double r = Math.sqrt(1 - (VerticalLook * VerticalLook));
		ViewTo[0] = ViewFrom[0] + r * Math.cos(HorizontalLook); // x軸移動
		ViewTo[1] = ViewFrom[1] + r * Math.sin(HorizontalLook);	// y軸移動
		ViewTo[2] = ViewFrom[2] + VerticalLook;					// z軸移動	
	}
	
	/*キー入力用class*/
	class KeyTyped extends KeyAdapter{
		
		//キーを押した時にtrue
		public void keyPressed(KeyEvent e) {
			
			switch(e.getKeyCode()) {
				case KeyEvent.VK_W : 	 Control[0] = true ; break; //前進
				case KeyEvent.VK_A : 	 Control[1] = true ; break; //後退
				case KeyEvent.VK_S : 	 Control[2] = true ; break; //左へ
				case KeyEvent.VK_D :	 Control[3] = true ; break; //右へ
				case KeyEvent.VK_X : 	 Control[4] = true ; break; //視点リセット
				case KeyEvent.VK_SPACE:	 Control[5] = true ; break; //上へ
				case KeyEvent.VK_SHIFT:	 Control[6] = true ; break; //下へ
				case KeyEvent.VK_BACK_SPACE: Control[7] = true ; break; //下へ
				case KeyEvent.VK_R : 	 Control[8] = true ; break; //キューブを生成
				case KeyEvent.VK_DELETE :Control[9] = true ; break; //キューブを削除
				case KeyEvent.VK_O 	:	OutLines = !OutLines; //ライン削除
					if(OutLines) {
						condition = "Show outer frame";
					}else {
						condition = "Hide outer frame";
					}
					break;
				case KeyEvent.VK_ENTER : Control[10] = true ; break;
				case KeyEvent.VK_ESCAPE : System.exit(0); break; //Escapeキーを押すと終了
			}

		}
		
		//キーを離した時にfalse
		public void keyReleased(KeyEvent e) {
			
			switch(e.getKeyCode()) {
				case KeyEvent.VK_W : 	 Control[0] = false ; break;
				case KeyEvent.VK_A : 	 Control[1] = false ; break;
				case KeyEvent.VK_S : 	 Control[2] = false ; break;
				case KeyEvent.VK_D :	 Control[3] = false ; break;
				case KeyEvent.VK_X : 	 Control[4] = false ; break;
				case KeyEvent.VK_SPACE:	 Control[5] = false ; condition = "NONE"; break;
				case KeyEvent.VK_SHIFT:	 Control[6] = false ; break;
				case KeyEvent.VK_BACK_SPACE: Control[7] = false ; break;
				case KeyEvent.VK_R : 	 	 Control[8] = false ; break;
				case KeyEvent.VK_DELETE :Control[9] = false ; break;
				case KeyEvent.VK_ENTER: Control[10] = false ; break;
			}
		}
	}
	
	/*
	 * マウスの情報を扱うクラス
	 * マウスの座標、マウスクリック情報、マウスホイールの情報を扱う
	 */
	class AboutMouse implements MouseListener , MouseMotionListener, MouseWheelListener{
		
		//マウスを中央に移動させるメソッド
		void CenterMouse(){
			try {
				r = new Robot();
				r.mouseMove((int)Main.ScreenSize.getWidth()/2, (int)Main.ScreenSize.getHeight()/2);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
		
		//マウスをドラッグしたときの制御		
		public void mouseDragged(MouseEvent e) {
			MouseMovement(e.getX(), e.getY());
			MouseX = e.getX();
			MouseY = e.getY();
			CenterMouse();
		}

		//マウスの動きを制御
		public void mouseMoved(MouseEvent e) {
			MouseMovement(e.getX(), e.getY());
			MouseX = e.getX();
			MouseY = e.getY();
			CenterMouse();
		}
		
		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
		
		//マウスのクリックをしたときの制御
		public void mousePressed(MouseEvent e) {
			//右クリック
			if(e.getButton() == MouseEvent.BUTTON1) {
				if(PolygonOver != null) PolygonOver.seeThrough = false;				
			}

			//左クリック
			if(e.getButton() == MouseEvent.BUTTON3) {				
				if(PolygonOver != null) PolygonOver.seeThrough = true;
			}
		}

		public void mouseReleased(MouseEvent e) {
		}
		public void mouseWheelMoved(MouseWheelEvent e) {
			if(e.getUnitsToScroll()>0){
				if(zoom > MinZoom) zoom -= 25 * e.getUnitsToScroll();
				condition = "Zoom out";
			}else{
				if(zoom < MaxZoom) zoom -= 25 * e.getUnitsToScroll();
				condition = "Zoom in";
			}	
			
		}
		
	}
}