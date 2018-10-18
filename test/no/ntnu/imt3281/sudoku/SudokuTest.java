package no.ntnu.imt3281.sudoku;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SudokuTest {

	private static final int NUMB_COLS = 9;
	private static final int NUMB_ROWS = 9;
	Sudoku tester = new Sudoku();
	private static int[][] array = { { 5, 3, -1, -1, 7, -1, -1, -1, -1 }, { 6, -1, -1, 1, 9, 5, -1, -1, -1 },
			{ -1, 9, 8, -1, -1, -1, -1, 6, -1 }, { 8, -1, -1, -1, 6, -1, -1, -1, 3 },
			{ 4, -1, -1, 8, -1, 3, -1, -1, 1 }, { 7, -1, -1, -1, 2, -1, -1, -1, 6 },
			{ -1, 6, -1, -1, -1, -1, 2, 8, -1 }, { -1, -1, -1, 4, 1, 9, -1, -1, 5 },
			{ -1, -1, -1, -1, 8, -1, -1, 7, 9 } };

	@Test
	public void testCheckValid() throws BadNumberException {
		tester.update(array);
		String i = "2";
		assertEquals(true, tester.checkValid(0, 8, i));
	}

	@Test
	public void testCheckRow() throws BadNumberException {
		tester.update(array);
		String i = "1";
		assertEquals(true, tester.checkRow(1, 1, i));
	}

	@Test
	public void testCheckCol() throws BadNumberException {
		tester.update(array);
		String i = "1";
		assertEquals(true, tester.checkColumn(0, 0, i));
	}

	@Test
	public void testCheckBox() throws BadNumberException {
		tester.update(array);
		String i = "1";
		assertEquals(true, tester.checkBox(1, 1, i));

	}

	@Test
	public void testCheckFinished() {
		tester.update(array);
		assertEquals(true, tester.checkFinished());
	}

	@Test
	public void testFinished() {

	}

	@Test
	public void testLock() {
		tester.update(array);
	}

	@Test
	public void testClear() {
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
		for (int i = 1; i < NUMB_ROWS; i++) {
			for (int j = 1; i < NUMB_COLS; i++) {
				assertEquals(array[i][j], tester.returnNumber(i, j));
			}
		}

	}

	@Test
	public void testFlipH() {
		tester.update(array);
		tester.flippH();
		int[][] arrayPostFlipp = { { -1, -1, -1, -1, 8, -1, -1, 7, 9 }, { -1, -1, -1, 4, 1, 9, -1, -1, 5 },
				{ -1, 6, -1, -1, -1, -1, 2, 8, -1 }, { 7, -1, -1, -1, 2, -1, -1, -1, 6 },
				{ 4, -1, -1, 8, -1, 3, -1, -1, 1 }, { 8, -1, -1, -1, 6, -1, -1, -1, 3 },
				{ -1, 9, 8, -1, -1, -1, -1, 6, -1 }, { 6, -1, -1, 1, 9, 5, -1, -1, -1 },
				{ 5, 3, -1, -1, 7, -1, -1, -1, -1 } };
		for (int i = 1; i < NUMB_ROWS; i++) {
			for (int j = 1; i < NUMB_COLS; i++) {
				assertEquals(arrayPostFlipp[i][j], tester.returnNumber(i, j));
			}
		}

	}

	@Test
	public void testFlipV() {
		tester.update(array);
		tester.flippV();
		int[][] arrayPostFlipp = { { 5, 3, -1, -1, 7, -1, -1, -1, -1 }, { 6, -1, -1, 1, 9, 5, -1, -1, -1 },
				{ -1, 9, 8, -1, -1, -1, -1, 6, -1 }, { 8, -1, -1, -1, 6, -1, -1, -1, 3 },
				{ 4, -1, -1, 8, -1, 3, -1, -1, 1 }, { 7, -1, -1, -1, 2, -1, -1, -1, 6 },
				{ -1, 6, -1, -1, -1, -1, 2, 8, -1 }, { -1, -1, -1, 4, 1, 9, -1, -1, 5 },
				{ -1, -1, -1, -1, 8, -1, -1, 7, 9 } };
		for (int i = 1; i < NUMB_ROWS; i++) {
			for (int j = 1; i < NUMB_COLS; i++) {
				assertEquals(arrayPostFlipp[i][j], tester.returnNumber(i, j));
			}
		}

	}

	@Test
	public void testFlipDB() {
		tester.update(array);
		tester.flippDB();
		int[][] arrayPostFlipp = { { 5, 6, -1, 8, 4, 7, -1, -1, -1 }, { 3, -1, 9, -1, -1, -1, 6, -1, -1 },
				{ -1, -1, 8, -1, -1, -1, -1, -1, -1 }, { -1, 1, -1, -1, 8, -1, -1, 4, -1 },
				{ 7, 9, -1, 6, -1, 2, -1, 1, 8 }, { -1, 5, -1, -1, 3, -1, -1, 9, -1 },
				{ -1, -1, -1, -1, -1, -1, 2, -1, -1 }, { -1, -1, 6, -1, -1, -1, 8, -1, 7 },
				{ -1, -1, -1, 3, 1, 6, -1, 5, 9 } };
		for (int i = 1; i < NUMB_ROWS; i++) {
			for (int j = 1; i < NUMB_COLS; i++) {
				assertEquals(arrayPostFlipp[i][j], tester.returnNumber(i, j));
			}
		}

	}

	@Test
	public void testFlipDR() {
		tester.update(array);
		tester.flippDR();
		int[][] arrayPostFlipp = { { 9, 5, -1, 6, 1, 3, -1, -1, -1 }, { 7, -1, 8, -1, -1, -1, 6, -1, -1 },
				{ -1, -1, 2, -1, -1, -1, -1, -1, -1 }, { -1, 9, -1, -1, 3, -1, -1, 5, -1 },
				{ 8, 1, -1, 2, -1, 6, -1, 9, 7 }, { -1, 4, -1, -1, 8, -1, -1, 1, -1 },
				{ -1, -1, -1, -1, -1, -1, 8, -1, -1 }, { -1, -1, 6, -1, -1, -1, 9, -1, 3 },
				{ -1, -1, -1, 7, 4, 9, -1, 6, 5 } };
		for (int i = 1; i < NUMB_ROWS; i++) {
			for (int j = 1; i < NUMB_COLS; i++) {
				assertEquals(arrayPostFlipp[i][j], tester.returnNumber(i, j));
			}
		}

	}
}