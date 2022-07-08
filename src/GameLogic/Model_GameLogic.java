package GameLogic;

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

    public String[][] board;
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

    }

    private void numToIntMap(){

    }

    private void randomBoard(){

    }

    public void reset(){

    }

    public void select(){

    }


}
