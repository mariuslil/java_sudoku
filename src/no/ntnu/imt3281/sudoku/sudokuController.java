package no.ntnu.imt3281.sudoku;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class sudokuController {

	private static final int NUMB_COLS = 9;
	private static final int NUMB_ROWS = 9;
	private TextField[][] textFields;

	@FXML
	private BorderPane borderPane;

	@FXML
	private Button NewGame;

	@FXML
	private ToolBar ToolBar;

	public sudokuController() {
		textFields = new TextField[NUMB_ROWS][NUMB_COLS];
		Platform.runLater(() -> {
			generate();
		});
	}

	public void generate() {
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
							textFields[row][col].clear();
						}
					}
				});
			}
		}
		borderPane.setCenter(grid);
		grid.setGridLinesVisible(true);
	}

	@FXML
	void newGame(ActionEvent event) {

	}

	public static boolean checkValid(int row, int column, TextField[][] board, String number) {
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

		int rowStart = (row / 3) * 3;
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
}
