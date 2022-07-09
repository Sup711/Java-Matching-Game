package GameLogic;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.*;

public class View_GUI extends Application implements Observer<Model_GameLogic, String>{

    private Stage stage;
    private BorderPane board;
    private Model_GameLogic game;
    private Label gameLabel;
    private Button[][] matches;

    public void init() {
        this.game = new Model_GameLogic();
        this.board = new BorderPane();
    }

    public void start(Stage stage) {
        this.stage = stage;
        GridPane size = new GridPane();
        Button two = new Button();
        Button four = new Button();
        Button six = new Button();

        two.setText("2x2");
        two.setOnAction(actionEvent -> game.newGame(2, 2));
        two.setMinHeight(100);
        two.setMinWidth(100);
        size.add(two, 0, 0);

        four.setText("4x4");
        four.setOnAction(actionEvent -> game.newGame(4, 4));
        four.setMinHeight(100);
        four.setMinWidth(100);
        size.add(four, 0, 1);

        six.setText("6x6");
        six.setOnAction(actionEvent -> game.newGame(6, 6));
        six.setMinHeight(100);
        six.setMinWidth(100);
        size.add(six, 0, 2);

        size.setAlignment(Pos.CENTER);
        board.setRight(size);
        board.setMinWidth(1250);
        board.setMinHeight(1100);

        this.gameLabel = new Label();
        gameLabel.setText("Select a Board Size");
        gameLabel.setAlignment(Pos.CENTER);
        board.setTop(gameLabel);

        Scene scene = new Scene(board);
        stage.setScene(scene);
        stage.show();


        this.game.addObserver(this);
    }

    public void update(Model_GameLogic game, String gameState){

        if (gameState.equals("load")){
            createFlips(game);
        }

    }


    public void createFlips(Model_GameLogic game){
        GridPane flips = new GridPane();

        for (int i = 0; i < game.numRows; i++) {
            for (int j = 0; j < game.numCols; j++) {
                Button temp = new Button();
                temp.setBackground(new Background(new BackgroundFill(game.board[i][j], new CornerRadii(5), Insets.EMPTY)));
                temp.setMinWidth(150);
                temp.setMinHeight(150);
                flips.add(temp, i, j);
            }
        }

        flips.setAlignment(Pos.CENTER);
        board.setCenter(flips);
        board.setMinSize(game.numRows*200, game.numCols*200);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            Application.launch(args);
        }
    }

}
