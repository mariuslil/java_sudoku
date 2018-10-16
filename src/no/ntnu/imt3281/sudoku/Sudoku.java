package no.ntnu.imt3281.sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
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

	public static void main(String args[]) {
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
						if (!checkValid(row, col, textFields, newValue)) {
							textFields[row][col].setStyle(styleRed);
						}
						if (newValue.matches("")) {
							textFields[row][col].setStyle(styleWhite);
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

	public void lock(int[][] board, int row, int col) {
		textFields[row][col].setText(board[row][col] + "");
		textFields[row][col].setEditable(false);
		textFields[row][col].setStyle(styleGray);
	}

	public void newGameEasy() {
		int[][] board = readJSON("board1.json");
		update(board);
	}
	
	public void newGameMedium() {
		int[][] board = readJSON("board2.json");
		update(board);
	}

	public boolean checkValid(int row, int column, TextField[][] board, String number) {
		/*Integer[] arr = new Integer[9];
		for (int r = 0; r < 9; r++) {
			if (r != column) {
				arr[r] = Integer.parseInt(board[row][r].getText());		
			}
		}
		Iterator<Integer> iterator = Arrays.stream(arr).iterator();
		if (iterator.hasNext()) {
			if (iterator.equals(Integer.parseInt(number))) {
				return false;
			}
		}*/
		for (int r = 0; r < 9; r++) {
			if (r != column) {
				if (board[row][r].getText().equals(number)) {
					return false;
				}
			}
		}

		for (int c = 0; c < 9; c++) {
			if (c != row) {
				if (board[c][column].getText().equals(number)) {
					return false;
				}
			}
		}

		int rowStart = (row / 3) * 3;						//Finner starten av ruten
		int colStart = (column / 3) * 3;
		int rowEnd = rowStart + 3;
		int colEnd = colStart + 3;
		for (int r = rowStart; r < rowEnd; r++) {
			for (int c = colStart; c < colEnd; c++) {
				if (r != row && c != column) {
					if (board[r][c].getText().equals(number)) {
						return false;
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

	public int[][] readJSON(String boardJSON) {
		int[][] board = new int[9][9];
		try (BufferedReader br = new BufferedReader(new FileReader(boardJSON))) {		
			StringBuffer sb = new StringBuffer();					//medsendt filnavn, board1 eller board2
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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

}
