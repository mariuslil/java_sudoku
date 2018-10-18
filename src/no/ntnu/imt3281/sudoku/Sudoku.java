package no.ntnu.imt3281.sudoku;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

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

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Sudoku.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Sudoku");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

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
	 * Locks a field with it's new value
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

	public void newGame() {
		int[][] board = readJSON();
		update(board);
	}

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

	public void finished() {
		System.out.println("Hurra! du klarte det!");
	}

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

	public int[][] readJSON() {
		int[][] board = new int[9][9];
		// new FileReader("board1.json"))
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

	public Iterator<String> getIteratorRow(int r) {
		ArrayList<String> arr = new ArrayList<String>();
		for (int i = 0; i < 9; i++) {
			arr.add(textFields[r][i].getText());
		}
		return arr.iterator();
	}

	public Iterator<String> getIteratorCol(int c) {
		ArrayList<String> arr = new ArrayList<String>();
		for (int i = 0; i < 9; i++) {
			arr.add(textFields[i][c].getText());
		}
		return arr.iterator();
	}

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
