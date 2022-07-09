package GameLogic;

import javafx.scene.paint.Color;

import java.util.*;

public class Model_GameLogic{


    private final List<Observer<Model_GameLogic, String>> observers = new LinkedList<>();

    public void addObserver(Observer<Model_GameLogic, String> observer){
        this.observers.add(observer);
    }

    public void alertObservers(String gameState){
        for (var observer : observers){
            observer.update(this, gameState);
        }
    }

//-----------------------------------------------------------------------------------------------

    public final Color[] COLORLIST = {Color.YELLOW, Color.RED, Color.GREEN, Color.BROWN, Color.AZURE,
                                      Color.TEAL, Color.PURPLE, Color.NAVY, Color.ORANGE, Color.MAROON,
                                      Color.CORAL, Color.FUCHSIA, Color.LIME, Color.HOTPINK, Color.GOLDENROD,
                                      Color.PLUM, Color.OLIVE, Color.CYAN};
    public String[][] board;
    public HashSet<Color> colorMap;
    public int numRows;
    public int numCols;
    private String[][] origBoard;


    public void newGame(int numRows, int numCols){
        this.numRows = numRows;
        this.numCols = numCols;
        this.board = new String[numRows][numCols];
        this.alertObservers("load");

        System.out.println("Rows: " + numRows);
        System.out.println("Cols: " + numCols);

        numToIntMap();

    }

    /**
     * Creates a list of colors to be used for the upcoming game
     */
    private void numToIntMap(){
        colorMap = new HashSet<Color>();
        Random rand = new Random();
        int randIndex;

        while (colorMap.size() < (numRows*numCols)/2){
            randIndex = rand.nextInt(18);
            colorMap.add(COLORLIST[randIndex]);
        }
        System.out.println(colorMap);
    }

    private void randomBoard(){

    }

    public void reset(){

    }

    public void select(){

    }

    @Override
    public int hashCode(){
        return Arrays.deepHashCode(COLORLIST);
    }


}
