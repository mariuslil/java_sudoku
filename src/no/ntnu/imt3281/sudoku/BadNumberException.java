package no.ntnu.imt3281.sudoku;

public class BadNumberException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int row;
	private int col;

	public BadNumberException(int row, int col) {
		this.row = row;
		this.col = col;
		System.err.println(getMessage());
	}

	@Override
	public String getMessage() {
		return String.format("Invalid number on row %s and column %s \n", row, col);
	}

}
