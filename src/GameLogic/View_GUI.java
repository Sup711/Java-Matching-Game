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

    /** The board the contains the cards */
    private BorderPane board;
    /** The actual game and game information */
    private Model_GameLogic game;
    /** The label that tells the user what to do */
    private Label gameLabel;
    /** The collection of cards */
    private Button[][] cards;
    /** The previous game state */
    private String prevGameState;
    /** The label that displays the score */
    private Label score;

    /**
     * Sets up the GUI and sends it to the screen
     * @param stage the stage the GUI gets created on
     */
    public void start(Stage stage) {
        /* Variable initiation */
        this.game = new Model_GameLogic();
        this.board = new BorderPane();
        GridPane size = new GridPane();
        GridPane top = new GridPane();
        Button two = new Button();
        Button four = new Button();
        Button six = new Button();
        Button reset = new Button();
        score = new Label();

        /* Creates the score label */
        score.setText("Score: " + 0);
        score.setAlignment(Pos.CENTER);
        score.setStyle("-fx-font: 24 Courier;");
        size.add(score, 0, 0);

        /* Creates the 2x2 size button */
        two.setText("2x2");
        two.setStyle("-fx-font: 24 Courier;");
        two.setOnAction(actionEvent -> game.newGame(2, 2));
        two.setMinHeight(100);
        two.setMinWidth(100);
        size.add(two, 0, 1);

        /* Creates the 4x4 size button */
        four.setText("4x4");
        four.setStyle("-fx-font: 24 Courier;");
        four.setOnAction(actionEvent -> game.newGame(4, 4));
        four.setMinHeight(100);
        four.setMinWidth(100);
        size.add(four, 0, 2);

        /* Creates the 6x6 size button */
        six.setText("6x6");
        six.setStyle("-fx-font: 24 Courier;");
        six.setOnAction(actionEvent -> game.newGame(6, 6));
        six.setMinHeight(100);
        six.setMinWidth(100);
        size.add(six, 0, 3);

        /* Creates the reset button */
        reset.setText("Reset");
        reset.setStyle("-fx-font: 24 Courier;");
        reset.setOnAction(actionEvent -> game.reset());
        reset.setMinHeight(100);
        reset.setMinWidth(100);
        size.add(reset, 0, 4);

        /* Sets up the window size and adds the size buttons in the correct position */
        size.setAlignment(Pos.CENTER);
        board.setRight(size);
        board.setMinWidth(1250);
        board.setMinHeight(1100);

        /* Creates the game label and adds it to the game board */
        this.gameLabel = new Label();
        gameLabel.setText("Select a Board Size");
        gameLabel.setStyle("-fx-font: 24 Courier;");
        top.add(gameLabel, 0, 0);
        top.setAlignment(Pos.TOP_CENTER);
        board.setTop(top);

        /* Puts the game board on the stage */
        Scene scene = new Scene(board);
        stage.setScene(scene);
        stage.show();

        /* Tells the observer the game is ready */
        this.game.addObserver(this);
    }

    /**
     * In charge of changing the GUI based on the current game state
     * @param game the current game status
     * @param gameState the current gamestate
     */
    public void update(Model_GameLogic game, String gameState){

        /* Load game state */
        if (gameState.equals("load")){
            createFlips(game);
            gameLabel.setText("Loaded a New Game");
            score.setText("Score: " + 0);
        }
        /* Reset game state */
        if (gameState.equals("reset")){
            createFlips(game);
            gameLabel.setText("Reset the Game");
            score.setText("Score: " + 0);
        }
        /* First selection game state */
        if (gameState.equals("select1")){
            if (!prevGameState.equals("match")){
                unflipCardsSelect(game);
            }
            flipCardsSelect1(game);
        }
        /* Second selection game state */
        if (gameState.equals("select2")){
            flipCardsSelect2(game);
        }
        /* Match made game state */
        if (gameState.equals("match")){
            gameLabel.setText("Congrats, You Matched :)");
            score.setText("Score: " + game.score);
        }
        /* No match made game state */
        if (gameState.equals("noMatch")){
            gameLabel.setText("Big Oof, You Failed to Match :(");
        }
        /* Stores the previous gamestate */
        prevGameState = gameState;
    }

    /**
     * Flips the first selected card
     * @param game the current game
     */
    public void flipCardsSelect1(Model_GameLogic game){
        int select1i = game.select1[0];
        int select1j = game.select1[1];
        cards[select1i][select1j].setBackground(new Background(new BackgroundFill(game.board[select1i][select1j], new CornerRadii(5), Insets.EMPTY)));
    }

    /**
     * Flips the second selected card
     * @param game the current game
     */
    public void flipCardsSelect2(Model_GameLogic game){
        int select2i = game.select2[0];
        int select2j = game.select2[1];
        cards[select2i][select2j].setBackground(new Background(new BackgroundFill(game.board[select2i][select2j], new CornerRadii(5), Insets.EMPTY)));
    }

    /**
     * Unflips the cards if no match was made
     * @param game the current game
     */
    public void unflipCardsSelect(Model_GameLogic game){
        int select1i = game.prevSelect1[0];
        int select1j = game.prevSelect1[1];
        int select2i = game.select2[0];
        int select2j = game.select2[1];
        cards[select1i][select1j].setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(5), Insets.EMPTY)));
        cards[select2i][select2j].setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(5), Insets.EMPTY)));
    }

    /**
     * Creates all the cards on the board
     * @param game the current game
     */
    public void createFlips(Model_GameLogic game){
        cards = new Button[game.numRows][game.numCols];
        GridPane flips = new GridPane();

        for (int i = 0; i < game.numRows; i++) {
            for (int j = 0; j < game.numCols; j++) {
                Button temp = new Button();
                temp.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(5), Insets.EMPTY)));
                temp.setMinWidth(150);
                temp.setMinHeight(150);
                int finalI = i;
                int finalJ = j;
                temp.setOnAction(event -> game.select(finalI, finalJ));
                cards[i][j] = temp;
                flips.add(temp, i, j);
            }
        }

        flips.setAlignment(Pos.CENTER);
        board.setCenter(flips);
        board.setMinSize(game.numRows*200, game.numCols*200);
    }

    /**
     * Just the main so the program can actually be run
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            Application.launch(args);
        }
    }

}
