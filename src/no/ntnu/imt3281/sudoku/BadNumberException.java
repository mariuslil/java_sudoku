package no.ntnu.imt3281.sudoku;

public class BadNumberException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int row;
	private int col;
	private String num;

	public BadNumberException(int row, int col, String num) {
		this.row = row;
		this.col = col;
		this.num = num;
		System.err.println(getMessage());
	}

	@Override
	public String getMessage() {
		return String.format("Invalid number %s on row %s and column %s %n", num, row, col);
	}

}
