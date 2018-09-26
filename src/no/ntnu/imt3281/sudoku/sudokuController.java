package no.ntnu.imt3281.sudoku;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

public class sudokuController {

	private Sudoku sudoku;

	@FXML
	private BorderPane borderPane;

	@FXML
	private Button NewGame;

	@FXML
	private ToolBar ToolBar;

	@FXML
	private Button flipp_h;

	@FXML
	private Button flipp_v;

	@FXML
	private Button flipp_dr;

	@FXML
	private Button flipp_db;

	@FXML
	private Button clear;

	public sudokuController() {
		sudoku = new Sudoku();
		Platform.runLater(() -> {
			sudoku.generate(borderPane);
		});
	}

	@FXML
	void newGame(ActionEvent event) {
		sudoku.newGame();
	}

	@FXML
	void clear(ActionEvent event) {
		sudoku.clear();
	}

	@FXML
	void flippDB(ActionEvent event) {
		sudoku.flippLR();
	}

	@FXML
	void flippDR(ActionEvent event) {
		sudoku.flippRL();
	}

	@FXML
	void flippH(ActionEvent event) {
		sudoku.flippH();
	}

	@FXML
	void flippV(ActionEvent event) {
		sudoku.flippV();
	}
}
