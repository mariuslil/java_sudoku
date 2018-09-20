package no.ntnu.imt3281.sudoku;


import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class sudokuController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button NewGame;
    
    @FXML
    private ToolBar ToolBar;

    @FXML
    void newGame(ActionEvent event) {
    	int rows = 9;
    	int cols= 9;
    	GridPane grid = new GridPane();
    	TextField textFields[][] = new TextField[rows][cols];
    	for (int r = 0; r<rows; r++) {
    		for (int c = 0; c<cols; c++) {
    			grid.add(textFields[r][c] = new TextField(), c, r);
    		}
    	}
    	borderPane.setCenter(grid);
    	grid.setGridLinesVisible(true);
    	GridPane.setRowSpan(grid, 9);
    	GridPane.setColumnSpan(grid, 9);
    }

}
