

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TicTacToe extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private Button[][] squares;
	private SquareHandler handler;
	private String turn;
	private int moves;
	private TextField xCountDisplay;
	private TextField drawCountDisplay;
	private TextField oCountDisplay;
	private int xCount;
	private int drawCount;
	private int oCount;
	
	@Override
	public void start(Stage primaryStage) {
		squares = new Button[3][3];
		GridPane grid = new GridPane();
		handler = new SquareHandler();
		turn = "X";
		moves = 0;
		xCount = drawCount = oCount = 0;
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				squares[i][j] = new Button(" ");
				squares[i][j].setFont(new Font("courier new", 80));
				squares[i][j].setOnAction(handler);
				grid.add(squares[i][j], j, i);
			}
		}
		BorderPane mainPane = new BorderPane();
		mainPane.setCenter(grid);
		Button clear = new Button("NEW GAME");
		clear.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				moves = 0;
				for(int i=0; i<3; i++) {
					for(int j=0; j<3; j++) {
						squares[i][j].setText(" ");
					}
				}
			}
			
		});
		HBox bottomBox = new HBox(clear);
		bottomBox.setAlignment(Pos.CENTER);
		HBox topBox = buildTopBox();
		mainPane.setTop(topBox);
		mainPane.setBottom(bottomBox);
		Scene scene = new Scene(mainPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tic-Tac-Toe");
		primaryStage.show();
	}
	
	private HBox buildTopBox() {
		Label xLabel = new Label("X wins: ");
		Label drawLabel = new Label("Draws: ");
		Label oLabel = new Label("O wins: ");
		xCountDisplay = new TextField("0");
		xCountDisplay.setMaxWidth(50);
		drawCountDisplay = new TextField("0");
		drawCountDisplay.setMaxWidth(50);
		oCountDisplay = new TextField("0");
		oCountDisplay.setMaxWidth(50);
		HBox box = new HBox(xLabel, xCountDisplay,
				            drawLabel, drawCountDisplay,
				            oLabel, oCountDisplay);
		box.setAlignment(Pos.CENTER);
		return box;
	}
	
	private String gameOver() {
		// Check rows
		for(int r=0; r<3; r++) {
			if (squares[r][0].getText().equals(squares[r][1].getText()) &&
				squares[r][1].getText().equals(squares[r][2].getText())) {
				if (!squares[r][0].getText().equals(" ")) return squares[r][0].getText();
			}
		}
		// Check columns
		for(int c=0; c<3; c++) {
			if (squares[0][c].getText().equals(squares[1][c].getText()) &&
				squares[1][c].getText().equals(squares[2][c].getText())) {
				if (!squares[0][c].getText().equals(" ")) return squares[0][c].getText();
			}
		}
		// Check diagonals
		if ((squares[0][0].getText().equals(squares[1][1].getText()) &&
			 squares[1][1].getText().equals(squares[2][2].getText())) ||
			(squares[0][2].getText().equals(squares[1][1].getText()) &&
			 squares[1][1].getText().equals(squares[2][0].getText()))) {
			if (!squares[1][1].getText().equals(" ")) return squares[1][1].getText();
		}
		return null;
	}

	private class SquareHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			Button square = (Button) event.getSource();
			if(square.getText().equals(" ")) {
				moves++;
				square.setText(turn);
				turn = turn.equals("X") ? "O" : "X";
				String winner = gameOver();
				if (winner != null) {
					if(winner.equals("X")) {
						xCount++;
						xCountDisplay.setText(xCount+"");
					} else {
						oCount++;
						oCountDisplay.setText(oCount+"");
					}
					Alert a = new Alert(Alert.AlertType.INFORMATION,
							            winner+" is the winner!",
							            ButtonType.OK);
					a.show();
				} else if (moves == 9) {
					drawCount++;
					drawCountDisplay.setText(drawCount+"");
					Alert a = new Alert(Alert.AlertType.INFORMATION,
				            "It's a DRAW!  Bummer.",
				            ButtonType.OK);
					a.show();
				}
			}
		}
		
	}
	
}