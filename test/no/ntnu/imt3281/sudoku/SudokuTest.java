package no.ntnu.imt3281.sudoku;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SudokuTest {

	private static final int NUMB_COLS = 9;
	private static final int NUMB_ROWS = 9;

	@Test
	public void testCheckValid() {

	}

	@Test
	public void testCheckRow() {

	}

	@Test
	public void testCheckCol() {

	}

	@Test
	public void testCheckBox() {

	}

	@Test
	public void testCheckFinished() {

	}

	@Test
	public void testFinished() {

	}

	@Test
	public void testLock() {

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
}