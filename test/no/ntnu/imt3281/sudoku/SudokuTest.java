package no.ntnu.imt3281.sudoku;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

public class SudokuTest {

	private static final int NUMB_COLS = 9;
	private static final int NUMB_ROWS = 9;

	@Test
	public void testCheckValid() throws BadNumberException {
		Sudoku tester = new Sudoku();
		int[][] array = {{5, 3, -1, -1, 7, -1, -1, -1, -1},
		         {6, -1, -1, 1, 9, 5, -1, -1, -1}, 
		         {-1, 9, 8, -1, -1, -1, -1, 6, -1}, 
		         {8, -1, -1, -1, 6, -1, -1, -1, 3}, 
		         {4, -1, -1, 8, -1, 3, -1, -1, 1}, 
		         {7, -1, -1, -1, 2, -1, -1, -1, 6}, 
		         {-1, 6, -1, -1, -1, -1, 2, 8, -1}, 
		         {-1, -1, -1, 4, 1, 9, -1, -1, 5}, 
		         {-1, -1, -1, -1, 8, -1, -1, 7, 9}};
		tester.update(array);
		String i = "2";
		assertEquals(true, tester.checkValid(0, 8, i));
	}

	@Test
	public void testCheckRow() throws BadNumberException {
		Sudoku tester = new Sudoku();
		int[][] array = {{5, 3, 4, 6, 7, 8, 9, 1, 2},
		         {6, 7, 2, 1, 9, 5, 3, 4, 8}, 
		         {1, 9, 8, 3, 4, 2, 5, 6, 7}, 
		         {8, 5, 9, 7, 6, 1, 4, 2, 3}, 
		         {4, 2, 6, 8, 5, 3, 7, 9, 1}, 
		         {7, 1, 3, 9, 2, 4, 8, 5, 6}, 
		         {9, 6, 1, 5, 3, 7, 2, 8, 4}, 
		         {2, 8, 7, 4, 1, 9, 6, 3, 5}, 
		         {3, 4, 5, 2, 8, 6, 1, 7, 9}};
		tester.update(array);
		String i = "1";
		assertEquals(true, tester.checkRow(1, 1, i));
	}

	@Test
	public void testCheckCol() throws BadNumberException {
		Sudoku tester = new Sudoku();
		int[][] array = {{5, 3, 4, 6, 7, 8, 9, 1, 2},
		         {6, 7, 2, 1, 9, 5, 3, 4, 8}, 
		         {1, 9, 8, 3, 4, 2, 5, 6, 7}, 
		         {8, 5, 9, 7, 6, 1, 4, 2, 3}, 
		         {4, 2, 6, 8, 5, 3, 7, 9, 1}, 
		         {7, 1, 3, 9, 2, 4, 8, 5, 6}, 
		         {9, 6, 1, 5, 3, 7, 2, 8, 4}, 
		         {2, 8, 7, 4, 1, 9, 6, 3, 5}, 
		         {3, 4, 5, 2, 8, 6, 1, 7, 9}};
		tester.update(array);
		String i = "1";
		assertEquals(true, tester.checkColumn(0, 0, i));
	}

	@Test
	public void testCheckBox() throws BadNumberException {
		Sudoku tester = new Sudoku();
		int[][] array = {{5, 3, 4, 6, 7, 8, 9, 1, 2},
		         {6, 7, 2, 1, 9, 5, 3, 4, 8}, 
		         {1, 9, 8, 3, 4, 2, 5, 6, 7}, 
		         {8, 5, 9, 7, 6, 1, 4, 2, 3}, 
		         {4, 2, 6, 8, 5, 3, 7, 9, 1}, 
		         {7, 1, 3, 9, 2, 4, 8, 5, 6}, 
		         {9, 6, 1, 5, 3, 7, 2, 8, 4}, 
		         {2, 8, 7, 4, 1, 9, 6, 3, 5}, 
		         {3, 4, 5, 2, 8, 6, 1, 7, 9}};
		tester.update(array);
		String i = "1";
		assertEquals(true, tester.checkBox(1, 1, i));
		
	}

	@Test
	public void testCheckFinished() {
		Sudoku tester = new Sudoku();
		int[][] array = {{5, 3, 4, 6, 7, 8, 9, 1, 2},
		         {6, 7, 2, 1, 9, 5, 3, 4, 8}, 
		         {1, 9, 8, 3, 4, 2, 5, 6, 7}, 
		         {8, 5, 9, 7, 6, 1, 4, 2, 3}, 
		         {4, 2, 6, 8, 5, 3, 7, 9, 1}, 
		         {7, 1, 3, 9, 2, 4, 8, 5, 6}, 
		         {9, 6, 1, 5, 3, 7, 2, 8, 4}, 
		         {2, 8, 7, 4, 1, 9, 6, 3, 5}, 
		         {3, 4, 5, 2, 8, 6, 1, 7, 9}};
		tester.update(array);
		assertEquals(true, tester.checkFinished());
	}

