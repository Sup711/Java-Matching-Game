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

public class View_GUI extends Application{

    private Stage stage;
    private BorderPane board;

    public void start(Stage stage) {
        this.stage = stage;
        this.board = new BorderPane();
        createFlips();

        Scene scene = new Scene(board);
        stage.setScene(scene);
        stage.show();
    }

    public void createFlips(){
        GridPane flips = new GridPane();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                flips.add(new Button(), i, j);
            }
        }
        flips.setAlignment(Pos.CENTER);
        board.setCenter(flips);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            Application.launch(args);
        }
    }

}
