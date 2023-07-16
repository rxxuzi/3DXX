package main;

import jdk.jfr.BooleanFlag;
import jet.Reader3dx;
import shot.Json;
import shot.Objct3DX;
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
 * Main.java��Frame�Ƀp�l���Ƃ���add����class
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
	 * 1.�d�͂̒ǉ�(���V�ł��Ȃ��Ȃ�)
	 * * 2.�J�����̍Œ�z���W��2�ƂȂ�
	 */
	public static final AtomicBoolean FIRST_PERSON_MODE = new AtomicBoolean(false);
	/**
	 * SWITCH_CUBE_OPERATION :
	 * true : �E�N���b�N����ƃu���b�N�𐶐�����
	 * false: �|���S����\��/��\���ɂ���
	 */
	private static final AtomicBoolean SWITCH_CUBE_OPERATION = new AtomicBoolean(true);
	/**
	 * �X�N���[���V���b�g�p�t���O
	 */
	private static final AtomicBoolean SCREEN_SHOT = new AtomicBoolean(false);
	/**
	 * �L���[�u���쐬���邩�̃t���O
	 */
	private final AtomicBoolean GENERATE = new AtomicBoolean(false);
	/**
	 * �L���[�u�������[�h����
	 */
	private final AtomicBoolean LOAD = new AtomicBoolean(false);
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
	public static Object PolygonOver = null ; //�J�[�\����̃|���S���̏��
	private static Object FocusPolygon = null;
	private static final long deleteInterval = 200;
	private static final long CubeGenerateInterval = 0; //default -> 100
	private long LastMoveTime = 0;
	private long LastCubeDeleteTime = 0;
	private long LastCubeGenerateTime = 0 ;
	static final double[] FViewFrom = { -2 , -2 , 10 };
	static final double[] FViewTo = {  -2 , 0 ,  5 };
	public static double[] ViewFrom = FViewFrom.clone(); //�J�����̍��W
	public static double[] ViewTo   = FViewTo.clone();	  //�I�u�W�F�N�g�̍��W
	public static double zoom = 1000;
	static double MinZoom = 100;
	static double MaxZoom = 5000;
	static double MouseX = 0 , MouseY = 0; //�}�E�X�̍��W
	private double drawFPS = 0;
	private double LastFPSCheck = 0 , Checks = 0 , LastRefresh = 0; //FPS�֘A
	private double VerticalLook = -0.9; //0.99 ~ -0.99�܂ŁA���̒l�̎��͏�����B���̒l�̎��͉�����
	private double HorizontalLook = 0; // �C�ӂ̐��l���Ƃ�A���W�A���P�ʂň������
	double VerticalRotationSpeed = 1000; //������]�̑���
	double HorizontalRotationSpeed = 500; //������]�̑���
	public static int[] NewOrder; //�z��DPolygon�̕`�悷�鏇�Ԃ�ێ�����z��
	static boolean OutLines = true; // �|���S���̃A�E�g���C���̕`������߂�t���O
	boolean[] Control = new boolean[15];//�L�[���͂̏����i�[����z��
	public static String condition = "NONE"; //��Ԃ�����������
	int Press = 10;
	public static long t ; //����
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

		Cubes.add(new Cube(5,-6,6,2,2,2,new Color(200,200,200),false , false));

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

		Cubes.add(new Cube(5,-6,6,2,2,2,new Color(200,200,200),false, false));
		Cubes.add(new Cube(5,6,2,2,2,2,new Color(200,200,200),false , false));

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
	/*�`��Ɋւ��郁�\�b�h*/

	public void paintComponent(Graphics g){

		//�`�惊�Z�b�g
		g.clearRect(0, 0, (int)Main.screenSize.getWidth(), (int)Main.screenSize.getHeight());

		//�J�����𓮂���
		KeyControl();

		//�t�H�[�J�X���ꂽ�|���S�����폜����
		deleteCube();

		//���̃J�����ʒu�ň�ʓI�Ȃ��̂����ׂČv�Z���܂��B
		Calculator.VectorInfo();

		// �|���S�������e���A�b�v�f�[�g
		for (DPolygon dPolygon : DPolygons) {
			dPolygon.updatePolygon();
		}

		//�S�Ẵ|���S���̋������\�[�g
		setOrder();

		//�}�E�X������Ă���|���S������肷��
		setPolygonOver();

		//setOrder�֐��Őݒ肳�ꂽ�����Ń|���S����`��

		IntStream.range(0, Screen.NewOrder.length).forEach(i -> DPolygons.get(Screen.NewOrder[i]).DrawablePolygon.drawPolygon(g));

		//��ʂ̒����ɃG�C��������
		drawMouseAim(g);

		//�t�H���g�̐ݒ�
		//�t�H���g�T�C�Y���w��
		int FontSize = 15;

		Font font = new Font(Font.DIALOG, Font.ITALIC,FontSize);
		//�p�x�̌v�Z
		double VAngle =  Math.toDegrees(Math.tan(VerticalLook));

		g.setFont(font);

		//�������w��
		snakeMove();

		if(Details){
			g.drawString("FPS : " + (int)drawFPS , 10, 15);
			g.drawString("ELAPSED TIME : " + (System.currentTimeMillis() - Main.StartUpTime ) + "ms" , 10 , 30);
			g.drawString("OBJECT : " + Arrays.toString(ViewTo)   , 10 ,45);
			g.drawString("CAMERA : " + Arrays.toString(ViewFrom) , 10 ,60);
			g.drawString("ZOOM   : " + zoom , 10 , 75);
			g.drawString("Vertical   Look : " + VerticalLook , 10 , 90);
			g.drawString("Horizontal Look(rad) : " + HorizontalLook , 10 , 105);
			g.drawString("Vertical angle   	 : " + (int)VAngle + "��" , 10 ,120);
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

		//�`��X�V�̃C���^�[�o��
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
	 * Control7�������ꂽ���A�t�H�[�J�X���Ă���L���[�u����肵�č폜����
	 *�@�A���ŏ�����̂�h���ׁAdelete interval��݂��Ă���
	 */

	private void deleteCube() {
		if(Control[7] || DELETE.get()) {
			if(System.currentTimeMillis() - LastCubeDeleteTime >= deleteInterval) {
				for(int i = 0; i < Cubes.size() ; i ++) {
					for(int j = 0; j < Cubes.get(i).Polys.length ; j ++) {
						if( Cubes.get(i).Polys[j].DrawablePolygon.equals(FocusPolygon) ) {
							if(Cubes.get(i).getdelete()){
								//�폜���ꂽ�L���[�u�̏��
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


		//�S�폜
		if(Control[9]) {
			allDelete();
		}
	}

	public void allDelete(){
		for(int i = 0; i < Cubes.size() ; i ++ ) {
			Cubes.get(i).removeCube();
			condition = "ALL DELETE";
		}
		System.gc();
	}

	//���������\�[�g
	private void setOrder(){
		//���������i�[����z��
		double[] k = new double[DPolygons.size()];
		//�\�[�g����������
		NewOrder = new int[DPolygons.size()];

		for(int i = 0 ; i < DPolygons.size() ; i++) {
			k[i] = DPolygons.get(i).AverageDistance;
			NewOrder[i] = i ;
		}

		/*
		���ϋ����̒Z�����Ƀ\�[�g����
		 (�o�u���\�[�g)
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
	 * �}�E�X���\���ɂ��郁�\�b�h
	 */
	private void invisibleMouse(){
		 Toolkit toolkit = Toolkit.getDefaultToolkit();
		 BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
		 Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, new Point(0,0), "InvisibleCursor");
		 setCursor(invisibleCursor);
	}

	/**
	 * �}�E�X�G�C����`�悷�郁�\�b�h
	 */
	private void drawMouseAim(Graphics g){
		double aimSight = 4;	// �Z���^�[�N���X�̑傫��
		g.setColor(Color.black);

		g.drawLine((int)(Main.screenSize.getWidth()/2 - aimSight), (int)(Main.screenSize.getHeight()/2), (int)(Main.screenSize.getWidth()/2 + aimSight), (int)(Main.screenSize.getHeight()/2));
		g.drawLine((int)(Main.screenSize.getWidth()/2), (int)(Main.screenSize.getHeight()/2 - aimSight), (int)(Main.screenSize.getWidth()/2), (int)(Main.screenSize.getHeight()/2 + aimSight));
	}



	//�L�[���͂𐧌�
	private void KeyControl(){
		Vector ViewVector = new Vector(ViewTo[0] - ViewFrom[0], ViewTo[1] - ViewFrom[1], ViewTo[2] - ViewFrom[2]);
		double xMove = 0, yMove = 0, zMove = 0;

		//(����)�P�ʃx�N�g�����擾
		Vector VerticalVector = new Vector (0, 0, 1);
		//�����ɓ����x�N�g��
		Vector SideViewVector = ViewVector.CrossProduct(VerticalVector);
		//�����ۂ̎��Ԃ��擾
		long moveTime = System.currentTimeMillis();
		//�����X�s�[�h���v�Z
		if(moveTime - LastMoveTime > moveInterval){
			//�O�Ɉړ�
			if(Control[0]){
				xMove += ViewVector.x * cameraSpeed;
				yMove += ViewVector.y * cameraSpeed;
				zMove += ViewVector.z * cameraSpeed;
			}

			//���Ɉړ�
			if(Control[2]){
				xMove -= ViewVector.x * cameraSpeed;
				yMove -= ViewVector.y * cameraSpeed;
				zMove -= ViewVector.z * cameraSpeed;
			}

			//���Ɉړ�
			if(Control[1]){
				xMove += SideViewVector.x * cameraSpeed;
				yMove += SideViewVector.y * cameraSpeed;
				zMove += SideViewVector.z * cameraSpeed;
			}

			//�E�Ɉړ�
			if(Control[3]){
				xMove -= SideViewVector.x * cameraSpeed;
				yMove -= SideViewVector.y * cameraSpeed;
				zMove -= SideViewVector.z * cameraSpeed;
			}

            LastMoveTime = System.currentTimeMillis();
        }

		
		//�ړ�����x�N�g��
		Vector MoveVector = new Vector(xMove, yMove, zMove);
		//�}�E�X�̃X�s�[�h
		double movementSpeed = 0.5;
		double fx = MoveVector.x * movementSpeed;
		double fy = MoveVector.y * movementSpeed;
		double fz = MoveVector.z * movementSpeed;
		
		MoveTo(ViewFrom[0] + fx, ViewFrom[1] + fy, ViewFrom[2] + fz);
		
		//�J�������W�����Z�b�g
		if(Control[4]) {
			for(int i = 0 ; i < FViewFrom.length ; i ++) {
				ViewFrom[i] = FViewFrom[i];
				ViewTo[i] = FViewTo[i];
			}
			zoom = 1000;
			condition = "View Reset";
		}
		
		//���̕�����z�ړ�
		if(Control[5]) {
			ViewFrom[2] += 0.4;
			ViewTo[2] += 0.4;	
			condition = "FLY";
		}
		
		//���̕�����z�ړ�
		if(Control[6]) {
			//z < 0�̏ꍇz = 0�Ŏ~�߂�
			if(ViewFrom[2] > 0.0) {
				ViewFrom[2] -= 0.4 + gravity;
				ViewTo[2] -= 0.4 + gravity;
			}else {
				ViewFrom[2] -= 0.1;
				ViewTo[2] -= 0.1;
			}
		}

		//�����_���ȃL���[�u�𐶐�
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

		//�X�N���[���V���b�g�Ɨ����̂̏����L�^����
		if(SCREEN_SHOT.get()){
			String date =  new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String time =  new SimpleDateFormat("HH-mm-ss-SSS").format(new Date());
			String name = "ScreenShot_" + date + "_" + time;
			p.setFileName(name);
			p.take();
			Json json = new Json(name);
			json.write("{");

			Objct3DX objct3DX = new Objct3DX();

			for (int i = 0; i < Cubes.size(); i++) {
				if(i != 0) {
					json.write(",");
				}
				json.write("Cube" , i , 1);
				json.write(Cubes.get(i).dataArray()  , 2);
				if (i == Cubes.size() - 1) {
					json.write("\n");
				}
				objct3DX.write(Cubes.get(i).toString());
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

	//�L���[�u�𐶐�����
	private void generateCube(){
		int cube = -1; //�쐬�ΏۂƂȂ�L���[�u���������Ă��Ȃ��ꍇ�A-1�ƂȂ�
		int side = -1;
		double x = 0,y=0,z=0;
		double d = Cube.size;
		//�|���S���̓���
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
						Cubes.add(new Cube(x,y,z-d,d,d,d, new Color(255,75,75) , false));
					}
				}
				case 1 -> {
					sss = "1 : Top"; // +z
					if(generateCube(x,y,z+d)) {
						Cubes.add(new Cube(x,y,z+d,d,d,d, new Color(75,255,255) , false));
					}
				}
				case 2 -> {
					sss = "2 : Right"; // +y
					if(generateCube(x,y+d,z)){
						Cubes.add(new Cube(x,y+d,z,d,d,d, new Color(75,75,255) , false));
					}
				}
				case 3 -> {
					sss = "3 : Front"; //-x
					if(generateCube(x-d,y,z)) {
						Cubes.add(new Cube(x-d,y,z,d,d,d, new Color(255,255,75) , false));
					}
				}
				case 4 -> {
					sss = "4 : Left"; // -y
					if(generateCube(x,y-d,z)) {
						Cubes.add(new Cube(x,y-d,z,d,d,d, new Color(75,255,75) , false));
					}
				}
				case 5 -> {
					sss = "5 : Back "; // +x
					if(generateCube(x+d,y,z)) {
						Cubes.add(new Cube(x+d,y,z,d,d,d, new Color(255,75,255) , false));
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


	//�L���[�u���d�����Ă��Ȃ����`�F�b�N
	private boolean CoordinateCheck(Integer xyz) {
		for(int i = 0 ; i < counter1 ; i ++) {
			if(colorBox[i] == xyz) {
				return false;
			}
		}
		return true;
	}

	//�J�����̍��W�����߂郁�\�b�h
	private void MoveTo(double x, double y, double z){
		ViewFrom[0] = x;
		ViewFrom[1] = y;
		ViewFrom[2] = z;

		if(FIRST_PERSON_MODE.get()){
			ViewFrom[2] = height;
		}else{
			if(ViewFrom[2] < 0.0) ViewFrom[2] = 0.0;
		}

		//�`��X�V
		updateView();
	}
	
	//�}�E�X������Ă���|���S�������
	private void setPolygonOver(){
		//��xnull�ƒ�`
		PolygonOver = null;
		//�T��
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
		//�}�E�X��y��(�X�N���[���̒���)����ǂꂾ���͂Ȃꂽ���v��
		double difX = (NewX - Main.screenSize.getWidth()/2);
		//�}�E�X��x��(�X�N���[���̒���)����ǂꂾ���͂Ȃꂽ���v��
		double difY = (NewY - Main.screenSize.getHeight()/2);
		difY *= 6 - Math.abs(VerticalLook) * 5;
		
		VerticalLook   -= difY  / VerticalRotationSpeed;
		HorizontalLook += difX / HorizontalRotationSpeed;
		
		//VerticalLook�̐�Βl��1.0�ȏ�ɂȂ�Ȃ��悤�ɂ���
		if(VerticalLook>0.999) VerticalLook = 0.999;
		if(VerticalLook<-0.999) VerticalLook = -0.999;
		
		updateView();
	}
	
	//���_���A�b�v�f�[�g
	private void updateView(){
		double r = Math.sqrt(1 - (VerticalLook * VerticalLook));
		ViewTo[0] = ViewFrom[0] + r * Math.cos(HorizontalLook); // x���ړ�
		ViewTo[1] = ViewFrom[1] + r * Math.sin(HorizontalLook);	// y���ړ�
		ViewTo[2] = ViewFrom[2] + VerticalLook;					// z���ړ�	
	}
	
	/*�L�[���͗pclass*/
	class KeyTyped extends KeyAdapter{
		
		//�L�[������������true
		public void keyPressed(KeyEvent e) {

			switch (e.getKeyCode()) {
				//�O�i
				case KeyEvent.VK_W -> Control[0] = true;
				//���
				case KeyEvent.VK_A -> Control[1] = true;
				//����
				case KeyEvent.VK_S -> Control[2] = true;
				//�E��
				case KeyEvent.VK_D -> Control[3] = true;
				//���_���Z�b�g
				case KeyEvent.VK_X -> Control[4] = true;
				//���
				case KeyEvent.VK_SPACE -> Control[5] = true;
				//����
				case KeyEvent.VK_SHIFT -> Control[6] = true;
				//����
				case KeyEvent.VK_BACK_SPACE -> Control[7] = true;
				//�L���[�u�𐶐�
				case KeyEvent.VK_R -> Control[8] = true;
				//�L���[�u���폜
				case KeyEvent.VK_DELETE -> Control[9] = true;
				//�ڍ׏��̕\��/��\��
				case KeyEvent.VK_ENTER -> Details = !Details;
				case KeyEvent.VK_O -> {
					OutLines = !OutLines; //���C���폜
					if (OutLines) {
						condition = "Show outer frame";
					} else {
						condition = "Hide outer frame";
					}
				}
				//Escape�L�[�������ƏI��
				case KeyEvent.VK_ESCAPE -> System.exit(0);
				case KeyEvent.VK_P -> SCREEN_SHOT.set(true);
				case KeyEvent.VK_K -> GENERATE.set(true);

				case KeyEvent.VK_SLASH -> {
					//sco�𔽓]
					SWITCH_CUBE_OPERATION.set(!SWITCH_CUBE_OPERATION.get());
					if(SWITCH_CUBE_OPERATION.get()) condition = "Switch Cube Operation : Generate";
					else condition = "Switch Cube Operation : Delete";

				}

				case KeyEvent.VK_COMMA -> Control[10] = true;
				case KeyEvent.VK_PERIOD -> Control[11] = true;

                case KeyEvent.VK_L -> {
					allDelete();
					Reader3dx.run();
					condition  = "Reader3dx : Run , overlap : " + Reader3dx.overlapCnt;
				}


			}

		}
		
		//�L�[�𗣂�������false
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
	 * �}�E�X�̏��������N���X
	 * �}�E�X�̍��W�A�}�E�X�N���b�N���A�}�E�X�z�C�[���̏�������
	 */
	class AboutMouse implements MouseListener , MouseMotionListener, MouseWheelListener{
		
		//�}�E�X�𒆉��Ɉړ������郁�\�b�h
		void CenterMouse(){
			try {
				r = new Robot();
				r.mouseMove((int)Main.screenSize.getWidth()/2, (int)Main.screenSize.getHeight()/2);
			} catch (AWTException ignored) {

			}
		}
		
		//�}�E�X���h���b�O�����Ƃ��̐���		
		public void mouseDragged(MouseEvent e) {
			MouseMovement(e.getX(), e.getY());
			MouseX = e.getX();
			MouseY = e.getY();
			CenterMouse();
		}

		//�}�E�X�̓����𐧌�
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
		
		//�}�E�X�̃N���b�N�������Ƃ��̐���
		public void mousePressed(MouseEvent e) {
			//���N���b�N
			if(e.getButton() == MouseEvent.BUTTON1) {

				if (SWITCH_CUBE_OPERATION.get()) {
					GENERATE.set(true);
				}else{
					DELETE.set(true);
				}
			}

			//�E�N���b�N
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