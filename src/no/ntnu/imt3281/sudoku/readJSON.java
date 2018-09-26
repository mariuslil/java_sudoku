package no.ntnu.imt3281.sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.*;

public class readJSON {

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader (new FileReader("board1.json"))) {
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			
			JSONArray json = new JSONArray(sb.toString());
			int[][] board = new int[9][9];
			int k = 0;
			for(int i = 0; i<9; i++) {
				for(int j = 0; j<9; j++) {
					board[i][j] = json.getInt(k);
					k++;
				}
			}
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