	@Test
	public void testFinished() {
		
	}

	@Test
	public void testLock() {
		Sudoku tester = new Sudoku();
		int[][] array = {{5, 3, -1, -1, 7, -1, -1, -1, -1},
		         {6, -1, -1, 1, 9, 5, -1, -1, -1}, 
		         {-1, 9, 8, -1, -1, -1, -1, 6, -1}, 
		         {8, -1, -1, -1, 6, -1, -1, -1, 3}, 
		         {4, -1, -1, 8, -1, 3, -1, -1, 1}, 
		         {7, -1, -1, -1, 2, -1, -1, -1, 6}, 
		         {-1, 6, -1, -1, -1, -1, 2, 8, -1}, 
		         {-1, -1, -1, 4, 1, 9, -1, -1, 5}, 
		         {-1, -1, -1, -1, 8, -1, -1, 7, 9}};
		tester.update(array);
	}

	@Test
	public void testClear() {
		Sudoku tester = new Sudoku();
		tester.readJSON();
		tester.clear();
		for (int i = 1; i < NUMB_ROWS; i++) {
			for (int j = 1; i < NUMB_COLS; i++) {
				assertEquals(0, tester.returnNumber(i, j));
			}
		}
	}
	
	@Test
	public void testreadJSON() {
		Sudoku tester = new Sudoku();
		tester.readJSON();
		int[][] array = {{5, 3, -1, -1, 7, -1, -1, -1, -1},
		         {6, -1, -1, 1, 9, 5, -1, -1, -1}, 
		         {-1, 9, 8, -1, -1, -1, -1, 6, -1}, 
		         {8, -1, -1, -1, 6, -1, -1, -1, 3}, 
		         {4, -1, -1, 8, -1, 3, -1, -1, 1}, 
		         {7, -1, -1, -1, 2, -1, -1, -1, 6}, 
		         {-1, 6, -1, -1, -1, -1, 2, 8, -1}, 
		         {-1, -1, -1, 4, 1, 9, -1, -1, 5}, 
		         {-1, -1, -1, -1, 8, -1, -1, 7, 9}};
		for (int i = 1; i < NUMB_ROWS; i++) {
			for (int j = 1; i < NUMB_COLS; i++) {
				assertEquals(array[i][j], tester.returnNumber(i, j));
			}
		}
		
	}
	
	@Test
	public void testFlipH() {
		Sudoku tester = new Sudoku();
		int[][] array = {{5, 3, -1, -1, 7, -1, -1, -1, -1},
		         {6, -1, -1, 1, 9, 5, -1, -1, -1}, 
		         {-1, 9, 8, -1, -1, -1, -1, 6, -1}, 
		         {8, -1, -1, -1, 6, -1, -1, -1, 3}, 
		         {4, -1, -1, 8, -1, 3, -1, -1, 1}, 
		         {7, -1, -1, -1, 2, -1, -1, -1, 6}, 
		         {-1, 6, -1, -1, -1, -1, 2, 8, -1}, 
		         {-1, -1, -1, 4, 1, 9, -1, -1, 5}, 
		         {-1, -1, -1, -1, 8, -1, -1, 7, 9}};
		tester.update(array);
		tester.flippH();
		int[][] arrayPostFlipp = {{-1, -1, -1, -1, 8, -1, -1, 7, 9},
				{-1, -1, -1, 4, 1, 9, -1, -1, 5}, 
		         {-1, 6, -1, -1, -1, -1, 2, 8, -1}, 
		         {7, -1, -1, -1, 2, -1, -1, -1, 6}, 
		         {4, -1, -1, 8, -1, 3, -1, -1, 1}, 
		         {8, -1, -1, -1, 6, -1, -1, -1, 3}, 
		         {-1, 9, 8, -1, -1, -1, -1, 6, -1}, 
		         {6, -1, -1, 1, 9, 5, -1, -1, -1}, 
		         {5, 3, -1, -1, 7, -1, -1, -1, -1}};
		for (int i = 1; i < NUMB_ROWS; i++) {
			for (int j = 1; i < NUMB_COLS; i++) {
				assertEquals(arrayPostFlipp[i][j], tester.returnNumber(i, j));
			}
		}
		
	}
	
