package no.ntnu.imt3281.sudoku;

public class mirrorSudoku {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
	}

	public static void horizontalMirror (int[][] array) {
		int[][] mirror = new int[9][9];
		for(int i = 0; i<9; i++) {
			for(int j = 0; j<9; j++) {
				mirror[i][j]=array[8-i][8-j];		
			}
		}
	}
	
	public static void verticalMirror (int[][] array) {
		int[][] mirror = new int[9][9];
		for(int j = 0; j<9; j++) {
			for(int i = 0; i<9; i++) {
				mirror[i][j]=array[8-i][8-j];		
			}
		}
	}
	
	public static void diagonalMirrorRL (int[][] array) {
		int[][] mirror = new int[9][9];
		int buffer;
		for(int i = 0; i<9; i++) {
			for(int j = 0; j<9; j++) {
				
			}
		}
		
	}
}



