package no.ntnu.imt3281.sudoku;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Sudoku extends Application {

	private static final int NUMB_COLS = 9;
	private static final int NUMB_ROWS = 9;
	private TextField[][] textFields;
	private String styleGray = "-fx-control-inner-background: rgba(187, 187, 187, 1);";
	private String styleRed = "-fx-control-inner-background: rgba(255, 0, 0, 0.8)";
	private String styleWhite = "-fx-control-inner-background: rgba(255, 255, 255, 1)";

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Starts the UI form the FXML file.
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		ResourceBundle bundle = ResourceBundle.getBundle("no.ntnu.imt3281.sudoku.MessagesBundle");
		Parent root = FXMLLoader.load(getClass().getResource("Sudoku.fxml"), bundle);
		Scene scene = new Scene(root);
		primaryStage.setTitle("Sudoku");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Geerates the board and all the fields. Activates listeners on all the fields
	 * to listen for changes and excecute the appropriate function denpending on the
	 * change.
	 * 
	 * @param borderPane is the FX element the board is generated in.
	 */
	public void generate(BorderPane borderPane) {
		textFields = new TextField[NUMB_ROWS][NUMB_COLS];
		GridPane grid = new GridPane();
		for (int r = 0; r < NUMB_ROWS; r++) {
			for (int c = 0; c < NUMB_COLS; c++) {
				TextField field = new TextField();
				field.setPrefSize((borderPane.getHeight()) / NUMB_ROWS, (borderPane.getWidth()) / NUMB_COLS);
				textFields[r][c] = field;
				grid.add(field, c, r);
				textFields[r][c].setAlignment(Pos.CENTER);
				textFields[r][c].setFont(Font.font("system regular", FontWeight.BOLD, 25));
				final int row = r;
				final int col = c;
				textFields[r][c].textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (!newValue.matches("\\d*")) { // force the field to be numeric only
							textFields[row][col].setText(newValue.replaceAll("[^\\d]", ""));
						}
						if (textFields[row][col].getText().length() > 1) {
							textFields[row][col].setText(textFields[row][col].getText().substring(0, 1));
						}
						if (newValue.matches("0")) {
							textFields[row][col].clear();
						}
						if (newValue.matches("")) {
							textFields[row][col].setStyle(styleWhite);
							return;
						}
						try {
							checkValid(row, col, newValue);
						} catch (BadNumberException e) {
							textFields[row][col].setStyle(styleRed);
						}
						if (checkFinished()) {
							finished();
						}
					}
				});
			}
		}
		borderPane.setCenter(grid);
		grid.setGridLinesVisible(true);
	}

	/**
	 * Updates the active board with new values. Starts with clearing the board
	 * before inserting new values. Calls the lock() function to insert and lock the
	 * new value to the board.
	 * 
	 * @param newBoard must be a valid sudoku board and replaces the current board.
	 */
	public void update(int[][] newBoard) {
		clear();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (newBoard[i][j] != -1 && newBoard[i][j] != 0) {
					lock(newBoard, i, j);
				}
			}
		}
	}

	/**
	 * Locks a field with it's new value.
	 * 
	 * @param board is the current or relevant board.
	 * @param row   defines on witch row to lock.
	 * @param col   defines on witch row to lock.
	 */
	public void lock(int[][] board, int row, int col) {
		textFields[row][col].setText(board[row][col] + "");
		textFields[row][col].setEditable(false);
		textFields[row][col].setStyle(styleGray);
	}

	/**
	 * Reads a new board from file and places it on the board.
	 */
	public void newGame() {
		int[][] board = readJSON();
		update(board);
	}

	/**
	 * Runs three functions that check if the new input is valid in corelation to
	 * the row, column and the 3x3 box it's in.
	 * 
	 * @param row is the row that the new value is on.
	 * @param col is the column that the new value is on.
	 * @param num is the new value that the user inputed.
	 * @return returns a boolean value of wheter the new value is valid in that
	 *         field.
	 * @throws BadNumberException if the number is not valid.
	 */
	public boolean checkValid(int row, int col, String num) throws BadNumberException {
		if (checkRow(row, col, num)) {
			if (checkColumn(row, col, num)) {
				if (checkBox(row, col, num)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if the new number is allready in the relevant row.
	 * 
	 * @param row the row on witch the new number is.
	 * @param col the column on witch the new number is.
	 * @param num the new number.
	 * @return the boolean value of wheter the new number is valid or not
	 * @throws BadNumberException if the number is not valid.
	 */
	public boolean checkRow(int row, int col, String num) throws BadNumberException {
		Iterator<String> itr = getIteratorRow(row);
		int i = 0;
		while (itr.hasNext()) {
			if (itr.next().equals(num)) {
				if (i != col) {
					throw new BadNumberException(row, col, num);
				}
			}
			i++;
		}
		return true;
	}

	/**
	 * Checks if the number is allready in the relevant column.
	 * 
	 * @param row the row on witch the new number is.
	 * @param col the column on witch the new number is.
	 * @param num the new number.
	 * @return the boolean value of wheter the new number is valid or not
	 * @throws BadNumberException if the number is not valid.
	 */
	public boolean checkColumn(int row, int col, String num) throws BadNumberException {
		Iterator<String> itr = getIteratorCol(col);
		int i = 0;
		while (itr.hasNext()) {
			if (itr.next().equals(num)) {
				if (i != row) {
					throw new BadNumberException(row, col, num);
				}
			}
			i++;
		}
		return true;
	}

	/**
	 * Checks if the number is allready in relevant the 3x3 box. uses the numbers
	 * row and column position to find the upper left corner of the box to start
	 * checking from there
	 * 
	 * @param row the row on witch the new number is.
	 * @param col the column on witch the new number is.
	 * @param num the new number.
	 * @return the boolean value of wheter the new number is valid or not
	 * @throws BadNumberException if the number is not valid.
	 */
	public boolean checkBox(int row, int col, String num) throws BadNumberException {
		int rowStart = (row / 3) * 3;
		int colStart = (col / 3) * 3;
		Iterator<String> itr = getIteratorBox(rowStart, colStart);
		for (int i = rowStart; i < rowStart + 3; i++) {
			for (int j = colStart; j < colStart + 3; j++) {
				if (itr.next().equals(num)) {
					if (i != row && j != col) {
						throw new BadNumberException(row, col, num);
					}
				}
			}
		}
		return true;
	}

	/**
	 * Checks if the game is finished by checking all the fields for any sign that
	 * it's not finished.
	 * 
	 * @return the boolean value of if it is finished or not.
	 */
	public boolean checkFinished() {
		for (int i = 0; i < NUMB_ROWS; i++) {
			for (int j = 0; j < NUMB_COLS; j++) {
				if (textFields[i][j].getStyle().equals(styleRed) || textFields[i][j].getText().isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * If the game is finished it should display a message to the user, this is not
	 * finished yet and therfore only displays a println.
	 */
	public void finished() {
		System.out.println("Hurra! du klarte det!");
	}

	/**
	 * Flipps the current board horizontaly by replacing all the numbers with the
	 * ones opposite them. Then sends the new board to be updated.
	 */
	public void flippH() {
		int[][] mirror = new int[NUMB_ROWS][NUMB_COLS];
		for (int i = 0; i < NUMB_ROWS; i++) {
			for (int j = 0; j < NUMB_COLS; j++) {
				if (!textFields[8 - i][j].getText().isEmpty() && textFields[8 - i][j].getStyle().equals(styleGray)) {
					mirror[i][j] = Integer.parseInt(textFields[8 - i][j].getText());
				}
			}
		}
		update(mirror);
	}

	/**
	 * Flipps the current board verticaly by replacing all the numbers with the ones
	 * underneath them. Then sends the new board to be updated.
	 */
	public void flippV() {
		int[][] mirror = new int[NUMB_ROWS][NUMB_COLS];
		for (int i = 0; i < NUMB_ROWS; i++) {
			for (int j = 0; j < NUMB_COLS; j++) {
				if (!textFields[i][8 - j].getText().isEmpty() && textFields[i][8 - j].getStyle().equals(styleGray)) {
					mirror[i][j] = Integer.parseInt(textFields[i][8 - j].getText());
				}
			}
		}
		update(mirror);
	}

	/**
	 * Flipps the board along the blue diagonal. Then sends the new board to be
	 * updated.
	 */
	public void flippDB() {
		int[][] mirror = new int[NUMB_ROWS][NUMB_COLS];
		for (int i = 0; i < NUMB_ROWS; i++) {
			for (int j = 0; j < NUMB_COLS; j++) {
				if (!textFields[j][i].getText().isEmpty() && textFields[j][i].getStyle().equals(styleGray)) {
					mirror[i][j] = Integer.parseInt(textFields[j][i].getText());
				}
			}
		}
		update(mirror);
	}

	/**
	 * Flipps the board along the red diagonal. Then sends the new board to be
	 * updated.
	 */
	public void flippDR() {
		int[][] mirror = new int[NUMB_ROWS][NUMB_COLS];
		for (int i = 0; i < NUMB_ROWS; i++) {
			for (int j = 0; j < NUMB_COLS; j++) {
				if (!textFields[8 - j][8 - i].getText().isEmpty()
						&& textFields[8 - j][8 - i].getStyle().equals(styleGray)) {
					mirror[i][j] = Integer.parseInt(textFields[8 - j][8 - i].getText());
				}
			}
		}
		update(mirror);
	}

	/**
	 * Replaces all the numbers by increasing them by 1. This should have been
	 * random.
	 */
	public void replaceNums() {
		int[][] newBoard = new int[NUMB_ROWS][NUMB_COLS];
		for (int i = 0; i < NUMB_ROWS; i++) {
			for (int j = 0; j < NUMB_COLS; j++) {
				if (!textFields[i][j].getText().isEmpty() && textFields[i][j].getStyle().equals(styleGray)) {
					newBoard[i][j] = Integer.parseInt(textFields[i][j].getText()) + 1;
					if (newBoard[i][j] > 9) {
						newBoard[i][j] = 1;
					}
				}
			}
		}
		update(newBoard);
	}

	/**
	 * Reads a new board from a JSON file. Makes sure that only the numbers are
	 * being read.
	 * 
	 * @return the board that has been read from the file.
	 */
	public int[][] readJSON() {
		int[][] board = new int[9][9];
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("board1.json"), "UTF-8"))) {
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			String[] numbers = sb.toString().split("[^-?1-9]");
			int[] intNumbers = new int[NUMB_ROWS * NUMB_COLS];
			int l = 0;
			for (String number : numbers) {
				if (!number.equals("")) {
					intNumbers[l++] = Integer.parseInt(number);
				}
			}
			int k = 0;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					board[i][j] = intNumbers[k++];
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return board;
	}

	/**
	 * Clears the board for all numbers.
	 */
	public void clear() {
		for (int i = 0; i < NUMB_ROWS; i++) {
			for (int j = 0; j < NUMB_COLS; j++) {
				textFields[i][j].clear();
				textFields[i][j].setEditable(true);
				textFields[i][j].setStyle(styleWhite);
			}
		}
	}

	public int returnNumber(int r, int c) {
		return Integer.parseInt(textFields[r][c].getText());
	}

	/**
	 * Makes an iterator of a row of the board.
	 * 
	 * @param r is the row that is to be transformed.
	 * @return the iterator.
	 */
	public Iterator<String> getIteratorRow(int r) {
		ArrayList<String> arr = new ArrayList<String>();
		for (int i = 0; i < 9; i++) {
			arr.add(textFields[r][i].getText());
		}
		return arr.iterator();
	}

	/**
	 * Makes an iterator of a column of the board.
	 * 
	 * @param c the column that is to be transformed.
	 * @return the iterator.
	 */
	public Iterator<String> getIteratorCol(int c) {
		ArrayList<String> arr = new ArrayList<String>();
		for (int i = 0; i < 9; i++) {
			arr.add(textFields[i][c].getText());
		}
		return arr.iterator();
	}

	/**
	 * Makes an iterator of a 3x3 box on the board.
	 * 
	 * @param r is the row position of the upper left corner of the box.
	 * @param c is the column position of the upper left corner of the box.
	 * @return the iterator.
	 */
	public Iterator<String> getIteratorBox(int r, int c) {
		ArrayList<String> arr = new ArrayList<String>();
		for (int i = r; i < r + 3; i++) {
			for (int j = c; j < c + 3; j++) {
				arr.add(textFields[i][j].getText());
			}
		}
		return arr.iterator();
	}

}
