package main;

import jdk.jfr.BooleanFlag;
import shot.Json;
import shot.Picture;
import vector.Calculator;
import vector.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

/**
 * Main.javaのFrameにパネルとしてaddするclass
 * @since 1.0
 * @author rxxuzi
 * @see javax.swing.JPanel
 * @see Main
 *
*
* */
public final class Screen extends JPanel {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * FIRST_PERSON_MODE :
	 * true :
	 * 1.重力の追加(浮遊できなくなる)
	 * * 2.カメラの最低z座標が2となる
	 */
	public static final AtomicBoolean FIRST_PERSON_MODE = new AtomicBoolean(false);
	/**
	 * SWITCH_CUBE_OPERATION :
	 * true : 右クリックするとブロックを生成する
	 * false: ポリゴンを表示/非表示にする
	 */
	private static final AtomicBoolean SWITCH_CUBE_OPERATION = new AtomicBoolean(false);
	/**
	 * スクリーンショット用フラグ
	 */
	private static final AtomicBoolean SCREEN_SHOT = new AtomicBoolean(false);
	/**
	 * キューブを作成するかのフラグ
	 */
	private final AtomicBoolean GENERATE = new AtomicBoolean(false);
	private final AtomicBoolean DELETE = new AtomicBoolean(false);
	private static final boolean DEBUG_MODE = false;
	private static final double height = 4.0;
	private static final double cameraSpeed = 0.002; //default => 0.25
	private static final long moveInterval = 10; // default => 0
	private static final double gravity = 0.001; // default => 0.001
	public static ArrayList<Cube> Cubes = new ArrayList<>();
	public static ArrayList<DPolygon> DPolygons = new ArrayList<>();
	public static ArrayList<Pyramid> Pyramids = new ArrayList<>();
	private static final int[] colorBox = new int[256 * 256 * 256];
	private static int counter1 = 0;
	public static Object PolygonOver = null ; //カーソル上のポリゴンの情報
	private static Object FocusPolygon = null;
	private static final long deleteInterval = 200;
	private static final long CubeGenerateInterval = 0; //default -> 100
	private long LastMoveTime = 0;
	private long LastCubeDeleteTime = 0;
	private long LastCubeGenerateTime = 0 ;
	static final double[] FViewFrom = { -2 , -2 , 10 };
	static final double[] FViewTo = {  -2 , 0 ,  5 };
	public static double[] ViewFrom = FViewFrom.clone(); //カメラの座標
	public static double[] ViewTo   = FViewTo.clone();	  //オブジェクトの座標
	public static double zoom = 1000;
	static double MinZoom = 100;
	static double MaxZoom = 5000;
	static double MouseX = 0 , MouseY = 0; //マウスの座標
	private double drawFPS = 0;
	private double LastFPSCheck = 0 , Checks = 0 , LastRefresh = 0; //FPS関連
	private double VerticalLook = -0.9; //0.99 ~ -0.99まで、正の値の時は上向き。負の値の時は下向き
	private double HorizontalLook = 0; // 任意の数値をとり、ラジアン単位で一周する
	double VerticalRotationSpeed = 1000; //垂直回転の速さ
	double HorizontalRotationSpeed = 500; //水平回転の速さ
	public static int[] NewOrder; //配列DPolygonの描画する順番を保持する配列
	static boolean OutLines = true; // ポリゴンのアウトラインの描画を決めるフラグ
	boolean[] Control = new boolean[15];//キー入力の情報を格納する配列
	public static String condition = "NONE"; //状態を示す文字列
	int Press = 10;
	public static long t ; //時間
	Robot r ;
	Random random = new Random();
	private final Picture p = new Picture();
	@BooleanFlag
	public boolean Details = true;
	private String sss = "";

	public Screen(){
		//Key and Mouse Listener
		this.addKeyListener(new KeyTyped());
		this.addMouseListener(new AboutMouse());
		this.addMouseMotionListener(new AboutMouse());
		this.addMouseWheelListener(new AboutMouse());

		setFocusable(true);

		invisibleMouse();

		Cubes.add(new Cube(5,-6,6,2,2,2,new Color(200,200,200),true , false));

		if (Main.MINIMUM_MODE) {
			this.setBackground(Color.BLACK);
		} else {
			Cubes.add(new Cube(5,5,5,2,2,2,Color.green));
			Cubes.add(new Cube(5,3,6,2,2,2,Color.blue));
			Cubes.add(new Cube(5,2,7,2,2,2,Color.red));
			Pyramids.add(new Pyramid(10,-5,2, 2,2,2,Color.MAGENTA));

			new Ball(3,3,3,4,4,4,Color.MAGENTA);
			new TextToObject("./rsc/object/mario.txt");
			new Ground();
			new Floor(-20,-10,30,30);
		}


	}