	@Test
	public void testFlipV() {
		Sudoku tester = new Sudoku();
		int[][] array = {{-1, -1, -1, -1, 7, -1, -1, 3, 5},
		         {-1, -1, -1, 5, 9, 1, -1, -1, 6}, 
		         {-1, 6, -1, -1, -1, -1, 8, 9, -1}, 
		         {3, -1, -1, -1, 6, -1, -1, -1, 8}, 
		         {1, -1, -1, 3, -1, 8, -1, -1, 4}, 
		         {6, -1, -1, -1, 2, -1, -1, -1, 7}, 
		         {-1, 8, 2, -1, -1, -1, 2, 6, -1}, 
		         {5, -1, -1, 9, 1, 4, -1, -1, -1}, 
		         {9, 7, -1, -1, 8, -1, -1, -1, -1}};
		tester.update(array);
		tester.flippV();
		int[][] arrayPostFlipp = {{5, 3, -1, -1, 7, -1, -1, -1, -1},
		         {6, -1, -1, 1, 9, 5, -1, -1, -1}, 
		         {-1, 9, 8, -1, -1, -1, -1, 6, -1}, 
		         {8, -1, -1, -1, 6, -1, -1, -1, 3}, 
		         {4, -1, -1, 8, -1, 3, -1, -1, 1}, 
		         {7, -1, -1, -1, 2, -1, -1, -1, 6}, 
		         {-1, 6, -1, -1, -1, -1, 2, 8, -1}, 
		         {-1, -1, -1, 4, 1, 9, -1, -1, 5}, 
		         {-1, -1, -1, -1, 8, -1, -1, 7, 9}};
		for (int i = 1; i < NUMB_ROWS; i++) {
			for (int j = 1; i < NUMB_COLS; i++) {
				assertEquals(arrayPostFlipp[i][j], tester.returnNumber(i, j));
			}
		}
		
		
	}
	
	@Test
	public void testFlipDB() {
		Sudoku tester = new Sudoku();
		int[][] array = {{5, 3, -1, -1, 7, -1, -1, -1, -1},
		         {6, -1, -1, 1, 9, 5, -1, -1, -1}, 
		         {-1, 9, 8, -1, -1, -1, -1, 6, -1}, 
		         {8, -1, -1, -1, 6, -1, -1, -1, 3}, 
		         {4, -1, -1, 8, -1, 3, -1, -1, 1}, 
		         {7, -1, -1, -1, 2, -1, -1, -1, 6}, 
		         {-1, 6, -1, -1, -1, -1, 2, 8, -1}, 
		         {-1, -1, -1, 4, 1, 9, -1, -1, 5}, 
		         {-1, -1, -1, -1, 8, -1, -1, 7, 9}};
		tester.update(array);
		tester.flippDB();
		int[][] arrayPostFlipp = {{5, 6, -1, 8, 4, 7, -1, -1, -1},
		         {3, -1, 9, -1, -1, -1, 6, -1, -1}, 
		         {-1, -1, 8, -1, -1, -1, -1, -1, -1}, 
		         {-1, 1, -1, -1, 8, -1, -1, 4, -1}, 
		         {7, 9, -1, 6, -1, 2, -1, 1, 8}, 
		         {-1, 5, -1, -1, 3, -1, -1, 9, -1}, 
		         {-1, -1, -1, -1, -1, -1, 2, -1, -1}, 
		         {-1, -1, 6, -1, -1, -1, 8, -1, 7}, 
		         {-1, -1, -1, 3, 1, 6, -1, 5, 9}};
		for (int i = 1; i < NUMB_ROWS; i++) {
			for (int j = 1; i < NUMB_COLS; i++) {
				assertEquals(arrayPostFlipp[i][j], tester.returnNumber(i, j));
			}
		}
		
		
	}
	
	@Test
	public void testFlipDR() {
		Sudoku tester = new Sudoku();
		int[][] array = {{5, 3, -1, -1, 7, -1, -1, -1, -1},
		         {6, -1, -1, 1, 9, 5, -1, -1, -1}, 
		         {-1, 9, 8, -1, -1, -1, -1, 6, -1}, 
		         {8, -1, -1, -1, 6, -1, -1, -1, 3}, 
		         {4, -1, -1, 8, -1, 3, -1, -1, 1}, 
		         {7, -1, -1, -1, 2, -1, -1, -1, 6}, 
		         {-1, 6, -1, -1, -1, -1, 2, 8, -1}, 
		         {-1, -1, -1, 4, 1, 9, -1, -1, 5}, 
		         {-1, -1, -1, -1, 8, -1, -1, 7, 9}};
		tester.update(array);
		tester.flippDR();
		int[][] arrayPostFlipp = {{9, 5, -1, 6, 1, 3, -1, -1, -1},
		         {7, -1, 8, -1, -1, -1, 6, -1, -1}, 
		         {-1, -1, 2, -1, -1, -1, -1, -1, -1}, 
		         {-1, 9, -1, -1, 3, -1, -1, 5, -1}, 
		         {8, 1, -1, 2, -1, 6, -1, 9, 7}, 
		         {-1, 4, -1, -1, 8, -1, -1, 1, -1}, 
		         {-1, -1, -1, -1, -1, -1, 8, -1, -1}, 
		         {-1, -1, 6, -1, -1, -1, 9, -1, 3}, 
		         {-1, -1, -1, 7, 4, 9, -1, 6, 5}};
		for (int i = 1; i < NUMB_ROWS; i++) {
			for (int j = 1; i < NUMB_COLS; i++) {
				assertEquals(arrayPostFlipp[i][j], tester.returnNumber(i, j));
			}
		}
		
		
	}
}