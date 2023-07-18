package main;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * This class is used to convert the text file in the rsc folder to a 2D array and add the cubes to the Screen.java class.
 * @since 2.1
 */
class TextToObject {
	//��������i�[����List
	ArrayList<String> line = new ArrayList<>();
	
	public TextToObject(String path) {
		
		File f = new File(path);
		Scanner sc;
		
		try {
			//�t�@�C���̓ǂݍ���
			sc = new Scanner(f);
			//�s���J�E���g�p�ϐ�
			//�P�s���t�@�C�����當����𔲂��o��
			while(sc.hasNextLine()) {
				line.add(sc.nextLine());
//	            System.out.println(line.get(c));
			}
			//���\�[�X���
			sc.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
//		System.out.println("copied");

		//�s��
		int row =  line.size();
		//��
		int col = line.get(0).length();
		//�t�@�C������2�����z��ɑ������ׂɒ�`
		int[][] x = new int[row][col];
		
		for(int i = 0 ; i < row; i ++ ) {
			for(int j = 0 ; j < col; j ++ ) {
				//String -> Char -> int �ɕϊ�
				x[i][j] = Character.getNumericValue(line.get(i).charAt(j));
			}
		}

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				//���ꂼ��ɑΉ������F�ɂ���Cube���쐬
				switch (x[i][j]) {
					case 1 -> Screen.Cubes.add(new Cube(j, -10, row - i, 1, 1, 1, new Color(255, 0, 0), true));
					case 2 -> Screen.Cubes.add(new Cube(j, -10, row - i, 1, 1, 1, new Color(98, 86, 26), true));
					case 3 -> Screen.Cubes.add(new Cube(j, -10, row - i, 1, 1, 1, new Color(237, 148, 102), true));
				}
			}
		}
	}
}