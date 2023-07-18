package main;

import java.awt.Color;
import java.util.Random;

// This class is used to initialize the program.
public class Cube {
	//���W
	double x, y, z, dx, dy, dz;
	double rotation = Math.PI*0.75;
	double[] RotAdd = new double[4];
	//�F���
	Color color;
	Random random = new Random();

	//��]��̍��W
	double x1, x2, x3, x4, y1, y2, y3, y4;
	DPolygon[] Polys = new DPolygon[6];
	//�p�x�����[����z��
	double[] angle;

	final static double e = 0.0001;
	//update�Ŏ��s����鏈��
	public boolean isDisplay = true;
	
	//x,y,z���W
	double a = x + dx;
	double b = y + dy;
	double c = z + dz;

	//�J�����Ɨ������ő勗��
	public static final double maxDis = 100d;

	public boolean moveX = random.nextBoolean();
	public boolean moveY = random.nextBoolean();
	public boolean moveZ = random.nextBoolean();

	public static final double minX = 0d;
	public static final double minY = 0d;
	public static final double minZ = 0d;

	public static final double maxX = 30d;
	public static final double maxY = 30d;
	public static final double maxZ = 30d;

	public static final double size = 2.0;

	public boolean move = true;

	private boolean canDelete = true;
	
	//���W�ƐF��񂩂�|���S���𐶐�
	public Cube(double x, double y, double z, double dx, double dy, double dz, Color color){

		Polys[0] = new DPolygon(new double[]{x, a, a, x}, new double[]{y, y, b, b},  new double[]{z, z, z, z}, color, false);
		Polys[1] = new DPolygon(new double[]{x, a, a, x}, new double[]{y, y, b, b},  new double[]{c, c, c, c}, color, false);
		Polys[2] = new DPolygon(new double[]{x, x, x, x}, new double[]{y, y, b, b},  new double[]{z, c, c, z}, color, false);
		Polys[3] = new DPolygon(new double[]{a, a, a, a}, new double[]{y, y, b, b},  new double[]{z, c, c, z}, color, false);
		Polys[4] = new DPolygon(new double[]{x, x, a, a}, new double[]{y, y, y, y},  new double[]{z, c, c, z}, color, false);
		Polys[5] = new DPolygon(new double[]{x, x, a, a}, new double[]{b, b, b, b},  new double[]{z, c, c, z}, color, false);
		
		//Screen.java��DPolygons<List>�ɓ]��
		Screen.DPolygons.add(Polys[0]);
		Screen.DPolygons.add(Polys[1]);
		Screen.DPolygons.add(Polys[2]);
		Screen.DPolygons.add(Polys[3]);
		Screen.DPolygons.add(Polys[4]);
		Screen.DPolygons.add(Polys[5]);
		
		//�C���X�^���X�ϐ��ɑ��
		this.color = color;
		this.x = x;
		this.y = y;
		this.z = z;
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		
		//�p�x�����擾
		setRotAdd();

		setDisplayCube();

		if(isDisplay){
			updatePoly();
		}
	}

	//���W�ƐF��񂩂�|���S���𐶐� + �Œ肷�邩�ǂ���
	public Cube(double x, double y, double z, double dx, double dy, double dz, Color color, boolean move){

		Polys[0] = new DPolygon(new double[]{x, a, a, x}, new double[]{y, y, b, b},  new double[]{z, z, z, z}, color, false);
		Polys[1] = new DPolygon(new double[]{x, a, a, x}, new double[]{y, y, b, b},  new double[]{c, c, c, c}, color, false);
		Polys[2] = new DPolygon(new double[]{x, x, x, x}, new double[]{y, y, b, b},  new double[]{z, c, c, z}, color, false);
		Polys[3] = new DPolygon(new double[]{a, a, a, a}, new double[]{y, y, b, b},  new double[]{z, c, c, z}, color, false);
		Polys[4] = new DPolygon(new double[]{x, x, a, a}, new double[]{y, y, y, y},  new double[]{z, c, c, z}, color, false);
		Polys[5] = new DPolygon(new double[]{x, x, a, a}, new double[]{b, b, b, b},  new double[]{z, c, c, z}, color, false);

		//Screen.java��DPolygons<List>�ɓ]��
		Screen.DPolygons.add(Polys[0]);
		Screen.DPolygons.add(Polys[1]);
		Screen.DPolygons.add(Polys[2]);
		Screen.DPolygons.add(Polys[3]);
		Screen.DPolygons.add(Polys[4]);
		Screen.DPolygons.add(Polys[5]);

		//�C���X�^���X�ϐ��ɑ��
		this.color = color;
		this.x = x;
		this.y = y;
		this.z = z;
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;

		//�p�x�����擾
		setRotAdd();

		setDisplayCube();

		if(isDisplay){
			updatePoly();
		}

		this.move = move;
	}

