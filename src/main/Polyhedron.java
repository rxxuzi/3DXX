package main;

import java.awt.*;

abstract public class Polyhedron  {
	//���W
	double x, y, z, dx, dy, dz;
	double rotation = Math.PI*0.75;
	double[] RotAdd = new double[4];
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

	public void setRotAdd(){
		
		angle = new double[4];
		
		double xdif = - dx/2 + e;
		double ydif = - dy/2 + e;
		
		for(int i = 0 ; i < angle.length ; i++) {
			angle[i] = Math.atan(ydif/xdif);
			if(xdif < 0) {
				angle[i] += Math.PI;				
			}
			switch(i) {
			case 0 : xdif =    dx/2 + e; ydif = - dy/2 + e; break;
			case 1 : xdif =    dx/2 + e; ydif =   dy/2 + e; break;
			case 2 : xdif =  - dx/2 + e; ydif =   dy/2 + e; break;
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

	void remove(){
		
		for(int i = 0; i < 6; i ++) {
			Screen.DPolygons.remove(Polys[i]);			
		}
		Screen.Cubes.remove(this);
	}
}