	public Screen(boolean fps){
		//Key and Mouse Listener
		this.addKeyListener(new KeyTyped());
		this.addMouseListener(new AboutMouse());
		this.addMouseMotionListener(new AboutMouse());
		this.addMouseWheelListener(new AboutMouse());

		setFocusable(true);

		invisibleMouse();

		Cubes.add(new Cube(5,-6,6,2,2,2,new Color(200,200,200),true , false));
		Cubes.add(new Cube(5,6,2,2,2,2,new Color(200,200,200),true , false));

		if (Main.MINIMUM_MODE) {
			this.setBackground(Color.BLACK);
		} else {
			Cubes.add(new Cube(5,5,5,2,2,2,Color.green));
			Cubes.add(new Cube(5,3,6,2,2,2,Color.blue));
			Cubes.add(new Cube(5,2,7,2,2,2,Color.red));
			Pyramids.add(new Pyramid(10,-5,2, 2,2,2,Color.MAGENTA));

			new Ball(3,3,3,4,4,4,Color.MAGENTA);
			new TextToObject("./rsc/object/mario.txt");
			new Ground();
			new Floor(-20,-10,30,30);
		}

		FIRST_PERSON_MODE.set(fps);
	}
	/*描画に関するメソッド*/

	public void paintComponent(Graphics g){

		if(Main.MINIMUM_MODE){
			g.setColor(Color.BLACK);
		}
		//描画リセット
		g.clearRect(0, 0, (int)Main.screenSize.getWidth(), (int)Main.screenSize.getHeight());

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

		IntStream.range(0, Screen.NewOrder.length).forEach(i -> DPolygons.get(Screen.NewOrder[i]).DrawablePolygon.drawPolygon(g));

		//画面の中央にエイムをつける
		drawMouseAim(g);

		//フォントの設定
		//フォントサイズを指定
		int FontSize = 15;

		Font font = new Font(Font.DIALOG, Font.ITALIC,FontSize);
		//角度の計算
		double VAngle =  Math.toDegrees(Math.tan(VerticalLook));

		g.setFont(font);

		//動きを指定
		snakeMove();

		if(Details){
			g.drawString("FPS : " + (int)drawFPS , 10, 15);
			g.drawString("ELAPSED TIME : " + (System.currentTimeMillis() - Main.StartUpTime ) + "ms" , 10 , 30);
			g.drawString("OBJECT : " + Arrays.toString(ViewTo)   , 10 ,45);
			g.drawString("CAMERA : " + Arrays.toString(ViewFrom) , 10 ,60);
			g.drawString("ZOOM   : " + zoom , 10 , 75);
			g.drawString("Vertical   Look : " + VerticalLook , 10 , 90);
			g.drawString("Horizontal Look(rad) : " + HorizontalLook , 10 , 105);
			g.drawString("Vertical angle   	 : " + (int)VAngle + "°" , 10 ,120);
			g.drawString("Number Of Polygons : " + DPolygons.size() , 10 ,135);
			g.drawString("Number Of Cubes    : " + Cubes.size() , 10 ,150);

			try{
				g.drawString("Focus Polys ID : " + FocusPolygon.toString() , 10 ,170);
			}catch (NullPointerException e){
				g.drawString("Focus Polys ID : " + "NULL" , 10 ,170);
			}
			g.setFont(new Font(Font.SANS_SERIF , Font.BOLD , 20));
			g.drawString("CONDITION: " + condition , 10 ,190);
			if(SWITCH_CUBE_OPERATION.get()){
				g.drawString("GENERATE MODE" , 10 , 220);
			}else{
				g.drawString("DELETE MODE" , 10 ,220);
			}
			g.drawString(t +"s", 10,240);
			g.drawString(sss , 10 , 260);
		}

		if(Control[10]){
			Press++;
		}

		//描画更新のインターバル
		SleepAndRefresh();

		if(FIRST_PERSON_MODE.get()){
			hitJudgment();
		}

		for(Cube c : Cubes){
			c.setDisplayCube();
		}

	}