	//���W�ƐF��񂩂�|���S���𐶐� + �Œ肷�邩�ǂ���
	public Cube(double x, double y, double z, double dx, double dy, double dz, Color color, boolean move ,boolean delete){

		Polys[0] = new DPolygon(new double[]{x, a, a, x}, new double[]{y, y, b, b},  new double[]{z, z, z, z}, color, false);
		Polys[1] = new DPolygon(new double[]{x, a, a, x}, new double[]{y, y, b, b},  new double[]{c, c, c, c}, color, false);
		Polys[2] = new DPolygon(new double[]{x, x, x, x}, new double[]{y, y, b, b},  new double[]{z, c, c, z}, color, false);
		Polys[3] = new DPolygon(new double[]{a, a, a, a}, new double[]{y, y, b, b},  new double[]{z, c, c, z}, color, false);
		Polys[4] = new DPolygon(new double[]{x, x, a, a}, new double[]{y, y, y, y},  new double[]{z, c, c, z}, color, false);
		Polys[5] = new DPolygon(new double[]{x, x, a, a}, new double[]{b, b, b, b},  new double[]{z, c, c, z}, color, false);

		//Screen.java��DPolygons<List>�ɓ]��
		Screen.DPolygons.add(Polys[0]);
		Screen.DPolygons.add(Polys[1]);
		Screen.DPolygons.add(Polys[2]);
		Screen.DPolygons.add(Polys[3]);
		Screen.DPolygons.add(Polys[4]);
		Screen.DPolygons.add(Polys[5]);

		//�C���X�^���X�ϐ��ɑ��
		this.color = color;
		this.x = x;
		this.y = y;
		this.z = z;
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;

		//�p�x�����擾
		setRotAdd();

		setDisplayCube();

		if(isDisplay){
			updatePoly();
		}

		this.move = move;
		this.canDelete = delete;
	}

	public void setRotAdd(){
		
		angle = new double[4];
		
		double xdif = - dx/2 + e;
		double ydif = - dy/2 + e;
		
		for(int i = 0 ; i < angle.length ; i++) {
			angle[i] = Math.atan(ydif/xdif);
			if(xdif < 0) {
				angle[i] += Math.PI;				
			}
			switch (i) {
				case 0 -> {
					xdif =  dx / 2 + e;
					ydif = -dy / 2 + e;
				}
				case 1 -> {
					xdif =  dx / 2 + e;
					ydif =  dy / 2 + e;
				}
				case 2 -> {
					xdif = -dx / 2 + e;
					ydif =  dy / 2 + e;
				}
			}
			
			RotAdd[i] = angle[i] + 0.25 * Math.PI;
			
		}
	}

	public void setDisplayCube(){
		double lengthX = Math.abs(Screen.ViewFrom[0] - x);
		double lengthY = Math.abs(Screen.ViewFrom[1] - y);
		double lengthZ = Math.abs(Screen.ViewFrom[2] - z);

		this.isDisplay = (lengthX + lengthY + lengthZ) / 3 < maxDis;
	}
	
	@SuppressWarnings("unused")
	private void UpdateDirection(double toX, double toY){
		double xdif = toX - (x + dx/2) + e;
		double ydif = toY - (y + dy/2) + e;
		
		double anglet = Math.atan(ydif/xdif) + 0.75 * Math.PI;

		if(xdif<0){
			anglet += Math.PI;
		}

		rotation = anglet;
		updatePoly();		
	}

