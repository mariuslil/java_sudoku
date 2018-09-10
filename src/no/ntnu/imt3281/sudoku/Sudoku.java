package no.ntnu.imt3281.sudoku;

public class Sudoku {
	public static void main(String args[]) {
		
	}
	
	private int[][] originalBoard = new int[9][9];
	private int[][] board = new int[9][9];
	private static int selectedRow;
	private static int selectedColumn;
	
	public static boolean checkValid(int row, int column, int[][] board, int number) {
		for (int r = 0;r < 9;r++) {
			if (number == board[row][r]) {
				return false;
			}
		}

		for (int c = 0;c < 9;c++) {
			if (number == board[c][column]) {
				return false;
			}
		}
		
		int block_x = (selectedRow/3)*3;
		int block_y = (selectedColumn/3)*3;
		
		for (int n = 0;n < 3;n++) {
			for (int m = 0;m < 3;m++) {
				if (number == board[block_x+1][block_y+1]) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	public static void insert(int number) {
		
	}
	
	
	
	
}