	private void snakeMove() {
		double dx = 0.1;
		double dy = 0.1;
		for(Cube c : Cubes){
            if(c.move){
                c.reflection(dx,dy,0.1);
                c.updatePoly();
            }
        }
	}

	private void SleepAndRefresh(){
		long timeSLU = (long) (System.currentTimeMillis() - LastRefresh);

		Checks ++;

		if(Checks >= 15){
			drawFPS = Checks/((System.currentTimeMillis() - LastFPSCheck)/1000.0);
			LastFPSCheck = System.currentTimeMillis();
			Checks = 0;
			System.gc();
		}

		double maxFPS = 2000;
		if(timeSLU < 1000.0/ maxFPS){
			try {
				Thread.sleep((long) (1000.0/ maxFPS - timeSLU));
			} catch (InterruptedException ignored) {
			}
		}
		LastRefresh = System.currentTimeMillis();
		repaint();
		t = (System.currentTimeMillis() - Main.StartUpTime) / 1000;
	}


	private void hitJudgment() {
		
		if(
				Cubes.get(0).x < ViewFrom[0] && Cubes.get(0).dx >ViewFrom[0] && Cubes.get(0).y < ViewFrom[1] && Cubes.get(0).dy > ViewFrom[1]
		){
			condition = "in the BOX";
			if (Math.abs(Cubes.get(0).x - ViewFrom[0]) > Math.abs(Cubes.get(0).dx - ViewFrom[0])) {
				ViewFrom[0] += 0.1;
			}else{
				ViewFrom[0] -= 0.1;
			}

			if (Math.abs(Cubes.get(0).y - ViewFrom[1]) > Math.abs(Cubes.get(0).dy - ViewFrom[1])) {
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

	private void deleteCube() {
		if(Control[7] || DELETE.get()) {
			if(System.currentTimeMillis() - LastCubeDeleteTime >= deleteInterval) {
				for(int i = 0; i < Cubes.size() ; i ++) {
					for(int j = 0; j < Cubes.get(i).Polys.length ; j ++) {
						if( Cubes.get(i).Polys[j].DrawablePolygon.equals(FocusPolygon) ) {
							if(Cubes.get(i).getdelete()){
								//削除されたキューブの情報
								String dCube = Cubes.get(i).toString();

								Cubes.get(i).removeCube();
								LastCubeDeleteTime = System.currentTimeMillis();
								condition = "CUBE DELETED : " + dCube;
								break;
							}
						}
					}
				}
			}
		}


		//全削除
		if(Control[9]) {
			for(int i = 0; i < Cubes.size() ; i ++ ) {
				Cubes.get(i).removeCube();
				condition = "ALL DELETE";
			}
			System.gc();
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
		double d_tmp ;
		int i_tmp;
		for(int i = 0 ; i < k.length - 1 ; i++) {
			for(int j = 0 ; j < k.length - 1 ; j++) {
				if(k[j] < k[j + 1]) {
					d_tmp = k[j];
					i_tmp = NewOrder[j];
					NewOrder[j] = NewOrder[j + 1];
					k[j] = k[j + 1];
					NewOrder[j + 1] = i_tmp;
					k[j + 1] = d_tmp;
				}
			}
		}
	}

	/**
	 * マウスを非表示にするメソッド
	 */
	private void invisibleMouse(){
		 Toolkit toolkit = Toolkit.getDefaultToolkit();
		 BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
		 Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, new Point(0,0), "InvisibleCursor");
		 setCursor(invisibleCursor);
	}

	/**
	 * マウスエイムを描画するメソッド
	 */
	private void drawMouseAim(Graphics g){
		double aimSight = 4;	// センタークロスの大きさ
		g.setColor(Color.black);

		g.drawLine((int)(Main.screenSize.getWidth()/2 - aimSight), (int)(Main.screenSize.getHeight()/2), (int)(Main.screenSize.getWidth()/2 + aimSight), (int)(Main.screenSize.getHeight()/2));
		g.drawLine((int)(Main.screenSize.getWidth()/2), (int)(Main.screenSize.getHeight()/2 - aimSight), (int)(Main.screenSize.getWidth()/2), (int)(Main.screenSize.getHeight()/2 + aimSight));
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
		//マウスのスピード
		double movementSpeed = 0.5;
		double fx = MoveVector.x * movementSpeed;
		double fy = MoveVector.y * movementSpeed;
		double fz = MoveVector.z * movementSpeed;
		
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
			if(Math.abs(LastCubeGenerateTime - generate) > CubeGenerateInterval) {
				int rx = random.nextInt(255);
				int ry = random.nextInt(255);
				int rz = random.nextInt(255);
				int xyz = rx * 1_000_000 + ry * 1000 + rz;

				colorBox[counter1] = xyz;

				if(CoordinateCheck(xyz)) {
					Cubes.add(new Cube(-rx/25d , ry/25d , rz/25d + 2, 1, 1, 1, new Color(rx , ry , rz) ));
					condition = "CUBE GENERATED : (x,y,z) = " + rx /25 +"," + ry /25 + "," + rz /25;
//					Saves.write(rx /25 +"," + ry /25 + "," + rz /25 , new Color(rx , ry , rz));
				}
				
				counter1 ++ ;
				LastCubeGenerateTime = System.currentTimeMillis();
			}
		}

		//スクリーンショットと立方体の情報を記録する
		if(SCREEN_SHOT.get()){
			String date =  new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String time =  new SimpleDateFormat("HH-mm-ss-SSS").format(new Date());
			String name = "ScreenShot_" + date + "_" + time;
			p.setFileName(name);
			p.take();
			Json json = new Json(name);
			json.write("{");
			for (int i = 0; i < Cubes.size(); i++) {
				if(i != 0) {
					json.write(",");
				}
				json.write("Cube" , i , 1);
				json.write(Cubes.get(i).dataArray()  , 2);
				if (i == Cubes.size() - 1) {
					json.write("\n");
				}
			}
			json.write("}\n");

			System.gc();
			SCREEN_SHOT.set(false);
		}

		if(GENERATE.get()){
			generateCube();
            GENERATE.set(false);
		}
	}

	//キューブを生成する
	private void generateCube(){
		int cube = -1; //作成対象となるキューブが見つかっていない場合、-1となる
		int side = -1;
		double x = 0,y=0,z=0;
		double d = Cube.size;
		//ポリゴンの特定
		for (int i = 0; i < Cubes.size(); i++) {
			for(int j = 0; j < Cubes.get(i).Polys.length ; j ++) {
                if(FocusPolygon == Cubes.get(i).Polys[j].DrawablePolygon) {
					sss = Cubes.get(i).toString();
					side = j;
					cube = i;
					x = Cubes.get(i).x;
					y = Cubes.get(i).y;
					z = Cubes.get(i).z;
                    break;
                }
            }
		}
		if(cube != -1) {
			switch (side) {
				case 0 -> {
					sss = "0 : Bottom"; //-z
					if(generateCube(x,y,z-d)) {
						Cubes.add(new Cube(x,y,z-d,d,d,d, new Color(255,75,75) , true));
					}
				}
				case 1 -> {
					sss = "1 : Top"; // +z
					if(generateCube(x,y,z+d)) {
						Cubes.add(new Cube(x,y,z+d,d,d,d, new Color(75,255,255) , true));
					}
				}
				case 2 -> {
					sss = "2 : Right"; // +y
					if(generateCube(x,y+d,z)){
						Cubes.add(new Cube(x,y+d,z,d,d,d, new Color(75,75,255) , true));
					}
				}
				case 3 -> {
					sss = "3 : Front"; //-x
					if(generateCube(x-d,y,z)) {
						Cubes.add(new Cube(x-d,y,z,d,d,d, new Color(255,255,75) , true));
					}
				}
				case 4 -> {
					sss = "4 : Left"; // -y
					if(generateCube(x,y-d,z)) {
						Cubes.add(new Cube(x,y-d,z,d,d,d, new Color(75,255,75) , true));
					}
				}
				case 5 -> {
					sss = "5 : Back "; // +x
					if(generateCube(x+d,y,z)) {
						Cubes.add(new Cube(x+d,y,z,d,d,d, new Color(255,75,255) , true));
					}
				}
				default -> sss = "-1 : Nothing";
			}

			condition = "Generate Cube";
		}
	}

	private boolean generateCube(double x, double y, double z){
		boolean canGenerate = true;
		for (int i = 0; i < Cubes.size(); i++) {
			if (Cubes.get(i).x == x) {
				if (Cubes.get(i).y == y) {
					if (Cubes.get(i).z == z) {
						canGenerate = false;
					}
				}
			}
		}
		return canGenerate;
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

		if(FIRST_PERSON_MODE.get()){
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
			if(
					DPolygons.get(NewOrder[i]).DrawablePolygon.MouseOver()
					&& DPolygons.get(NewOrder[i]).draw
					&& DPolygons.get(NewOrder[i]).DrawablePolygon.visible
			){
				FocusPolygon = DPolygons.get(NewOrder[i]).DrawablePolygon;
				PolygonOver = DPolygons.get(NewOrder[i]).DrawablePolygon;
				
				break;
			}
		}	
	}



	private void MouseMovement(double NewX, double NewY){		
		//マウスがy軸(スクリーンの中央)からどれだけはなれたか計測
		double difX = (NewX - Main.screenSize.getWidth()/2);
		//マウスがx軸(スクリーンの中央)からどれだけはなれたか計測
		double difY = (NewY - Main.screenSize.getHeight()/2);
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

			switch (e.getKeyCode()) {
				//前進
				case KeyEvent.VK_W -> Control[0] = true;
				//後退
				case KeyEvent.VK_A -> Control[1] = true;
				//左へ
				case KeyEvent.VK_S -> Control[2] = true;
				//右へ
				case KeyEvent.VK_D -> Control[3] = true;
				//視点リセット
				case KeyEvent.VK_X -> Control[4] = true;
				//上へ
				case KeyEvent.VK_SPACE -> Control[5] = true;
				//下へ
				case KeyEvent.VK_SHIFT -> Control[6] = true;
				//下へ
				case KeyEvent.VK_BACK_SPACE -> Control[7] = true;
				//キューブを生成
				case KeyEvent.VK_R -> Control[8] = true;
				//キューブを削除
				case KeyEvent.VK_DELETE -> Control[9] = true;
				//詳細情報の表示/非表示
				case KeyEvent.VK_ENTER -> Details = !Details;
				case KeyEvent.VK_O -> {
					OutLines = !OutLines; //ライン削除
					if (OutLines) {
						condition = "Show outer frame";
					} else {
						condition = "Hide outer frame";
					}
				}
				//Escapeキーを押すと終了
				case KeyEvent.VK_ESCAPE -> System.exit(0);
				case KeyEvent.VK_P -> SCREEN_SHOT.set(true);
				case KeyEvent.VK_K -> GENERATE.set(true);

				case KeyEvent.VK_SLASH -> {
					//scoを反転
					SWITCH_CUBE_OPERATION.set(!SWITCH_CUBE_OPERATION.get());
					if(SWITCH_CUBE_OPERATION.get()) condition = "Switch Cube Operation : Generate";
					else condition = "Switch Cube Operation : Delete";

				}

				case KeyEvent.VK_COMMA -> Control[10] = true;

			}

		}
		
		//キーを離した時にfalse
		public void keyReleased(KeyEvent e) {

			switch (e.getKeyCode()) {
				case KeyEvent.VK_W -> Control[0] = false;
				case KeyEvent.VK_A -> Control[1] = false;
				case KeyEvent.VK_S -> Control[2] = false;
				case KeyEvent.VK_D -> Control[3] = false;
				case KeyEvent.VK_X -> Control[4] = false;
				case KeyEvent.VK_SPACE -> {
					Control[5] = false;
					condition = "NONE";
				}
				case KeyEvent.VK_SHIFT -> Control[6] = false;
				case KeyEvent.VK_BACK_SPACE -> Control[7] = false;
				case KeyEvent.VK_R -> Control[8] = false;
				case KeyEvent.VK_DELETE -> Control[9] = false;
				case KeyEvent.VK_COMMA -> Control[10] = false;
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
				r.mouseMove((int)Main.screenSize.getWidth()/2, (int)Main.screenSize.getHeight()/2);
			} catch (AWTException ignored) {

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
			//左クリック
			if(e.getButton() == MouseEvent.BUTTON1) {

				if (SWITCH_CUBE_OPERATION.get()) {
					GENERATE.set(true);
				}else{
					DELETE.set(true);
				}
			}

			//右クリック
			if(e.getButton() == MouseEvent.BUTTON3) {
				if(PolygonOver != null) {
					PolygonOver.seeThrough = !PolygonOver.seeThrough;
				}
			}
		}

		public void mouseReleased(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
                DELETE.set(false);
            }
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

	@Override
	public String toString() {
		return " FPS MODE : " + FIRST_PERSON_MODE
				+ "\n DEBUG MODE : " + DEBUG_MODE
				+ "\n GRAVITY	 : " + gravity;
	}
}