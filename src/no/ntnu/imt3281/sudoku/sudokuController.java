package no.ntnu.imt3281.sudoku;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

public class sudokuController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button NewGame;
    
    @FXML
    private ToolBar ToolBar;

    @FXML
    void generate(ActionEvent event) {
    	System.out.println("Hei");
    }

}
