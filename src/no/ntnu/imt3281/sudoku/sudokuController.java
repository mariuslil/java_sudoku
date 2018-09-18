package no.ntnu.imt3281.sudoku;

import java.awt.TextField;
import java.awt.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class sudokuController {
	
    @FXML
    private Button newGame;
    
    @FXML
    private Canvas canvas;
    
    @FXML
    private BorderPane borderPane;
    
    @FXML
    private TextField rows;
    
    @FXML
    private TextField columns;
    
    @FXML
    private TextField status;
    
	@FXML
    void newGame(ActionEvent event) {
    	int numRows = 9;
    	int numColumns = 9;
    	
    	GridPane grid = new GridPane();
    	TextField textFields[][] = new TextField[numRows][numColumns];
    	
    	for (int r = 0; r < numRows; r++) {
    		for (int c = 0; c < numColumns; c++) {
    			textFields[r][c] = new TextField(String.format("[%d][%d]", r, c));
    			grid.add(textFields[r][c], c, r);
    			
    		}
    	}
    	
    	borderPane.setCenter(grid);

    }

}