	void updatePoly(){

		if(isDisplay){

			for(int i = 0; i < 6; i++){
				Screen.DPolygons.add(Polys[i]);
				Screen.DPolygons.remove(Polys[i]);
			}

			double radius = Math.sqrt(dx*dx + dy*dy);

			x1 = x + dx*0.5 + radius*0.5*Math.cos(rotation + RotAdd[0]);
			x2 = x + dx*0.5 + radius*0.5*Math.cos(rotation + RotAdd[1]);
			x3 = x + dx*0.5 + radius*0.5*Math.cos(rotation + RotAdd[2]);
			x4 = x + dx*0.5 + radius*0.5*Math.cos(rotation + RotAdd[3]);

			y1 = y + dy*0.5 + radius*0.5*Math.sin(rotation + RotAdd[0]);
			y2 = y + dy*0.5 + radius*0.5*Math.sin(rotation + RotAdd[1]);
			y3 = y + dy*0.5 + radius*0.5*Math.sin(rotation + RotAdd[2]);
			y4 = y + dy*0.5 + radius*0.5*Math.sin(rotation + RotAdd[3]);

			Polys[0].x = new double[]{x1, x2, x3, x4};
			Polys[0].y = new double[]{y1, y2, y3, y4};
			Polys[0].z = new double[]{ z,  z,  z,  z};

			Polys[1].x = new double[]{x4, x3, x2, x1};
			Polys[1].y = new double[]{y4, y3, y2, y1};
			Polys[1].z = new double[]{z+dz, z+dz, z+dz, z+dz};

			Polys[2].x = new double[]{x1, x1, x2, x2};
			Polys[2].y = new double[]{y1, y1, y2, y2};
			Polys[2].z = new double[]{z, z+dz, z+dz, z};

			Polys[3].x = new double[]{x2, x2, x3, x3};
			Polys[3].y = new double[]{y2, y2, y3, y3};
			Polys[3].z = new double[]{z, z+dz, z+dz, z};

			Polys[4].x = new double[]{x3, x3, x4, x4};
			Polys[4].y = new double[]{y3, y3, y4, y4};
			Polys[4].z = new double[]{z, z+dz, z+dz, z};

			Polys[5].x = new double[]{x4, x4, x1, x1};
			Polys[5].y = new double[]{y4, y4, y1, y1};
			Polys[5].z = new double[]{z, z+dz, z+dz, z};

		}
	}

	public void reflection(double ddx , double ddy, double ddz){
		if(x+dx > maxX){
            moveX = false;
        }
		if(x < minX){
            moveX = true;
        }
		if(y+dy > maxY){
			moveY = false;
		}
		if(y < minY){
			moveY = true;
		}
		if(z+dz > maxZ){
            moveZ = false;
        }
		if(z < minZ){
			moveZ = true;
		}

		if(moveX){
			x += ddx;
		}else{
			x -= ddx;
		}

		if (moveY){
            y += ddy;
        }else{
			y  -= ddy;
		}

		if (moveZ){
			z += ddz;
		}else{
			z -= ddz;
		}
	}

	@SuppressWarnings("unused")
	public void reflection2D(double ddx , double ddy){
		if(x+dx > maxX){
            moveX = false;
        }
		if(x < minX){
            moveX = true;
        }
		if(y+dy > maxY){
			moveY = false;
		}
		if(y < minY){
			moveY = true;
		}

		if(moveX){
			x += ddx;
		}else{
			x -= ddx;
		}

		if (moveY){
            y += ddy;
        }else{
			y  -= ddy;
		}
	}



	void removeCube(){
		if(canDelete){
			for(int i = 0; i < 6; i ++) {
				Screen.DPolygons.remove(Polys[i]);
			}
			Screen.Cubes.remove(this);
		}
	}


	public String[] dataArray(){
		String[] data = new String[6];
		data[0] = "\"x \" : " + x  +", \"y \" : " + y  +", \"z \" : " + z  +",\n";
		data[1] = "\"dx \": " + dx +", \"dy \": " + dy +", \"dz \": " + dz +",\n";
		data[2] = "\"rotation \" : " + rotation +",\n";
		data[3] = "\"isDisplay \" : \"" + isDisplay + "\""+",\n";
		data[4] = "\"move  \" : " + "\"" + move + "\""+",\n";
		data[5] = "\"Color \" : [" + this.color.getRed() + ", " + this.color.getGreen() + ", " + this.color.getBlue() + "]";
        return data;
	}

	/**
	 * @since {@index }
	 * @return x,y,z,dx,dy,dz,rotation,isDisplay,move,canDelete,color
	 */
	@Override
    public String toString() {
		String s;
		s = x + "," + y + "," + z + "," + dx + "," + dy + "," + dz + ","   + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "," + move + ","  + canDelete + "," ;
		return s;
	}

	public boolean overlap(double x, double y , double z , double dx, double dy, double dz) {
		return x + dx == this.x && x == this.x + dx && y + dy == this.y && y == this.y + dy && z + dz == this.z && z == this.z + dz;
	}
	
	public boolean getdelete() {
		return canDelete;
	}
}